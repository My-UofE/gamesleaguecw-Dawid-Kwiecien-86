package gamesleague;

import java.io.*;
import java.util.ArrayList;

public class Player implements Serializable {

    private int id;
    private String email;
    private String displayName;
    private String name;
    private String phone;

    public Player(String email, String displayName, String name) {
        this.email = email;
        this.displayName = displayName;
        this.name = name;
        setId(); // assigns the current player with the next available ID

    }

    public Player(String email, String displayName, String name, String phone) {
        this.email = email;
        this.displayName = displayName;
        this.name = name;
        this.phone = phone;
        setId();
    }

    public void addPlayerToList() {
        ArrayList<Player> playerList = getPlayers();
        playerList.add(this);
        serialisePlayers(playerList); // serialising new list of players containing the current player into memory
    }

    public static ArrayList<Player> getPlayers() {
        // De-serialising an ArrayList of Player objects from 'Players.ser' 
        try (ObjectInputStream playerIn = new ObjectInputStream(new FileInputStream("./src/gamesleague/save/Players.ser"))) {
            return (ArrayList<Player>) playerIn.readObject(); // reading value from bytestream
        } catch (IOException | ClassNotFoundException e) {
            // if the code reaches this point, the file is empty so we serialise an empty Player ArrayList to hold all Player objects
            serialisePlayers(new ArrayList<Player>()); 
        }
        return new ArrayList<Player>(); // if there are no players stored in 'Players.set', an empty list is returned
    }

    public static void serialisePlayers(ArrayList<Player> players) {
        // Serialising the 'players' parameter to be stored in 'Players.ser'
        try (ObjectOutputStream playerOut = new ObjectOutputStream(new FileOutputStream("./src/gamesleague/save/Players.ser"))) {
            playerOut.writeObject(players); // writing value of 'players' to bytestream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getters and setters
    public int getId() {
        return this.id;
    }

    // IDs are allocated automatically; should not be used for reassignment
    private void setId() {
        ArrayList<Player> players = getPlayers();

        if (players.isEmpty()) {
            this.id = 0; // start from 0 if no players exist
        } else {
            int maxId = 0;
            for (Player p : players) {
                if (p.getId() > maxId) {
                    maxId = p.getId();
                }
            }
            this.id = maxId + 1;
        }
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
        serialisePlayers(getPlayers()); // Writes changes to object into memory
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        serialisePlayers(getPlayers());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        serialisePlayers(getPlayers());
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        serialisePlayers(getPlayers());
    }

}
