package Wojna;

public class Secretary {
    public void displayGeneralsState(General general1, General general2) {
        System.out.println("\n===== Generals' State =====");

        System.out.println("General 1: " + general1.getName());
        System.out.println("Coins: " + general1.getCoins());
        System.out.println("Army Strenght: " + general1.getStrengthOfArmy());
        System.out.println("Army Composition:");
        general1.printArmy();
        System.out.println("--------------------------");

        System.out.println("General 2: " + general2.getName());
        System.out.println("Coins: " + general2.getCoins());
        System.out.println("Army Strenght: " + general2.getStrengthOfArmy());
        System.out.println("Army Composition:");
        general2.printArmy();
        System.out.println("==========================\n");
    }
}




