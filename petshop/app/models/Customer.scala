package models

import com.typesafe.config.Config
import play.api.ConfigLoader
import play.api.libs.json.Json

case class Customer(name: String, age: Int)

object Customer {
  implicit val format = Json.format[Customer]
}

case class CustomerForm(name: String, age: Int)

case class CustomerConfig(val host: String, val port: Int, val isWeb: Boolean, val regions: Seq[String])
object CustomerConfig {
  implicit val configLoader: ConfigLoader[CustomerConfig] = new ConfigLoader[CustomerConfig] {

    override def load(rootConfig: Config, path: String): CustomerConfig = {
      val config = rootConfig.getConfig(path)
      CustomerConfig(
        host = config.getString("host"),
        port = config.getInt("port"),
        isWeb = config.getBoolean("isWeb"),
        regions = config.getStringList("regions").toArray.map(_.toString)
      )
    }
  }
}