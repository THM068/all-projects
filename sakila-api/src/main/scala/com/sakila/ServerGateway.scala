package com.sakila

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await

trait ServerGateway {
   def service: Service[Request, Response]

   lazy val running = {
      def simpleHttpServer = Http.serve(":9090", service)

      Await.ready(simpleHttpServer)
   }
}
