package net.dmitriyvolk.pizzaandtech.generator

import net.dmitriyvolk.pizzaandtech.generator.DataWriter.DataPath

object DataWriter {

  case class DataPath(path: String)
}

trait DataWriter {

  def writeJsonData(folder: DataPath, filename: String, json: String): Unit

}