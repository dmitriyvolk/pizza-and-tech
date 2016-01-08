package net.dmitriyvolk.pizzaandtech.generator.s3

import awscala.s3.{Bucket, S3}
import com.amazonaws.regions.{Regions, Region}
import Regions.US_EAST_1
import com.amazonaws.services.s3.model.{ObjectMetadata}
import com.amazonaws.util.StringInputStream
import net.dmitriyvolk.pizzaandtech.generator.DataWriter
import net.dmitriyvolk.pizzaandtech.generator.DataWriter.DataPath


class S3DataWriter(bucketName: String, rootFolder: String = "", region: Regions = US_EAST_1) extends DataWriter {

  implicit val s3 = S3()
  s3.setRegion(Region.getRegion(region))

  def createBucket(bucketName: String): Bucket = s3.createBucket(bucketName)

  val bucket = s3.bucket(bucketName).getOrElse(createBucket(bucketName))

  override def writeJsonData(folder: DataPath, filename: String, json: String): Unit = {
    val path = (if (rootFolder.isEmpty) "" else rootFolder.stripSuffix("/") + "/") + folder.path.stripPrefix("/")
    val objectName = s"${path}/$filename"
    bucket.putObject(objectName, new StringInputStream(json), new ObjectMetadata())
  }
}
