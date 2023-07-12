package me.zdziszkee.flatnotifier

import http.HTTPClient

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

@main
def main(): Unit = {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val futureHttpResponse = HTTPClient.sendRequest(system, "https://www.olx.pl/nieruchomosci/mieszkania/wynajem/krakow/?search%5Bfilter_enum_rooms%5D%5B0%5D=two&search%5Bfilter_float_price%3Ato%5D=2500&search%5Border%5D=created_at%3Adesc#764927822")
  val futureFlats = OLXParser.parse(futureHttpResponse)
}