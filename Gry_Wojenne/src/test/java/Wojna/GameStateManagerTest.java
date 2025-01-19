package Wojna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


class GameStateManagerTest {
    private GameStateManager gameStateManager;


    @BeforeEach
    void setUp() {
        gameStateManager = new GameStateManager();
    }

    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testGeneral1IsInitialized() {
        gameStateManager.addGenerals("General A", "General B");
        assertNotNull(gameStateManager.general1);
    }

    @Test
    void testGeneral2IsInitialized() {
        gameStateManager.addGenerals("General A", "General B");
        assertNotNull(gameStateManager.general2);
    }

    @Test
    void testGeneral1Name() {
        gameStateManager.addGenerals("General A", "General B");
        assertEquals("General A", gameStateManager.general1.getName());
    }

    @Test
    void testGeneral2Name() {
        gameStateManager.addGenerals("General A", "General B");
        assertEquals("General B", gameStateManager.general2.getName());
    }

    @Test
    void testRecruitSoldierAddsToArmy() {
        General general = new General("General A", 100);
        provideInput("1\n");
        gameStateManager.recruitSoldier(general);
        assertEquals(2, general.getArmySize());
    }

    @Test
    void testRecruitSoldierDeductsGold() {
        General general = new General("General A", 100);
        provideInput("1\n");
        gameStateManager.recruitSoldier(general);
        assertEquals(90, general.getCoins()); // Assuming SZEREGOWY costs 10 gold
    }

    @Test
    void testRecruitSoldierFailsForInsufficientFunds() {
        General general = new General("General A", 5);
        provideInput("1\n");
        gameStateManager.recruitSoldier(general);
        assertEquals(1, general.getArmySize());
    }

    @Test
    void testGoldUnchangedWhenRecruitmentFails() {
        General general = new General("General A", 5);
        provideInput("1\n");
        gameStateManager.recruitSoldier(general);
        assertEquals(5, general.getCoins()); // Shouldn't deduct gold if recruitment fails
    }

    @Test
    void testBattleReducesGeneral1ArmyPotential() {
        General general1 = new General("General A", 100);
        General general2 = new General("General B", 100);

        general1.recruitSoldier(MilitaryRank.SZEREGOWY);
        general2.recruitSoldier(MilitaryRank.SZEREGOWY);

        gameStateManager.general1 = general1;
        gameStateManager.general2 = general2;

        gameStateManager.battle();
        assertTrue(general1.getStrengthOfArmy() < 100);
    }

    @Test
    void testBattleReducesGeneral2ArmyPotential() {
        General general1 = new General("General A", 100);
        General general2 = new General("General B", 100);

        general1.recruitSoldier(MilitaryRank.SZEREGOWY);
        general2.recruitSoldier(MilitaryRank.SZEREGOWY);

        gameStateManager.general1 = general1;
        gameStateManager.general2 = general2;

        gameStateManager.battle();
        assertTrue(general2.getStrengthOfArmy() < 100);
    }

    @Test
    void testSaveGameCreatesFile() {
        provideInput("4\n");
        gameStateManager.startGame(1, "General A", "General B", 1);
        File file = new File("savegame-turn1.dat");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void testLoadGameSucceeds() {
        provideInput("4\n");
        gameStateManager.startGame(1, "General A", "General B", 1);
        provideInput("1\n");
        boolean loaded = gameStateManager.loadGame();
        assertTrue(loaded);
    }

    @Test
    void testSwitchToNextGeneral() {
        gameStateManager.general1 = new General("General A", 100);
        gameStateManager.general2 = new General("General B", 100);
        gameStateManager.currentGeneral = gameStateManager.general1;

        gameStateManager.switchToNextGeneral();
        assertEquals(gameStateManager.general2, gameStateManager.currentGeneral);
    }

    @Test
    void testGameIsRunningInitially() {
        assertTrue(gameStateManager.isGameRunning());
    }

    @Test
    void testExitGameStopsRunning() {
        gameStateManager.exitGame();
        assertFalse(gameStateManager.isGameRunning());
    }
}
