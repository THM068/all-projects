package models

import faunadb.FaunaClient

object FaunaDbClient {

  val faunaDbClient = FaunaClient(
    secret = "fnAE89F-N3ACWRJH6RglI-slJ2REDM3SHrSCwXiF",
    endpoint = "https://db.fauna.com" // Adjust for Region Groups
  )


}
