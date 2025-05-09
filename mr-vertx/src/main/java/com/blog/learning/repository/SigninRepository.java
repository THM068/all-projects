package com.blog.learning.repository;

import com.blog.learning.repository.request.SigninInitRequest;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import jakarta.inject.Singleton;

@Singleton
public class SigninRepository {

  public Future<HttpResponse<JsonObject>> init(SigninInitRequest signinInitRequest) {
    return signinInitRequest.request().send();
  }
}
