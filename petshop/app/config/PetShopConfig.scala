package config

import com.google.inject.ImplementedBy
import models.CustomerConfig
import play.api.Configuration

import javax.inject._

@ImplementedBy(classOf[PetShopConfigImpl])
trait PetShopConfig {
  def host: String
  def port: Int
  def isWeb: Boolean
  def regions: Seq[String]
  def customerEnabled: Boolean

  def customerConfig: CustomerConfig
}

class PetShopConfigImpl @Inject()(config: Configuration) extends PetShopConfig {
  override def host: String = config.get[String]("customer.host")


  override def port: Int = config.get[Int]("customer.port")

  override def isWeb: Boolean = config.get[Boolean]("customer.isWeb")

  override def regions: Seq[String] = config.get[Seq[String]]("customer.regions")

  override def customerConfig: CustomerConfig = config.get[CustomerConfig]("customer")

  override def customerEnabled: Boolean = config.get[Boolean]("customer.enabled")
}
