package Wojna;

public class Validator {

    public boolean validateGeneral(General general) {
        return general != null && general.getCoins() >= 0 && general.getArmy().size() >= 0;
    }

    public boolean validateSoldier(Soldier soldier) {
        return soldier != null && soldier.getRank() > 0 && soldier.getExperience() > 0;
    }
}