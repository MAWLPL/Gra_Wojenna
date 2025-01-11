package Wojna;

import java.util.List;

public class Secretary {

    public void reportArmy(General general) {
        List<Soldier> army = general.getArmy();
        System.out.println("Raport armii generała:");
        for (Soldier soldier : army) {
            System.out.println("Stopień: " + soldier.getRank() + ", Doświadczenie: " + soldier.getExperience());
        }
    }

    public void logAction(String action) {
        System.out.println("Działanie: " + action);
    }
}
