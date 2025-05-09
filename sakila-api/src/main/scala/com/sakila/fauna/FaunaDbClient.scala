package com.sakila.fauna

import faunadb.FaunaClient

object FaunaDbClient {

  def client(): FaunaClient = FaunaClient(
    secret = "fnAE6wo2elACWQbagUL41dUZZHPf79UH9XxvH_AU",
    endpoint = "https://db.fauna.com" // Adjust for Region Groups
  )
}
