package gamesleague;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;


public class League implements Serializable {

    private int leagueId;
    private int ownerId;
    private String name;
    private GameType gameType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate closeDate;
    private Status leagueStatus;
    private ArrayList<Integer> playerIds;
    private ArrayList<String> emailInvites;
    private ArrayList<Integer> playerInvites;
    private HashMap<Integer, int[]> dayScores;
    private HashMap<Integer, HashMap<Integer, String>> gameReports;
    private HashMap<Integer, Status> playerStatus;


    public League(int owner, String name, GameType gameType) {
        this.ownerId = owner;
        this.name = name;
        this.gameType = gameType;
        this.startDate = LocalDate.now();
        setId();

        this.playerIds = new ArrayList<>();
        this.emailInvites = new ArrayList<>();
        this.playerInvites = new ArrayList<>();
        this.dayScores = new HashMap<>();
        this.gameReports = new HashMap<>();
        this.playerStatus = new HashMap<>();
    }

    public void addLeagueToList() {
        ArrayList<League> leagueList = getLeagues();
        leagueList.add(this);
        serialiseLeagues(leagueList); // serialising new list of leagues containing the current league into memory
    }

    public static ArrayList<League> getLeagues() {
        // De-serialising an ArrayList of League objects from 'Leagues.ser'
        try (ObjectInputStream leagueIn = new ObjectInputStream(new FileInputStream("./src/gamesleague/save/Leagues.ser"))) {
            return (ArrayList<League>) leagueIn.readObject(); // reading value from bytestream
        } catch (IOException | ClassNotFoundException e) {
            // if the code reaches this point, the file is empty so we serialise an empty League ArrayList to hold all League objects
            serialiseLeagues(new ArrayList<League>());
        }
        return new ArrayList<League>(); // if there are no leagues stored in 'Leagues.ser', an empty list is returned
    }

    public static void serialiseLeagues(ArrayList<League> leagues) {
        // Serialising the 'leagues' parameter to be stored in 'Leagues.ser'
        try (ObjectOutputStream leagueOut = new ObjectOutputStream(new FileOutputStream("./src/gamesleague/save/Leagues.ser"))) {
            leagueOut.writeObject(leagues); // writing value of 'leagues' to bytestream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNextLeagueId() {
        // De-serialising nextLeagueId from 'nextLeagueId.ser'
        try (ObjectInputStream leagueIdIn = new ObjectInputStream(new FileInputStream("./src/gamesleague/save/nextLeagueId.ser"))) {
            return (int) leagueIdIn.readObject(); // reading value from bytestream
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("nextLeagueId.ser not found or unreadable. Starting from ID 0.");
            return 0;
        }
    }

    public static void serialiseNextLeagueId(int nextLeagueId) {
        // Serialising the 'nextLeagueId' parameter to be stored in 'nextLeagueId.ser'
        try (ObjectOutputStream leagueIdOut = new ObjectOutputStream(new FileOutputStream("./src/gamesleague/save/nextLeagueId.ser"))) {
            leagueIdOut.writeObject(nextLeagueId); // writing value of 'nextLeagueId' to bytestream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getters and setters
    public int getId() {
        return this.leagueId;
    }

    // ensure unique, non-reassignable league IDs using nextLeagueId.ser
    private void setId() {
        int newLeagueId = getNextLeagueId();
        this.leagueId = newLeagueId;
        serialiseNextLeagueId(newLeagueId + 1);
    }

    public int getOwnerId(){
        return this.ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
        serialiseLeagues(getLeagues());
    }

    public String getLeagueName(){
        return this.name;
    }

    public void setLeagueName(String name) {
        this.name = name;
        serialiseLeagues(getLeagues());
    }

    public GameType getGameType() {
        return this.gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
        serialiseLeagues(getLeagues());
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        serialiseLeagues(getLeagues());
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        serialiseLeagues(getLeagues());
    }

    public LocalDate getCloseDate() {
        return this.closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        serialiseLeagues(getLeagues());
    }

    public Status getLeagueStatus() {
        return this.leagueStatus;
    }

    public void setLeagueStatus(Status leagueStatus) {
        this.leagueStatus = leagueStatus;
        serialiseLeagues(getLeagues());
    }

    public ArrayList<Integer> getLeaguePlayerIds() {
        return new ArrayList<>(this.playerIds); // Returning a copy to prevent direct modification
    }

    public ArrayList<String> getEmailInvites() {
        return new ArrayList<>(this.emailInvites);
    }

    public ArrayList<Integer> getPlayerInvites() {
        return new ArrayList<>(this.playerInvites);
    }

    public HashMap<Integer, int[]> getDayScores() {
        return new HashMap<>(this.dayScores);
    }

    public void setDayScores(HashMap<Integer, int[]> dayScores) {
        this.dayScores = dayScores;
    }

    public void addDayScores(int day, int[] scores) {
        this.dayScores.put(day, scores);
        serialiseLeagues(getLeagues());
    }

    public HashMap<Integer, HashMap<Integer, String>> getGameReports() {
        return new HashMap<>(this.gameReports);
    }

    public void addGameReport(int playerId, int day, String gameReport) {
        HashMap<Integer, String> playerGameReports = this.gameReports.get(playerId);
        playerGameReports.put(day, gameReport);
        serialiseLeagues(getLeagues());
    }

    public HashMap<Integer, Status> getPlayerStatus() {
        return new HashMap<>(this.playerStatus);
    }

    public void setPlayerStatus(int playerId, Status status) {
        this.playerStatus.replace(playerId, status);
        serialiseLeagues(getLeagues());
    }

}

