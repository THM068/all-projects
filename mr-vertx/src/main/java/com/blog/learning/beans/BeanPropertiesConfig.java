package com.blog.learning.beans;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("idris.engine")
public class BeanPropertiesConfig {
  private String url;


  public String getUrl() {
    return url;
  }
}
