package net.dmitriyvolk.pizzaandtech.generator.s3

import java.util.UUID

import awscala.s3.S3
import net.dmitriyvolk.pizzaandtech.generator.DataWriter.DataPath
import org.scalatest.concurrent.Eventually._
import org.scalatest.{Matchers, WordSpec}

import scala.io.Source

class S3DataWriterSpec(ignore: String) extends WordSpec with Matchers {

  def generateBucketName() = "pizza-and-tech-test-" + UUID.randomUUID().toString

  "S3 Data Writer" when {

    implicit val s3 = S3()

    def verifyWritingFile(bucketName: String, s3DataWriter: S3DataWriter, prefix: String) = {
      s3DataWriter.writeJsonData(DataPath("test/path"), "testfile.txt", "TEST DATA")
      eventually {
        val bucket = s3.bucket(bucketName).get
        val file = bucket.get(s"${prefix}test/path/testfile.txt").get
        Source.fromInputStream(file.getObjectContent).mkString should be("TEST DATA")
      }
    }

    "bucket exists" when {
      val bucketName = generateBucketName()
      val bucket = s3.createBucket(bucketName)


      "no root path specified" should {
         "write data into object" in verifyWritingFile(bucketName, new S3DataWriter(bucketName), "")
      }

      "root path is specified" should {
        "write data into object" in verifyWritingFile(bucketName, new S3DataWriter(bucketName, "some/root/path"), "some/root/path/")
      }

      bucket.destroy()
    }

    "bucket doesn't exist" should {
      "create bucket before writing file" in {
        val bucketName = generateBucketName()
        verifyWritingFile(bucketName, new S3DataWriter(bucketName), "")
      }
    }
  }
}
