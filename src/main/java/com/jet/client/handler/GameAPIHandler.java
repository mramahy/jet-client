package com.jet.client.handler;

import com.google.gson.Gson;
import com.jet.client.model.GameDto;
import com.jet.client.model.Move;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GameAPIHandler {
  private final HttpClient client;
  private static final String SERVER_URL = "http://localhost:8080/games";

  public GameAPIHandler() {
    this.client = HttpClient.newHttpClient();
  }

  public GameDto makeMove(GameDto game, Move move)
      throws URISyntaxException, IOException, InterruptedException {
    String payload = toJson(game);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("%s/games/%s/move/%s".formatted(SERVER_URL, game, move)))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(payload))
        .build();
    var response =client.send(request, HttpResponse.BodyHandlers.ofString());
    return fromJson(response.body());
  }

  public GameDto checkGameState(String gameId)
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("%s/%s".formatted(SERVER_URL, gameId)))
        .GET()
        .build();
    var response =client.send(request, HttpResponse.BodyHandlers.ofString());
    return fromJson(response.body());
  }

  public GameDto joinGame()
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI(SERVER_URL))
        .PUT(HttpRequest.BodyPublishers.noBody())
        .build();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if(response.statusCode() == 500){

    }
    return fromJson(response.body());
  }

  public GameDto createGame(GameDto game)
      throws URISyntaxException, IOException, InterruptedException {
    String payload = toJson(game);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI(SERVER_URL))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(payload))
        .build();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return fromJson(response.body());
  }

  private GameDto fromJson(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, GameDto.class);
  }

  private String toJson(GameDto game) {
    Gson gson = new Gson();
    return gson.toJson(game);
  }
}
