package me.zdziszkee.flatnotifier
package config


import com.google.gson.Gson

import java.nio.file.{Files, Paths}

val CONFIG_NAME = "config.json"
val gson = Gson()
case class Config(url: String = "https://www.olx.pl/", email: String = "youremail@mail.com", delayInSeconds: Int = 30)

object ConfigLoader {
  def load(): Config = {
    val workingDir = Paths.get(".").toAbsolutePath.toString
    val configPath = Paths.get(workingDir, CONFIG_NAME)
    if (Files.exists(configPath)) {
      val contents = Files.readString(configPath)
      gson.fromJson(contents,classOf[Config])
    } else {
      val file = Files.createFile(configPath)
      Files.write(file,gson.toJson(Config()).getBytes)
      Config()
    }
  }
}
