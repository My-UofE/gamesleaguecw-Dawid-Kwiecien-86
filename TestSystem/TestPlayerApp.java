package TestSystem;

import gamesleague.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Arrays;

public class TestPlayerApp {

    private static GamesLeague leagueSystem = new GamesLeague();

    public static void main(String[] args) {
        System.out.println("========== Starting Player Tests ==========\n");

        testCreatePlayers();
        testInvalidPlayers();
        testRetrievePlayers();
        testRetrievePlayerDetails();
        testUpdatePlayerDisplayName();
        testRetrievePlayers();

        System.out.println("\n========== Player Tests Complete ==========");
    }

    /**
     * Test valid player creation
     */
    private static void testCreatePlayers() {
        System.out.println("\n--- Running Create Player Tests ---");

        try {
            int player1 = leagueSystem.createPlayer("player1@example.com", "PlayerOne", "John Doe", "123456789");
            System.out.println("Created Player with ID: " + player1);

            int player2 = leagueSystem.createPlayer("player2@example.com", "PlayerTwo", "Jane Doe", "987654321");
            System.out.println("Created Player with ID: " + player2);

            int player3 = leagueSystem.createPlayer("player3@example.com", "PlayerThree", "Jack Doe", "111111111");
            System.out.println("Created Player with ID: " + player3);

            int player4 = leagueSystem.createPlayer("player4@example.com", "PlayerFour", "Jessie Doe", "");
            System.out.println("Created Player with ID: " + player4);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Test invalid player creation cases
     */
    private static void testInvalidPlayers() {
        System.out.println("\n--- Running Invalid Player Tests ---");

        try {
            leagueSystem.createPlayer("player1@example.com", "Duplicate", "Jack Doe", "555555555");
            System.out.println("Error: Duplicate email test failed.");
        } catch (Exception e) {
            System.out.println("Correctly caught duplicate email error: " + e.getMessage());
        }

        try {
            leagueSystem.createPlayer("invalidemail.com", "InvalidEmail", "Invalid Name", "111111111");
            System.out.println("Error: Invalid email format test failed.");
        } catch (Exception e) {
            System.out.println("Correctly caught invalid email format error: " + e.getMessage());
        }

        try {
            leagueSystem.createPlayer("valid@example.com", "", "Johnny", "222222222");
            System.out.println("Error: Empty display name test failed.");
        } catch (Exception e) {
            System.out.println("Correctly caught empty display name error: " + e.getMessage());
        }

        try {
            leagueSystem.createPlayer("valid2@example.com", "ValidName", "Jo", "333333333");
            System.out.println("Error: Name too short test failed.");
        } catch (Exception e) {
            System.out.println("Correctly caught short name error: " + e.getMessage());
        }
    }

    /**
     * Test retrieving all stored players
     */
    private static void testRetrievePlayers() {
        System.out.println("\n--- Running Retrieve Player Tests ---");

        try {
            List<Player> players = Player.getPlayers();
            System.out.println("Stored Players:");
            for (Player p : players) {
                System.out.println("Player ID: " + p.getId() + ", Email: " + p.getEmail() +
                        ", Name: " + p.getName() + ", Display Name: " + p.getDisplayName() +
                        ", Join Date: " + p.getJoinDate());
            }
        } catch (Exception e) {
            System.out.println("Error while fetching players: " + e.getMessage());
        }
    }

    /**
     * Test retrieving specific player details
     */
    private static void testRetrievePlayerDetails() {
        System.out.println("\n--- Running Retrieve Player Details Tests ---");

        try {
            int playerId = leagueSystem.getPlayerId("player1@example.com");
            System.out.println("Player ID for 'player1@example.com': " + playerId);
        } catch (Exception e) {
            System.out.println("Error retrieving Player ID: " + e.getMessage());
        }

        try {
            String displayName = leagueSystem.getPlayerDisplayName(0);
            System.out.println("Player display name for ID 0: " + displayName);
        } catch (Exception e) {
            System.out.println("Error retrieving Player display name: " + e.getMessage());
        }

        try {
            String email = leagueSystem.getPlayerEmail(1);
            System.out.println("Player email for ID 1: " + email);
        } catch (Exception e) {
            System.out.println("Error retrieving Player email: " + e.getMessage());
        }

        try {
            LocalDate joinDate = leagueSystem.getPlayerJoinDate(1);
            System.out.println("Player join date for ID 1: " + joinDate);
        } catch (Exception e) {
            System.out.println("Error retrieving Player join date: " + e.getMessage());
        }
    }

    /**
     * Test updating player display name
     */
    private static void testUpdatePlayerDisplayName() {
        System.out.println("\n--- Running Update Player Display Name Test ---");

        try {
            int playerId = leagueSystem.getPlayerId("player1@example.com");
            String newName = "PlayerOne";
            leagueSystem.updatePlayerDisplayName(playerId, newName);
            System.out.println("Updated display name for Player ID " + playerId + " to " + newName);

            // Verify the update
            String updatedName = leagueSystem.getPlayerDisplayName(playerId);
            System.out.println("Retrieved Updated Display Name: " + updatedName);

            if (updatedName.equals(newName)) {
                System.out.println("Test Passed: Display name successfully updated.");
            } else {
                System.out.println("Test Failed: Display name did not update correctly.");
            }

        } catch (Exception e) {
            System.out.println("Error during update test: " + e.getMessage());
        }
    }
}