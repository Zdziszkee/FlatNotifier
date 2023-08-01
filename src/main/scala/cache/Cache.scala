package me.zdziszkee.flatnotifier
package cache


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.FlatOffer

import java.nio.file.{Files, Paths}
import java.util


val gson = Gson()
val CACHE_NAME = "cache.json"

case class Cache(flats: util.ArrayList[FlatOffer])

object Cache {
  def write(flats: util.List[FlatOffer]): Unit = {
    val workingDir = Paths.get(".").toAbsolutePath.toString
    val cachePath = Paths.get(workingDir, CACHE_NAME)
    if (!Files.exists(cachePath)) {
      Files.createFile(cachePath)
    }
    val config = gson.toJson(flats)
    Files.write(cachePath, config.getBytes())
  }

  def read(): util.List[FlatOffer] = {
    val workingDir = Paths.get(".").toAbsolutePath.toString
    val cachePath = Paths.get(workingDir, CACHE_NAME)
    if (!Files.exists(cachePath)) {
      Files.createFile(cachePath)
      write(util.Collections.emptyList())
    }
    val token = new TypeToken[util.List[FlatOffer]]{}.getType
    val contents = Files.readString(cachePath)
    gson.fromJson(contents,token)
  }
}

