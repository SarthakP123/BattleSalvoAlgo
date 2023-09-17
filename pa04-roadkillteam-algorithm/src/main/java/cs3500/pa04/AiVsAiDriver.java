package cs3500.pa04;

import cs3500.pa04.controller.LocalGameController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.oldmodel.OldAiPlayer;
import java.io.InputStreamReader;

public class AiVsAiDriver {
  public static void main(String[] args) {
    AiPlayer player = new AiPlayer();
    OldAiPlayer player2 = new OldAiPlayer();
    cs3500.pa04.View.GameView
        view = new cs3500.pa04.View.GameView(new InputStreamReader(System.in), System.out);
    LocalGameController controller = new LocalGameController(view, player, player2);
    controller.run();
  }
}

