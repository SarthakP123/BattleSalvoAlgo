package cs3500.pa04;

import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.controller.TestGameController;
import cs3500.pa04.model.AiPlayer;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TestDriver {
  public static void main(String[] args) {
    Map<Float, String> games = new TreeMap<>();
    ArrayList<Float> ratings = new ArrayList<>();
    for (int i = 0; i < 500; i++) {
      AiPlayer player = new AiPlayer();
      TestGameController controller = new TestGameController(player);
      try {
        Socket socket = new Socket("localhost", 35001);
        ProxyController net = new ProxyController(socket, controller);
        net.run();
      } catch (IOException e) {
        System.err.println("Unable to connect to server!");
      }
      if (controller.result != null) {
        games.put(controller.score(), controller.game());
        ratings.add(controller.score());
      }
    }
    for (float move : ratings) {
      System.out.print(move + " ");
    }
    System.out.println();
    int i = 0;
    for(String game : games.values()){
      if(i > 10){
        break;
      }
      System.out.println(game);
      i++;
    }
  }
}