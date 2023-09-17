package cs3500.pa04;

import cs3500.pa04.controller.LocalGameController;
import cs3500.pa04.controller.NetGameController;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.InputPlayer;
import cs3500.pa04.View.GameView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Represents the driver class of the game
 */
public class Driver {
  public static void main(String[] args) {
    AiPlayer player = new AiPlayer();
    if (args.length == 0) {
      GameView view = new GameView(new InputStreamReader(System.in), System.out);
      InputPlayer player2 = new InputPlayer(view);
      LocalGameController controller = new LocalGameController(view, player, player2);
      controller.run();
    } else if (args.length == 2) {
      NetGameController controller = new NetGameController(player);
      try {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        ProxyController net = new ProxyController(socket, controller);
        net.run();
      } catch (IOException e) {
        System.err.println("Unable to connect to server!");
      }
    }
  }
}


