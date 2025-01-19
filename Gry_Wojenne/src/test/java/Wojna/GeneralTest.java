package Wojna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneralTest {

    private General general;
    private General opponent;

    @BeforeEach
    void setUp() {
        general = new General("Bem", 100);
        opponent = new General("Anders", 100);
    }

    @Test
    void testInitialName() {
        assertEquals("Bem", general.getName());
    }

    @Test
    void testInitialGold() {
        assertEquals(100, general.getCoins());
    }

    @Test
    void testInitialArmySize() {
        assertEquals(1, general.getArmy().size()); // General starts with one soldier
    }

    @Test
    void testAddSoldierIncreasesArmySize() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        general.addSoldier(soldier);
        assertEquals(2, general.getArmy().size());
    }

    @Test
    void testAddSoldierContainsCorrectSoldier() {
        Soldier soldier = new Soldier(MilitaryRank.KAPRAL);
        general.addSoldier(soldier);
        assertTrue(general.getArmy().contains(soldier));
    }

    @Test
    void testRecruitSoldierSuccess() {
        boolean recruited = general.recruitSoldier(MilitaryRank.KAPRAL);
        assertTrue(recruited);
    }

    @Test
    void testRecruitSoldierDecreasesGold() {
        general.recruitSoldier(MilitaryRank.KAPRAL);
        assertEquals(80, general.getCoins());
    }

    @Test
    void testRecruitSoldierFailsWithoutEnoughGold() {
        general.reduceCoins(90); // Reduce gold to 10
        boolean recruited = general.recruitSoldier(MilitaryRank.MAJOR);
        assertFalse(recruited);
    }

    @Test
    void testManeuversIncreaseExperience() {
        general.Maneuvers(1);
        assertEquals(2, general.getArmy().get(0).getExperience());
    }

    @Test
    void testManeuversFailsWithoutEnoughGold() {
        general.reduceCoins(100); // Reduce gold to 5, less than cost of maneuvers
        general.Maneuvers(1);
        assertEquals(1, general.getArmy().get(0).getExperience()); // No experience increase
    }

    @Test
    void testGetStrengthOfArmyInitial() {
        int potential = general.getStrengthOfArmy();
        assertTrue(potential > 0);
    }

    @Test
    void testGetStrengthOfArmyIncreasesAfterRecruitment() {
        general.recruitSoldier(MilitaryRank.KAPRAL);
        int potential = general.getStrengthOfArmy();
        assertTrue(potential > 0);
    }

    @Test
    void testBattleWinGainsGold() {
        general.addSoldier(new Soldier(MilitaryRank.KAPRAL));
        general.battle(opponent);
        assertTrue(general.getCoins() > 100);
    }


    @Test
    void testBattleDrawRemovesSoldiers() {
        general.battle(opponent);
        assertTrue(general.getArmy().isEmpty() || opponent.getArmy().isEmpty());
    }

    @Test
    void testLoseGoldReduced() {
        general.lose(opponent);
        assertTrue(general.getCoins() < 100);
    }

    @Test
    void testLoseOpponentGainsGold() {
        general.lose(opponent);
        assertTrue(opponent.getCoins() > 100);
    }

    @Test
    void testLoseArmySizeReduced() {
        general.lose(opponent);
        assertTrue(general.getArmy().size() < 1);
    }

    @Test
    void testPrintArmyExecutesSuccessfully() {
        assertDoesNotThrow(() -> general.printArmy());
    }

    @Test
    void testReduceCoins() {
        assertEquals(90, general.reduceCoins(90));
    }

    @Test
    void testAddCoins() {
        general.addCoins(90);
        assertEquals(190, general.getCoins());
    }
}