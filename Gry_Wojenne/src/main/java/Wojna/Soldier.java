package Wojna;

public class Soldier {
    private int rank;
    private int experience;

    public Soldier(int rank) {
        this.rank = rank;
        this.experience = 1;
    }

    public int getRank() {
        return rank;
    }

    public int getExperience() {
        return experience;
    }

    public void increaseExperience() {
        experience++;
    }

    public int getStrength() {
        return rank * experience;
    }

    public boolean isAlive() {
        return experience > 0;
    }

    public void promote() {
        if (experience >= 5 * rank) {
            rank++;
            experience = 1;
        }
    }

    public void loseExperience() {
        experience--;
    }
}