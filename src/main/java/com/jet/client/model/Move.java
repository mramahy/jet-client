package com.jet.client.model;

public enum Move {
  PLUS, MINUS, ZERO;

  public static Move getValueOf(String value){
    try {
      return valueOf(value);
    } catch (IllegalArgumentException exception){
      return null;
    }
  }
}
