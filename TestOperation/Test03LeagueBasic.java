import gamesleague.*;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalDate.*; 


public class Test03LeagueBasic {
    public static void main(String[] args) {
        System.out.println("\n#### Running Test03LeagueBasic...\n");
        GamesLeague gl = new GamesLeague();

        int p1 = gl.createPlayer("wardlejj@wardle.com",  "BigJW", "Josh Wardle", "123-456-7890");
        System.out.println("Created Player with id: " + p1);

        int p2 = gl.createPlayer("annakw@wardle.com", "AKAnna", "Anna Karl-Witham",  "");
        System.out.println("Created Player with id: " + p2);

        
        int league1ID = gl.createLeague(p1,"DR League", GameType.DICEROLL);
        System.out.println("\nCreated League with id: " + league1ID);

        int league2ID = gl.createLeague(p2,"WM League", GameType.WORDMASTER);
        System.out.println("Created League with id: " + league2ID);

        System.out.println("\nChecking result of getLeagueStartDate / setLeagueStartDate");
        if (gl.getLeagueStartDate(league2ID) == -1) {
            System.out.println("PASS: gl.getLeagueStartDate("+league2ID+") == -1");
        } else {
            System.out.println("FAIL: gl.getLeagueStartDate("+league2ID+") should return -1)");
        }

        int todayEpochDay = (int)LocalDate.now().toEpochDay();

        System.out.println("Setting start date of league "+league2ID+ " to today: " +todayEpochDay);
        gl.setLeagueStartDate(league2ID, (int)LocalDate.now().toEpochDay());
        if (gl.getLeagueStartDate(league2ID) == todayEpochDay) {
            System.out.println("PASS: gl.getLeagueStartDate("+league2ID+") == "+todayEpochDay);
        } else {
            System.out.println("FAIL: gl.getLeagueStartDate("+league2ID+") should return "+todayEpochDay);
        }
        System.out.println("\nSetting end date of league "+league2ID+ " to one weeks time: " + LocalDate.now().plusWeeks(1));
        gl.setLeagueEndDate(league2ID, (int)LocalDate.now().plusWeeks(1).toEpochDay());


        System.out.println("\nLeague ids now stored in GamesLeague backend:");
        int[] leagueIds = gl.getLeagueIds();
        System.out.println("Leagues:" + Arrays.toString(leagueIds));

        System.out.println("\nLeague Details:\n");
        for (int id : leagueIds) {
            String lName = gl.getLeagueName(id);
            String startDateString = "Not defined";
            String endDateString = "Not yet ended";
            String statusString = gl.getLeagueStatus(id).toString();
            if (gl.getLeagueStartDate(id) != -1) {
                startDateString = LocalDate.ofEpochDay(gl.getLeagueStartDate(id)).toString();
            }
            if (gl.getLeagueCloseDate(id) != -1) {
                endDateString = LocalDate.ofEpochDay(gl.getLeagueCloseDate(id)).toString();
            }
            String infoString = String.format("League[id=%d, name=%s, status=%s, start=%s, end=%s]", id, lName, statusString, startDateString, endDateString);
            System.out.println(infoString);
            int[] ownerIds = gl.getLeagueOwners(id);
            for (int ownerId : ownerIds) {
                String pEmail = gl.getPlayerEmail(ownerId);
                String pDisplayName = gl.getPlayerDisplayName(ownerId);       
                infoString = String.format("Owner is Player[id=%d, displayName=%s, email=%s]", ownerId, pDisplayName, pEmail);
                System.out.println(infoString);
            }
            int[] playerIds = gl.getLeaguePlayers(id);
            for (int playerId : playerIds) {
                String pEmail = gl.getPlayerEmail(playerId);
                String pDisplayName = gl.getPlayerDisplayName(playerId);       
                infoString = String.format("Player is: Player[id=%d, displayName=%s, email=%s]\n", playerId, pDisplayName, pEmail);
                System.out.println(infoString);
            }
        }

        System.out.println("Removing league with id: " + league1ID);
        gl.removeLeague(league1ID);
        leagueIds = gl.getLeagueIds();

        System.out.println("Renaming League2: " + league1ID);
        gl.updateLeagueName(league2ID, "NEW WML"); 


        System.out.println("\nLeague ids now stored in GamesLeague backend:");
        System.out.println("Leagues:" + Arrays.toString(leagueIds));

        System.out.println("\nLeague Details:\n");

        for (int id : leagueIds) {
            String lName = gl.getLeagueName(id);
            String startDateString = "Not defined";
            String endDateString = "Not yet ended";
            String statusString = gl.getLeagueStatus(id).toString();
            if (gl.getLeagueStartDate(id) != -1) {
                startDateString = LocalDate.ofEpochDay(gl.getLeagueStartDate(id)).toString();
            }
            if (gl.getLeagueCloseDate(id) != -1) {
                endDateString = LocalDate.ofEpochDay(gl.getLeagueCloseDate(id)).toString();
            }
            String infoString = String.format("League[id=%d, name=%s, status=%s, start=%s, end=%s]", id, lName, statusString, startDateString, endDateString);
            System.out.println(infoString);
            int[] ownerIds = gl.getLeagueOwners(id);
            for (int ownerId : ownerIds) {
                String pEmail = gl.getPlayerEmail(ownerId);
                String pDisplayName = gl.getPlayerDisplayName(ownerId);       
                infoString = String.format("Owner is: Player[id=%d, displayName=%s, email=%s]", ownerId, pDisplayName, pEmail);
                System.out.println(infoString);
            }
            int[] playerIds = gl.getLeaguePlayers(id);
            for (int playerId : playerIds) {
                String pEmail = gl.getPlayerEmail(playerId);
                String pDisplayName = gl.getPlayerDisplayName(playerId);       
                infoString = String.format("Player is: Player[id=%d, displayName=%s, email=%s]\n", playerId, pDisplayName, pEmail);
                System.out.println(infoString);
            }

        }

        int league3ID = gl.createLeague(p2,"2ND WM LEAGUE", GameType.WORDMASTER);
        System.out.println("\nCreated League with id: " + league3ID + " and owner Player " + p2);

        System.out.println("\nPlayer " + p2 + " details:");
        int[] player2Leagues = gl.getPlayerLeagues(p2);
        int[] player2OwnedLeagues = gl.getPlayerOwnedLeagues(p2);

        System.out.println("Player " + p2 + " is Player in leagues: " + Arrays.toString(player2Leagues));
        System.out.println("Player " + p2 + " is Owner in leagues: " + Arrays.toString(player2OwnedLeagues));

    }
}