package Wojna;

public class Game {

    public static void main(String[] args) {
        General general1 = new General(100);
        General general2 = new General(100);

        general1.addSoldier(new Soldier(1));
        general2.addSoldier(new Soldier(2));

        ArmyManager armyManager = new ArmyManager();
        Secretary secretary = new Secretary();

        armyManager.executeManeuver(general1);
        armyManager.executeManeuver(general2);

        secretary.reportArmy(general1);
        secretary.reportArmy(general2);

        General winner = armyManager.executeBattle(general1, general2);
        if (winner != null) {
            System.out.println("Zwyciężył generał z " + winner.getCoins() + " monetami!");
        } else {
            System.out.println("Bitwa zakończyła się remisem.");
        }
    }
}
