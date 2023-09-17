package cs3500.pa04.controller;



import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.GameType;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class ProxyControllerTest {

  private ProxyController proxyController;
  private ByteArrayOutputStream outputStream;
  private Mocket mocket;
  private NetGameController netGameController;

  @BeforeEach
  void setUp() throws Exception {
    List<String> messages = Arrays.asList(
        "{\"method-name\":\"join\", \"arguments\": {}}",
        "{\"method-name\":\"setup\", \"arguments\": " +
            "{\"width\":6, \"height\":6, \"fleet-spec\": {\"CARRIER\": 1, \"BATTLESHIP\": 1, " +
            "\"DESTROYER\": 1, \"SUBMARINE\": 1}}}",
        "{\"method-name\":\"take-shots\", \"arguments\": {}}",
        "{\"method-name\":\"report-damage\", \"arguments\": {\"coordinates\": [{\"x\": 3, " +
            "\"y\": 2}]}}",
        "{\"method-name\":\"successful-hits\", \"arguments\": {\"coordinates\": [{\"x\": 3, " +
            "\"y\": 2}]}}",
        "{\"method-name\":\"end-game\", \"arguments\": {\"result\": \"WIN\", \"reason\": " +
            "\"Player 1 sank all of Player 2's ships\"}}"
    );

    this.outputStream = new ByteArrayOutputStream();
    mocket = new Mocket(outputStream, messages);
    AiPlayer player = new AiPlayer();
    netGameController = new NetGameController(player, GameType.SINGLE);
    this.proxyController = new ProxyController(mocket, netGameController);
  }

  @Test
  void run() {
    proxyController.run();
    assertTrue(outputStream.toString().trim()
        .contains("roadkillsanta"));

  }
  @Test
  void testJoinMessageSent() {
    proxyController.run();
    assertTrue(outputStream.toString().contains("\"method-name\":\"join\""));
  }

  @Test
  void testSetupMessageSent() {
    proxyController.run();
    assertTrue(outputStream.toString().contains("\"method-name\":\"setup\""));
  }

  @Test
  void testTakeShotsMessageSent() {
    proxyController.run();
    assertTrue(outputStream.toString().contains("\"method-name\":\"take-shots\""));
  }

  @Test
  void testReportDamageMessageSent() {
    proxyController.run();
    assertTrue(outputStream.toString().contains("\"method-name\":\"report-damage\""));
  }

  @Test
  void testSuccessfulHitsMessageSent() {
    proxyController.run();
    assertTrue(outputStream.toString().contains("\"method-name\":\"successful-hits\""));
  }

  @Test
  void testEndGameMessageSent() {
    proxyController.run();
    assertTrue(outputStream.toString().contains("\"method-name\":\"end-game\""));
  }
}