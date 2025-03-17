package TestSystem;

import gamesleague.*;

import java.util.List;
import java.time.LocalDate;

public class TestPlayerApp {
    public static void main(String[] args) {
        GamesLeague league = new GamesLeague();

        System.out.println("Starting Player Tests...");

        try {
            // create valid players
            int player1 = league.createPlayer("player1@example.com", "PlayerOne", "John Doe", "123456789");
            System.out.println("Created Player 1 with ID: " + player1);

            int player2 = league.createPlayer("player2@example.com", "PlayerTwo", "Jane Doe", "987654321");
            System.out.println("Created Player 2 with ID: " + player2);

            // attempt to create a player with a duplicate email (should fail)
            league.createPlayer("player1@example.com", "Duplicate", "Jack Doe", "555555555");
            System.out.println("Error: Duplicate email test failed");

        } catch (Exception e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }

        try {
            // attempt to create a player with an invalid email
            league.createPlayer("invalidemail.com", "InvalidEmail", "Invalid Name", "111111111");
            System.out.println("Error: Invalid email test failed");
        } catch (Exception e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }

        try {
            // attempt to create a player with an empty display name
            league.createPlayer("valid@example.com", "", "Johnny", "222222222");
            System.out.println("Error: Empty display name test failed");
        } catch (Exception e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }

        try {
            // attempt to create a player with a name that is too short
            league.createPlayer("valid2@example.com", "ValidName", "Joe", "333333333");
            System.out.println("Error: Short name test failed");
        } catch (Exception e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }

        try {
            // add third and fourth player
            int player3 = league.createPlayer("player3@example.com", "PlayerThree", "Jack Doe", "111111111");
            System.out.println("Created Player 3 with ID: " + player3);

            int player4 = league.createPlayer("player4@example.com", "PlayerFour", "Jessie Doe", "");
            System.out.println("Created Player 4 with ID: " + player4);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        // display stored players
        try {
            List<Player> players = Player.getPlayers();
            System.out.println("\nStored Players:");
            for (Player p : players) {
                System.out.println("Player ID: " + p.getId() + ", Email: " + p.getEmail() + ", Name: " + p.getName()
                        + ", Display Name: " + p.getDisplayName() + ", Join Date: " + p.getJoinDate());
            }

        } catch (Exception e) {
            System.out.println("Error while fetching players: " + e.getMessage());
        }

        // test getPlayerIDs
        try {
            int[] playerIDs = league.getPlayerIds();
            System.out.println("\nStored Player IDs:");
            for (int Id : playerIDs) {
                System.out.println("Found player ID: " + Id);
            }

        } catch (Exception e) {
            System.out.println("Error while fetching player IDs: " + e.getMessage());
        }


        // test getPlayerId
        try {
            int id = league.getPlayerId("player1@example.com");
            System.out.println("\nRetrieved Player ID for player1@example.com: " + id);
        } catch (Exception e) {
            System.out.println("Error while retrieving player ID: " + e.getMessage());
        }

        // test getPlayerDisplayName
        try {
            String displayName = league.getPlayerDisplayName(0);
            System.out.println("Retrieved display name for Player ID 0: " + displayName);
        } catch (Exception e) {
            System.out.println("Error while retrieving display name: " + e.getMessage());
        }

        // test getPlayerEmail
        try {
            String email = league.getPlayerEmail(1);
            System.out.println("Retrieved email for Player ID 1: " + email);
        } catch (Exception e) {
            System.out.println("Error while retrieving player email: " + e.getMessage());
        }

        // test getJoinDate
        try {
            LocalDate joinDate = league.getPlayerJoinDate(1);
            System.out.println("Retrieved join date for Player ID 1: " + joinDate);
        } catch (Exception e) {
            System.out.println("Error while retrieving player join date: " + e.getMessage());
        }

        // test updatePlayerDisplayName
        try {
            int playerId = league.getPlayerId("player1@example.com"); // Get ID of an existing player
            league.updatePlayerDisplayName(playerId, "PlayerOne");
            System.out.println("\nUpdated display name for Player ID " + playerId + " to 'PlayerOne'");

            // Verify the update
            String updatedName = league.getPlayerDisplayName(playerId);
            System.out.println("Retrieved Updated Display Name: " + updatedName);

            if (updatedName.equals("PlayerOne")) {
                System.out.println("Test Passed: Display name successfully updated.");
            } else {
                System.out.println("Test Failed: Display name did not update correctly.");
            }

        } catch (Exception e) {
            System.out.println("Error during update test: " + e.getMessage());
        }

        // display stored players again
        try {
            List<Player> players = Player.getPlayers();
            System.out.println("\nStored Players after UpdateDisplayName:");
            for (Player p : players) {
                System.out.println("Player ID: " + p.getId() + ", Email: " + p.getEmail() + ", Name: " + p.getName()
                        + ", Display Name: " + p.getDisplayName() + ", Join Date: " + p.getJoinDate());
            }

        } catch (Exception e) {
            System.out.println("Error while fetching players: " + e.getMessage());
        }

        System.out.println("Player Tests Complete.");
    }
}