package gamesleague;

import java.io.*;
import java.util.ArrayList;

public class Player {

    private int id;
    private String email;
    private String displayName;
    private String name;
    private String phone;

    public Player(String email, String displayName, String name) {
        this.id = getNextId();
        this.email = email;
        this.displayName = displayName;
        this.name = name;
    }

    public Player(String email, String displayName, String name, String phone) {
        this(email, displayName, name);
        this.phone = phone;
    }

    public int getNextId() {
        ArrayList<Integer> idList = getPlayerIds();
        idList.add(idList.size());
        serialisePlayerIds(idList);
        return idList.size()-1;
    }

    public static ArrayList<Integer> getPlayerIds() {
        try (ObjectInputStream idIn = new ObjectInputStream(new FileInputStream("./src/gamesleague/save/PlayerIds.txt"))) {
            return (ArrayList<Integer>) idIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Integer>();
    }

    public static void serialisePlayerIds(ArrayList<Integer> playerIds) {
        try (ObjectOutputStream idOut = new ObjectOutputStream(new FileOutputStream("./src/gamesleague/save/PlayerIds.txt"))) {
            idOut.writeObject(playerIds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
