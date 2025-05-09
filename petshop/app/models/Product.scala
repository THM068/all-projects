package models

//create a case class Product with productId, productType, colour, availability:boolean and size:String
case class Product(productId: Int,
                   productType: String,
                   colour: String,
                   availability: Boolean,
                   size: String)

object Product {
  def ListProduct(): List[Product] = List(
    Product(1234, "Shirt", "Red", true, "L"),
    Product(5678, "Trousers", "Black", false, "M"),
    Product(9876, "Shirt", "Green", true, "S")
  )
}
