package me.zdziszkee.flatnotifier
package parsers

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import model.FlatOffer

import java.util
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContextExecutor, Future}

trait RequestParser {
  def parse(future: Future[HttpResponse])(implicit system: ActorSystem, executionContext: ExecutionContextExecutor): Future[util.List[FlatOffer]]
}

