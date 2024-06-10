package com.jet.client.manager;

import static com.jet.client.constants.Commands.JOIN;
import static com.jet.client.constants.Commands.START;
import static com.jet.client.model.GameMode.AUTOMATIC;
import static com.jet.client.model.GameMode.MANUAL;
import static com.jet.client.model.PlayerState.LOSE;
import static com.jet.client.model.PlayerState.WIN;

import com.jet.client.CommandLineInterface;
import com.jet.client.handler.GameAPIHandler;
import com.jet.client.model.GameDto;
import com.jet.client.model.GameMode;
import com.jet.client.model.GameState;
import com.jet.client.model.Move;
import com.jet.client.model.PlayerState;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class GameManager {
  GameAPIHandler handler;
  CommandLineInterface commandLineInterface;
  Random random ;


  public GameManager() {
    this.handler = new GameAPIHandler();
    this.commandLineInterface = new CommandLineInterface();
    this.random = new Random();
  }

  public void startOrJoin(){
    commandLineInterface.printIntroduction();
    String input = commandLineInterface.readPlayerInput();
    switch (input){
      case START:
        start();
        break;
      case JOIN:
        join();
        break;
      default:
    }
  }

  public void start() {
    commandLineInterface.printHowToStartGame();
    var input = commandLineInterface.readPlayerInput();
    var mode = GameMode.getValueOf(input.toUpperCase());

    if (mode == null) {
      commandLineInterface.invalidInput();
      commandLineInterface.exit();
    }

    GameMode gameMode = Objects.requireNonNull(mode);
    try {
      if (gameMode == AUTOMATIC) {
        playGameAutomatically();
      } else if (gameMode == GameMode.MANUAL) {
        playGameManually();
      }
    } catch (URISyntaxException | IOException | InterruptedException e) {
      commandLineInterface.gameIsUnavailable();
    }

    commandLineInterface.close();
  }

  private void playGameManually() throws URISyntaxException, IOException, InterruptedException {
    Integer number = Integer.valueOf(commandLineInterface.readPlayerInput());
    GameDto game = GameDto.builder().number(number).mode(MANUAL).build();
    var finalPlayerState = playManually(game);
    commandLineInterface.printResult(finalPlayerState);
  }

  private void playGameAutomatically() throws URISyntaxException, IOException, InterruptedException {
    Integer number = random.nextInt(3, 1000);
    var game = GameDto.builder().number(number).mode(AUTOMATIC).build();
    game = handler.createGame(game);
    commandLineInterface.waitingForPlayersToJoin();

    while (game.getState() == GameState.OPEN) {
      game = handler.checkGameState(game.getId());
    }

    var finalPlayerState = playAutomatically(game);
    commandLineInterface.printResult(finalPlayerState);
  }


  private PlayerState playManually(GameDto game)
      throws URISyntaxException, IOException, InterruptedException {
    var updatedGame = handler.checkGameState(game.getId());
    if (updatedGame.getNumber().equals(1)){
      return LOSE;
    }
    if (updatedGame.getNumber().equals(game.getNumber())){
      return playManually(updatedGame);
    } else {
      var move = Move.getValueOf(commandLineInterface.readPlayerInput());
      if (move == null){
        commandLineInterface.invalidInput();
        playManually(game);
      }
      var moveGame = handler.makeMove(updatedGame, move);
      if (moveGame.getNumber().equals(1)) {
        return WIN;
      } else {
        return playManually(moveGame);
      }
    }
  }

  private PlayerState playAutomatically(GameDto game)
      throws URISyntaxException, IOException, InterruptedException {
    var updatedGame = handler.checkGameState(game.getId());
    if (updatedGame.getNumber().equals(1)){
      return LOSE;
    }
    if (updatedGame.getNumber().equals(game.getNumber())){
      return playAutomatically(updatedGame);
    } else {
      var index = random.nextInt(0,2);
      var move = Move.values()[index];
      var moveGame = handler.makeMove(updatedGame, move);

      if (moveGame.getNumber().equals(1)) {
        return WIN;
      } else {
        return playAutomatically(moveGame);
      }
    }
  }


  public void join(){
    try {
      GameDto game = handler.joinGame();
      if(game == null){
        commandLineInterface.printNoGamesToJoin();
        startOrJoin();
        return;
      }

      var input = commandLineInterface.readPlayerInput();
      var mode = GameMode.getValueOf(input.toUpperCase());

    } catch (URISyntaxException | IOException | InterruptedException  e) {
      commandLineInterface.gameIsUnavailable();
    }
  }
}
