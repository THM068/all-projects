package models

trait ProductService {
  def findAll(): List[Product] = Product.ListProduct()
}

object ProductService extends ProductService
