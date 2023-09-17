package cs3500.pa04;

import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.controller.TestGameController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.GameType;
import java.io.IOException;
import java.net.Socket;

public class TestDriverMulti {
  public static void main(String[] args) {
    int wins = 0;
    int draws = 0;
    int losses = 0;
    for (int i = 0; i < 100; i++) {
      AiPlayer player = new AiPlayer();
      TestGameController controller = new TestGameController(player, GameType.MULTI);
      try {
        Socket socket = new Socket("localhost", 35001);
        ProxyController net = new ProxyController(socket, controller);
        net.run();
      } catch (IOException e) {
        System.err.println("Unable to connect to server!");
      }
      if(controller.result == null){
        continue;
      }
      if(controller.result.equals(GameResult.WIN)){
        wins++;
      }
      if(controller.result.equals(GameResult.DRAW)){
        draws++;
      }
      if(controller.result.equals(GameResult.LOSE)){
        losses++;
      }
    }
    System.out.println("won "+wins+", drew:"+draws+", lost: "+losses);
  }
}