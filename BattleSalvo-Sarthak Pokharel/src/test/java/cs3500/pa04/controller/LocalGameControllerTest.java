//package cs3500.pa04.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import cs3500.pa04.View.GameView;
//import cs3500.pa04.model.AiPlayer;
//import cs3500.pa04.model.InputPlayer;
//import cs3500.pa04.model.Ship;
//import java.io.StringReader;
//import java.util.List;
//import java.util.Random;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class LocalGameControllerTest {
//  private  GameView view;
//  private Appendable appendable;
//  private Readable readable;
//  private AiPlayer aiPlayer;
//  private InputPlayer inputPlayer;
//  private List<Ship> aiShips1;
//  private List<Ship> aiShips2;
//  private LocalGameController controller;
//  private String boardResult;
//
// @BeforeEach
//  void setup() {
//   appendable = new StringBuilder("");
//   Random randomSeed = new Random(1);
//   aiPlayer = new AiPlayer(randomSeed);
//   inputPlayer = new InputPlayer(view);
//   readable = new StringReader("");
//   view = new GameView(readable, appendable);
//
//   controller = new LocalGameController(view, aiPlayer, inputPlayer);
//   boardResult = "Please enter your player name: \n" +
//       "SETUP\n" +
//       "SYSTEM: Game setup\n" +
//       "Please enter a valid height and width below (two numbers between 6 and 15):\n" +
//       "Please enter a valid grouping of ships.\n" +
//       "Opponent's Board: \n" +
//       " - - - - - -\n" +
//       " - - - - - -\n" +
//       " - - - - - -\n" +
//       " - - - - - -\n" +
//       " - - - - - -\n" +
//       " - - - - - -\n" +
//       "\n" +
//       "Your Board: \n" +
//       " C - - - - B\n" +
//       " C - - - D B\n" +
//       " C - - - D B\n" +
//       " C S S S D B\n" +
//       " C - - - D B\n" +
//       " C - - - - -\n" +
//       "\n" +
//       "Please enter 4 coordinates to hit:\n" +
//       "You win!\n" +
//       "AI has no ships remaining";
// }
//
//  @Test
//  void run() {
//  readable = new StringReader("roadKillSanta\n" + "6 6\n" +
//      "1 1 1 1" +
//      "\n0 0\n0 1\n 0 2\n 0 3\n 0 4"
//  );
//  view = new GameView(readable, appendable);
//  controller = new LocalGameController(view, aiPlayer, inputPlayer);
//  controller.run();
//  //assertEquals(boardResult, appendable.toString());
//  }
//
//  @Test
//  void endGame() {
//  }
//}