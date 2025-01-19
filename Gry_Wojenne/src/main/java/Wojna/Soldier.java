package Wojna;

import java.io.Serializable;

enum MilitaryRank {
    SZEREGOWY(1),
    KAPRAL(2),
    KAPITAN(3),
    MAJOR(4);

    public final int value;

    MilitaryRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class Soldier implements Serializable {
    private static final long serialVersionUID = 1L;
    private MilitaryRank rank;
    private int experience;

    public Soldier(MilitaryRank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null.");
        }
        this.rank = rank;
        this.experience = 1;
    }

    public int getPower() {
        return rank.getValue() * experience;
    }

    public void increaseExperience() {
        int maxExperience = rank.getValue() * 5;

        if (experience >= maxExperience) {
            throw new IllegalStateException("Cannot increase experience beyond the maximum for rank: " + rank.name());
        }

        experience++;

        if (experience == maxExperience) {
            MilitaryRank nextRank = getPromotion();
            if (nextRank != null) {
                rank = nextRank;
                experience = 1;
            }
        }
    }

    public boolean decreaseExperience() {
        if (experience <= 0) {
            throw new IllegalStateException("Cannot decrease experience below 0.");
        }

        experience--;
        return experience > 0;
    }

    private MilitaryRank getPromotion() {
        MilitaryRank[] ranks = MilitaryRank.values();
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i] == rank && i + 1 < ranks.length) {
                return ranks[i + 1];
            }
        }
        return null;
    }

    public int getExperience() {
        return experience;
    }

    public MilitaryRank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank.name() + " (Value: " + rank.getValue() + ", Experience: " + experience + ")";
    }
}


