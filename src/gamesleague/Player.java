package gamesleague;

import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class Player implements Serializable {

    private int id;
    private String email;
    private String displayName;
    private String name;
    private String phone;
    private LocalDate joinDate;

    public Player(String email, String displayName, String name) {
        this.email = email;
        this.displayName = displayName;
        this.name = name;
        this.joinDate = LocalDate.now();
        setId();
    }

    public Player(String email, String displayName, String name, String phone) {
        this.email = email;
        this.displayName = displayName;
        this.name = name;
        this.phone = phone;
        this.joinDate = LocalDate.now();
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
        return new ArrayList<Player>(); // if there are no players stored in 'Players.ser', an empty list is returned
    }

    public static void serialisePlayers(ArrayList<Player> players) {
        // Serialising the 'players' parameter to be stored in 'Players.ser'
        try (ObjectOutputStream playerOut = new ObjectOutputStream(new FileOutputStream("./src/gamesleague/save/Players.ser"))) {
            playerOut.writeObject(players); // writing value of 'players' to bytestream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNextId() {
        // De-serialising nextId from 'nextId.ser'
        try (ObjectInputStream nextIdIn = new ObjectInputStream(new FileInputStream("./src/gamesleague/save/nextId.ser"))) {
            return (int) nextIdIn.readObject(); // reading value from bytestream
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("nextId.ser not found or unreadable. Starting from ID 0.");
            return 0;
        }

    }

    public static void serialiseNextId(int nextId) {
        // Serialising the 'nextId' parameter to be stored in 'nextId.ser'
        try (ObjectOutputStream nextIdOut = new ObjectOutputStream(new FileOutputStream("./src/gamesleague/save/nextId.ser"))) {
            nextIdOut.writeObject(nextId); // writing value of 'nextId' to bytestream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getters and setters
    public int getId() {
        return this.id;
    }

    // ensure unique, non-reassignable IDs using nextId.ser
    private void setId() {
        int newId = getNextId();
        this.id = newId;
        serialiseNextId(newId + 1);
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

    public LocalDate getJoinDate() {
        return this.joinDate;
    }

}
