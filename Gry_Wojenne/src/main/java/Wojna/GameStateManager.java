package Wojna;

import java.io.*;
import java.util.Scanner;

public class GameStateManager {
    General general1;
    General general2;
    General currentGeneral;
    private final Logger logger;
    private final Secretary secretary;
    private boolean gameRunning;
    private int turn = 1;

    public GameStateManager() {
        logger = new Logger();
        secretary = new Secretary();
        gameRunning = true;
    }

    public void startGame(int mode, String general1Name, String general2Name, int maxTurns) {
        addGenerals(general1Name, general2Name);

        currentGeneral = general1;
        int currentTurn = 1;

        while (gameRunning && currentTurn <= maxTurns) {
            saveGame();
            processTurn(currentGeneral);
            turn++;
            switchToNextGeneral();
            secretary.displayGeneralsState(general1, general2);
            currentTurn++;
        }
        endGameReport();
    }

    protected void addGenerals(String general1Name, String general2Name) {
        general1 = new General(general1Name, 50);
        general2 = new General(general2Name, 50);

        logger.logAction("Game added Generals: " + general1.getName() + " and " + general2.getName());
    }

    private void processTurn(General general) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n========== " + general.getName() + "'s Turn ==========");
            System.out.println("Coins: " + general.getCoins());
            System.out.println("Army Strength: " + general.getStrengthOfArmy());
            System.out.println("1. Make Maneuver");
            System.out.println("2. Battle");
            System.out.println("3. Recruit Soldier");
            System.out.println("4. Save Game");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    makeManeuvers(general);
                    break;
                case 2:
                    battle();
                    break;
                case 3:
                    recruitSoldier(general);
                    break;
                case 4:
                    saveGame();
                    break;
                case 5:
                    exitGame();
                    break;
                default:
                    System.out.println("Wrong option.");
            }
        }
    }

    void makeManeuvers(General general) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter number of soldiers to use:");
            int numSoldiers = scanner.nextInt();

            if (general.Maneuvers(numSoldiers)) {
                logger.logAction(general.getName() + " used " + numSoldiers + " soldiers.");
            }
        }
    }

    void battle() {
        if (general1.getStrengthOfArmy() == 0 || general2.getStrengthOfArmy() == 0) {
            System.out.println("One of the generals has no army. Battle is over before it started.");
            return;
        }

        System.out.println("Battle initiated between " + general1.getName() + " and " + general2.getName());
        general1.battle(general2);
        logger.logAction("Battle occurred between " + general1.getName() + " and " + general2.getName());
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    void recruitSoldier(General general) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choose rank to recruit:");
            System.out.println("1. SZEREGOWY (Cost: 10)");
            System.out.println("2. KAPRAL (Cost: 20)");
            System.out.println("3. KAPITAN (Cost: 30)");
            System.out.println("4. MAJOR (Cost: 40)");

            int choice = scanner.nextInt();
            MilitaryRank rank;

            switch (choice) {
                case 1:
                    rank = MilitaryRank.SZEREGOWY;
                    break;
                case 2:
                    rank = MilitaryRank.KAPRAL;
                    break;
                case 3:
                    rank = MilitaryRank.KAPITAN;
                    break;
                case 4:
                    rank = MilitaryRank.MAJOR;
                    break;
                default:
                    System.out.println("Invalid rank. No soldier recruited.");
                    return;
            }

            boolean success = general.recruitSoldier(rank);
            if (success) {
                logger.logAction(general.getName() + " recruited a " + rank);
            }
        }
    }

    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("currentSave" + turn + ".dat"))) {
            oos.writeObject(general1);
            oos.writeObject(general2);
            oos.writeObject(turn);
            oos.writeObject(currentGeneral);
            logger.logAction("Game saved successfully.");
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }

    private String[] findSaves() {
        File directory = new File("./");
        File[] fList = directory.listFiles((dir, name) -> name.endsWith(".dat"));
        if (fList == null) return new String[0];

        String[] fNames = new String[fList.length];
        for (int i = 0; i < fList.length; i++) {
            fNames[i] = fList[i].getName();
            System.out.println((i + 1) + ". " + fNames[i]);
        }
        return fNames;
    }

    public boolean loadGame() {
        String[] saves = findSaves();

        if (saves.length == 0) {
            System.out.println("No save files found.");
            return false;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choose a save file to load:");
            for (int i = 0; i < saves.length; i++) {
                System.out.printf("%d. %s%n", i + 1, saves[i]);
            }

            int choice = -1;
            while (true) {
                System.out.print("Enter the number of the save file: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    if (choice >= 1 && choice <= saves.length) {
                        break;
                    } else {
                        System.out.println("Invalid choice. Please select a valid save file.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }

            String selectedSaveFile = saves[choice - 1];
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedSaveFile))) {
                general1 = (General) ois.readObject();
                general2 = (General) ois.readObject();
                turn = (Integer) ois.readObject();
                currentGeneral = (General) ois.readObject();

                logger.logAction("Game loaded successfully from " + selectedSaveFile);
                System.out.println("Game loaded successfully.");
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("Error: Save file not found.");
            } catch (IOException e) {
                System.out.println("Error: Failed to read save file. " + e);
            } catch (ClassNotFoundException e) {
                System.out.println("Error: Save file contains invalid data.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred while loading the save file.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
        }
        return false;
    }

    void switchToNextGeneral() {
        currentGeneral = (currentGeneral == general1) ? general2 : general1;
    }

    void exitGame() {
        gameRunning = false;
        System.out.println("Exiting the game.");
    }

    private void endGameReport() {
        System.out.println("\nFinal Game State:");
        general1.printArmy();
        general2.printArmy();
        logger.printLogs();
    }
}
