package Wojna;

import java.util.ArrayList;
import java.util.List;

public class General {
    private List<Soldier> army;
    private int coins;

    public General(int initialCoins) {
        this.army = new ArrayList<>();
        this.coins = initialCoins;
    }

    public List<Soldier> getArmy() {
        return army;
    }

    public int getCoins() {
        return coins;
    }

    public void addSoldier(Soldier soldier) {
        int cost = 10 * soldier.getRank();
        if (coins >= cost) {
            army.add(soldier);
            coins -= cost;
        }
    }

    public void removeSoldier(Soldier soldier) {
        army.remove(soldier);
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public int getTotalArmyStrength() {
        return army.stream().mapToInt(Soldier::getStrength).sum();
    }

    public boolean hasArmy() {
        return !army.isEmpty();
    }
}
