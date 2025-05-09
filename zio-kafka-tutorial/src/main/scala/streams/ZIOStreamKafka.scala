package streams

import org.apache.kafka.clients.producer.ProducerRecord
import zio._
import zio.kafka.consumer._
import zio.kafka.producer.{Producer, ProducerSettings}
import zio.kafka.serde._
import zio.stream.ZStream

object ZIOStreamKafka extends ZIOAppDefault {
//  private val BOOSTRAP_SERVERS = List("localhost:9092", "localhost:9093")
  private val BOOSTRAP_SERVERS = List("bright-mammoth-6011-eu1-kafka.upstash.io:9092")
  private val KAFKA_TOPIC = "signups"

  private val producer: ZLayer[Any, Throwable, Producer] =
    ZLayer.scoped(
      Producer.make(
        ProducerSettings(BOOSTRAP_SERVERS)
          .withProperty("sasl.mechanism", "SCRAM-SHA-256")
          .withProperty("security.protocol", "SASL_SSL")
          .withProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"YnJpZ2h0LW1hbW1vdGgtNjAxMSQ-uUOt2Eoqa2H0S4zUc4QvfJ9OtvWToaI1PoU\" password=\"95ec241d9760453cb93eeb7296a1b5de\";")
          .withProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
          .withProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

      )
    )

  private val consumer: ZLayer[Any, Throwable, Consumer] =
    ZLayer.scoped(
      Consumer.make(
        ConsumerSettings(BOOSTRAP_SERVERS)
          .withProperty("sasl.mechanism", "SCRAM-SHA-256")
          .withProperty("security.protocol", "SASL_SSL")
          .withProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"YnJpZ2h0LW1hbW1vdGgtNjAxMSQ-uUOt2Eoqa2H0S4zUc4QvfJ9OtvWToaI1PoU\" password=\"95ec241d9760453cb93eeb7296a1b5de\";")
          .withProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
          .withProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
          .withProperty("auto.offset.reset", "earliest")
          .withGroupId("streaming-kaka-app")
      )
    )



  def run = {
    val p: ZStream[Producer, Throwable, Nothing] =
      ZStream
        .repeatZIO(Clock.currentDateTime)
        .schedule(Schedule.spaced(1.second))
        .map(time => new ProducerRecord(KAFKA_TOPIC, time.getMinute, s"$time -- Hello, World!"))
        .via(Producer.produceAll(Serde.int, Serde.string))
        .drain

    val c: ZStream[Consumer, Throwable, Nothing] =
      Consumer
        .plainStream(Subscription.topics(KAFKA_TOPIC), Serde.int, Serde.string)
        .tap(e => Console.printLine(e.value))
        .map(_.offset)
        .aggregateAsync(Consumer.offsetBatches)
        .mapZIO(_.commit)
        .drain

    (p merge c).runDrain.provide(producer, consumer)
  }

}
