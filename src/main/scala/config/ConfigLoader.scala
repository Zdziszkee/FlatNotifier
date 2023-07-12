package me.zdziszkee.flatnotifier
package config

import spray.json.{JsonParser, JsonReader}

import java.nio.file.{Files, Paths}

val SUPPORTED_SITES = List("https://www.olx.pl/");
val CONFIG_NAME = "config.json"

object ConfigLoader {
  def load(): Unit = {
    val workingDir = Paths.get(".").toAbsolutePath.toString
    val configPath = Paths.get(workingDir, CONFIG_NAME)
    if (Files.exists(configPath)) {
      val contents = Files.readString(configPath)
      val config = JsonParser(contents).convertTo[List[String]]
      val urls = config.urls

    } else {
      Files.createFile(configPath)

    }
  }
}
