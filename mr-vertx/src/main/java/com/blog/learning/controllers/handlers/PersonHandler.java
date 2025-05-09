package com.blog.learning.controllers.handlers;

import com.blog.learning.beans.PersonService;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Singleton;

@Singleton
public class PersonHandler {

  private PersonService personService;

  public PersonHandler(PersonService personService) {
    this.personService = personService;
  }

  public void getName(RoutingContext routingContext) {
    String name = this.personService.getName();

    final HttpServerResponse response = routingContext.response();
    final JsonObject result = new JsonObject().put("result", name);

    response.putHeader("Accept", "application/json");
    response
      .setStatusCode(200)
      .end(result.encode());
  }
}
