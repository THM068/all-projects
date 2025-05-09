package com.bill.tracker.microstream

import one.microstream.concurrency.XThreads

import java.util
import java.util.Collection
import zio._
trait FruitRepository {
  def list: ZIO[Any, Throwable, java.util.Collection[Fruit]]

  def create(fruit: FruitCommand): ZIO[Any, Throwable, Fruit]
}

case class FruitRepositoryImp(microStream: MicroStream[FruitContainer]) extends FruitRepository {
  override def list: ZIO[Any, Throwable, java.util.Collection[Fruit]] = for {
    dbRoot <- microStream.root()
      list = dbRoot.getFruits().values()
  } yield list

  override def create(fruitCommand: FruitCommand): ZIO[Any, Throwable, Fruit] = (for {
    dbRoot <- microStream.root()
    fruit = dbRoot.getFruits().put(fruitCommand.name, new Fruit(fruitCommand.name, fruitCommand.description))
    _ <-  microStream.store(dbRoot.getFruits())

  } yield fruit)

  def create(): ZIO[Any, Throwable, Fruit] = (for {
    _ <- Console.printLine("I have been called ****")
    dbRoot <- microStream.root()
    fruit = dbRoot.getFruits().put("test", new Fruit("", "fruitCommand.description"))
    _ <- microStream.store(dbRoot.getFruits())

  } yield fruit).repeatN(1)
}



object FruitRepository {
  val layer: ZLayer[MicroStream[FruitContainer], Nothing, FruitRepository] =
    ZLayer.fromFunction(FruitRepositoryImp.apply _)
}
