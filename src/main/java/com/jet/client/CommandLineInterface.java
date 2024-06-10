package com.jet.client;

import static com.jet.client.constants.CliMessages.HOW_TO_START_GAME;
import static com.jet.client.constants.CliMessages.READ_COMMAND;
import static com.jet.client.constants.CliMessages.WELCOME_MESSAGE;
import static java.lang.System.*;

import com.jet.client.model.PlayerState;
import java.util.Scanner;

public class CommandLineInterface {

  private final Scanner scanner;

  public CommandLineInterface() {
    scanner = new Scanner(in);
  }
  public void printIntroduction(){
    out.flush();
    out.println(WELCOME_MESSAGE);
  }

  public String readPlayerInput(){
    return scanner.nextLine();
  }

  public void exit(){
    out.flush();
    out.println("Exiting the game...");
    close();
  }

  public void invalidInput(){
    out.println("Invalid input!");
  }

  public void printHowToStartGame() {
    out.println(HOW_TO_START_GAME);
    out.println(READ_COMMAND);
  }

  public void close() {
    scanner.close();
  }

  public void gameIsUnavailable() {
    out.println("Game is  Unavailable at the moment!");
    out.println("Exiting the game...");
    close();
  }

  public void printLoss() {
    out.println("You lose!");
  }

  public void printNoGamesToJoin() {
    out.println("No Games to join are found! will return to main menu");
  }

  public void printResult(PlayerState finalPlayerState) {
    switch (finalPlayerState){
      case WIN -> out.println("Congratulations You won!!");
      case LOSE -> out.println("You Lost! Good luck next time!");
    }
  }

  public void waitingForPlayersToJoin() {
    out.println("Waiting for player to join...");
  }
}
