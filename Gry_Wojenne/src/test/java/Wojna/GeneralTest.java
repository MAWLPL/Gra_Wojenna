package Wojna;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneralTest {
    @Test
    public void testAddSoldier() {
        General general = new General(100);
        Soldier soldier = new Soldier(1);
        general.addSoldier(soldier);
        assertEquals(1, general.getArmy().size());
        assertEquals(90, general.getCoins());
    }

    @Test
    public void testRemoveSoldier() {
        General general = new General(100);
        Soldier soldier = new Soldier(1);
        general.addSoldier(soldier);
        general.removeSoldier(soldier);
        assertEquals(0, general.getArmy().size());
    }
}
