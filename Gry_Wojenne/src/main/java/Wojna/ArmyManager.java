package Wojna;

import java.util.List;
import java.util.Random;

public class ArmyManager {

    public void executeManeuver(General general) {
        List<Soldier> army = general.getArmy();
        int cost = army.size();
        if (general.getCoins() >= cost) {
            general.addCoins(-cost);
            for (Soldier soldier : army) {
                soldier.increaseExperience();
                soldier.promote();
            }
        }
    }

    public General executeBattle(General general1, General general2) {
        int strength1 = general1.getTotalArmyStrength();
        int strength2 = general2.getTotalArmyStrength();

        if (strength1 > strength2) {
            general1.addCoins(10);
            return general1;
        } else if (strength2 > strength1) {
            general2.addCoins(10);
            return general2;
        } else {
            removeRandomSoldier(general1);
            removeRandomSoldier(general2);
            return null; // Remis
        }
    }

    private void removeRandomSoldier(General general) {
        if (general.hasArmy()) {
            Random random = new Random();
            int index = random.nextInt(general.getArmy().size());
            general.getArmy().remove(index);
        }
    }
}
