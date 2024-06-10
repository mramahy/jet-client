package com.jet.client.model;


public enum GameMode {
  MANUAL, AUTOMATIC;

  public static GameMode getValueOf(String value){
    try {
      return valueOf(value);
    } catch (IllegalArgumentException exception){
      return null;
    }
  }
}
