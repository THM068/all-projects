package com.bill.tracker.server.routes

import com.bill.tracker.microstream.{FruitCommand, FruitRepository, Fruits}
import com.bill.tracker.model.CategoryDTO
import com.bill.tracker.server.routes.HandleErrors.catchException
import com.bill.tracker.server.routes.ServerUtils.parseBody
import zio.{Schedule, ZIO, ZLayer}
import zio.http.ZClient.request
import zio.http.{Method, Request, Response, Routes, handler}
import zio.json.EncoderOps

import scala.jdk.CollectionConverters._
case class FruitRoutes(fruitRepository: FruitRepository) {

  val create = Method.POST / "fruit"  -> handler { (request: Request ) =>
    (for {
      fruitCommand <- parseBody[FruitCommand](request)
      fruit <- fruitRepository.create(fruitCommand).retry(Schedule.recurs(1))
    } yield Response.json(fruit.toJson))
  }

  val list = Method.GET / "fruit" -> handler {
    for {
      fruits <- fruitRepository.list
    } yield Response.json(Fruits(fruits.asScala.toList).toJson)
  }

  val apps = Routes(create, list)
    .handleError(HandleErrors.handle)
    .toHttpApp


}

object FruitRoutes {
  val layer: ZLayer[FruitRepository, Nothing, FruitRoutes] =
    ZLayer.fromFunction(FruitRoutes.apply _)

}
