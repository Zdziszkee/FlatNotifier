package me.zdziszkee.flatnotifier

import parsers.RequestParser

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.stream.{ActorMaterializer, SystemMaterializer}
import akka.stream.scaladsl.*
import me.zdziszkee.flatnotifier.model.Flat
import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import org.jsoup.select.NodeFilter
import spray.json.*

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.*
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object OLXParser extends RequestParser {

  override def parse(future: Future[HttpResponse])(implicit system: ActorSystem, executionContext: ExecutionContextExecutor): Future[ListBuffer[Flat]] = {
    future.flatMap { response =>
      val responseBodyFuture: Future[String] = response.entity.toStrict(5.seconds).map(_.data.utf8String)
      responseBodyFuture.map { responseBody =>
        val document = Jsoup.parse(responseBody)
        val listings = document.getElementsByClass("css-oukcj3").get(0).children();
        val flats: ListBuffer[Flat] = ListBuffer()
        listings.forEach { element =>
          if (element.hasAttr("data-cy")) {
            val advertElement = element.getElementsByClass("css-3xiokn")
            val children = advertElement.get(0).children()
            if(children.size()==0) {
              val url = "https://www.olx.pl" + element.children().get(0).attr("href")
              flats += Flat(url)
            }
          }
        }
        flats
      }.recover {
        case exception: Throwable =>
          println(s"Failed to parse response body: ${exception.getMessage}")
          throw exception
      }
    }.recover {
      case exception: Throwable =>
        println(s"Request failed: ${exception.getMessage}")
        throw exception
    }
  }
}