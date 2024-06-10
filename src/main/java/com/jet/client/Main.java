package com.jet.client;

import com.jet.client.manager.GameManager;

public class Main {

  public static void main(String[] args) {
    GameManager manager = new GameManager();
    manager.startOrJoin();
  }
}