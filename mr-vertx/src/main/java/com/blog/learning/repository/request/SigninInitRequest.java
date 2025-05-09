package com.blog.learning.repository.request;

import com.blog.learning.controllers.handlers.Context;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;

public class SigninInitRequest extends BaseRequest {
  private final String idrisEngineBaseUrl;



  public SigninInitRequest(Vertx vertx, Context context, String idrisEngineBaseUrl) {
    super(vertx, context);
    this.idrisEngineBaseUrl = idrisEngineBaseUrl;

  }

  @Override
  public String path() {
    return "/idrisengine/signin/init";
  }

  public HttpRequest<JsonObject> request(){
    System.out.println(getContext().getProposition());
    return WebClient.create(getVertx())
      .postAbs("https://idrisengine-int.dev.ce.eu-central-1-aws.npottdc.sky/idrisengine/signin/init")
      .putHeader("X-SkyOTT-Provider", getContext().getProvider())
      .putHeader("X-SkyOTT-Territory", getContext().getTerritory())
      .putHeader("X-SkyOTT-Proposition", getContext().getProposition())
      .putHeader("Content-Type", "application/vnd.bridge.v1+json")
      .putHeader("X-SkyOTT-Platform", "COMPUTER")
      .putHeader("X-SkyOTT-Device", "MACOS")
      .as(BodyCodec.jsonObject());
//      .expect(ResponsePredicate.SC_OK);
  }


}
