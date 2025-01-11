package Wojna;

import java.io.*;

public class GameStateManager {

    public void saveGameState(General general, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(general);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public General loadGameState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (General) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
