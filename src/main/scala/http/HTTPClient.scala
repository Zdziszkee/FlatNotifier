package me.zdziszkee.flatnotifier
package http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*

import scala.concurrent.Future

object HTTPClient {
  def sendRequest(implicit actorSystem:ActorSystem, uri: Uri): Future[HttpResponse] = {
    Http().singleRequest(HttpRequest(uri = uri))
  }
}
