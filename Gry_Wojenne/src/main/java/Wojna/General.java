package Wojna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class General implements Serializable {
    private static final long serialVersionUID = 1L;
    private int coins;
    private String name;
    private ArrayList<Soldier> army;

    public General(String name, int initialCoins) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("General's name cannot be null or empty.");
        }
        this.name = name;

        if (initialCoins < 0) {
            throw new IllegalArgumentException("Initial coins cannot be negative.");
        }
        this.coins = initialCoins;

        this.army = new ArrayList<>();
        army.add(new Soldier(MilitaryRank.SZEREGOWY));
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins) {
        if (coins <= 0) {
            throw new IllegalArgumentException("Added coins must be greater than 0.");
        }
        this.coins += coins;
    }

    public int reduceCoins(int coins) {
        if (coins <= 0) {
            throw new IllegalArgumentException("Reduced coins must be greater than 0.");
        }
        if (this.coins < coins) {
            throw new IllegalStateException("Not enough coins to reduce.");
        }
        this.coins -= coins;
        return coins;
    }

    public boolean Maneuvers(int numberOfSoldiers) {
        if (numberOfSoldiers < 1 || numberOfSoldiers > army.size()) {
            System.out.println("Invalid number of soldiers for maneuvers.");
            return false;
        }
        if (coins < getCostOfManeuvers()) {
            System.out.println("Not enough money, sir!");
            return false;
        }
        for (int i = 0; i < numberOfSoldiers; i++) {
            army.get(i).increaseExperience();
        }
        coins -= getCostOfManeuvers();
        return true;
    }

    private int getCostOfManeuvers() {
        int cost = 0;
        for (Soldier soldier : army) {
            cost += soldier.getRank().getValue();
        }
        return cost;
    }

    public void addSoldier(Soldier soldier) {
        if (soldier == null) {
            throw new IllegalArgumentException("Soldier cannot be null.");
        }
        army.add(soldier);
    }

    public int getStrengthOfArmy() {
        int strength = 0;
        for (Soldier soldier : army) {
            strength += soldier.getPower();
        }
        return strength;
    }

    public void battle(General opponent) {
        if (opponent == null) {
            throw new IllegalArgumentException("Opponent cannot be null.");
        }
        if (army.isEmpty() || opponent.army.isEmpty()) {
            System.out.println("One of the armies is empty. Battle is over before it started.");
            return;
        }

        int firstArmyStrength = getStrengthOfArmy();
        int secondArmyStrength = opponent.getStrengthOfArmy();
        Random random = new Random();

        if (firstArmyStrength == secondArmyStrength) {
            System.out.println("It's a tie! Both armies lose a random soldier.");
            army.remove(random.nextInt(army.size()));
            opponent.army.remove(random.nextInt(opponent.army.size()));
        } else if (firstArmyStrength < secondArmyStrength) {
            System.out.println(opponent.getName() + " wins the battle!");
            lose(opponent);
        } else {
            System.out.println(name + " wins the battle!");
            opponent.lose(this);
        }
    }

    public void lose(General opponent) {
        if (opponent == null) {
            throw new IllegalArgumentException("Opponent cannot be null.");
        }
        int stolenCoins = reduceCoins((int) (getCoins() * 0.1));
        opponent.addCoins(stolenCoins);

        Iterator<Soldier> iterator = army.iterator();
        while (iterator.hasNext()) {
            Soldier soldier = iterator.next();
            if (!soldier.decreaseExperience()) {
                iterator.remove();
            }
        }
    }

    public List<Soldier> getArmy() {
        return new ArrayList<>(army);
    }

    public boolean recruitSoldier(MilitaryRank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null.");
        }
        int cost = 10 * rank.getValue();
        if (coins >= cost) {
            army.add(new Soldier(rank));
            coins -= cost;
            System.out.println(name + " recruited a " + rank);
            return true;
        } else {
            System.out.println("Not enough money to recruit a soldier of rank: " + rank);
            return false;
        }
    }

    public void printArmy() {
        if (army.isEmpty()) {
            System.out.println("The army is empty.");
        } else {
            for (Soldier soldier : army) {
                System.out.println(soldier);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getArmySize() {
        return army.size();
    }
}
