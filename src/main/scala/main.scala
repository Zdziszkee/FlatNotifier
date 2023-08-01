package me.zdziszkee.flatnotifier

import cache.Cache
import config.ConfigLoader
import clients.{EmailClient, HTTPClient}
import model.FlatOffer

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse

import java.util
import java.util.concurrent.{Executors, TimeUnit}
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

@main
def main(): Unit = {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val executorService = Executors.newScheduledThreadPool(4)
  val config = ConfigLoader.load()
  executorService.scheduleAtFixedRate(() => {
    val futureHttpResponse: Future[HttpResponse] = HTTPClient.sendRequest(system, config.url)
    val futureFlats: Future[util.List[FlatOffer]] = OLXParser.parse(futureHttpResponse)
    futureFlats.onComplete {
      case Failure(exception) => throw exception
      case Success(value) =>
        val cache = Cache.read()
        val difference: util.List[FlatOffer] = util.ArrayList()
        println("checking for offers")
        value.forEach(flat => {
          if (!cache.contains(flat)) {
            difference.add(flat)
          }
        })

        if (!difference.isEmpty) {
          cache.addAll(difference)
          Cache.write(cache)
          //send email
          val builder = StringBuilder()
          difference.forEach { elem =>
            builder.append(elem)
            builder.append("\n")
          }
          println("found differences")
          println(difference.toString)
          println("sending mail")
          EmailClient.sendEmail(config.email, builder.toString())
        }else{
          println("no difference found")
        }
    }
  }, 0, config.delayInSeconds, TimeUnit.SECONDS)


}

