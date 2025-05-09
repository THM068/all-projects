package com.blog.learning.controllers.handlers;

import java.io.Serializable;

public class Context implements Serializable {
  private String provider;
  private String territory;
  private String proposition;

  public Context(String provider, String territory, String proposition) {
    this.provider = provider;
    this.territory = territory;
    this.proposition = proposition;
  }

  public static Context create(String provider, String territory, String proposition) {
    return new Context(provider, territory, proposition);
  }

  public String getProvider() {
    return provider;
  }

  public String getTerritory() {
    return territory;
  }

  public String getProposition() {
    return proposition;
  }






}
