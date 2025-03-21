package TestSystem;

import gamesleague.*;
import java.util.List;
import java.time.LocalDate;
import java.util.Arrays;

public class TestLeagueApp {

    private static GamesLeague leagueSystem = new GamesLeague();

    public static void main(String[] args) {
        System.out.println("========== Starting League Tests ==========\n");

        testCreateLeagues();
        testInvalidLeagues();
        testRetrieveLeagues();
        testLeagueIDs();
        testRetrieveLeagueName();
        testUpdateLeagueName();
        testRetrieveLeagues();
        testLeagueInvites();
        testLeagueOwners();
        testLeagueStatusAndDates();

        System.out.println("\n========== League Tests Complete ==========");
    }

    /**
     * Test valid league creation
     */
    private static void testCreateLeagues() {
        System.out.println("\n--- Running Create League Tests ---");

        try {
            int league1 = leagueSystem.createLeague(0, "Premier League", GameType.DICEROLL);
            System.out.println("Created League with ID: " + league1);

            int league2 = leagueSystem.createLeague(1, "Champions League", GameType.WORDMASTER);
            System.out.println("Created League with ID: " + league2);

            int league3 = leagueSystem.createLeague(2, "Europa League", GameType.DICEROLL);
            System.out.println("Created League with ID: " + league3);

            int league4 = leagueSystem.createLeague(3, "National League", GameType.WORDMASTER);
            System.out.println("Created League with ID: " + league4);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Test invalid league creation cases
     */
    private static void testInvalidLeagues() {
        System.out.println("\n--- Running Invalid League Tests ---");

        try {
            leagueSystem.createLeague(0, "Premier League", GameType.DICEROLL);
            System.out.println("Error: Duplicate league name test failed.");
        } catch (Exception e) {
            System.out.println("Correctly caught duplicate league name error: " + e.getMessage());
        }

        try {
            leagueSystem.createLeague(99, "Invalid League", GameType.DICEROLL);
            System.out.println("Error: Invalid owner ID test failed.");
        } catch (Exception e) {
            System.out.println("Correctly caught invalid owner ID error: " + e.getMessage());
        }

        try {
            leagueSystem.createLeague(1, "", GameType.DICEROLL);
            System.out.println("Error: Empty league name test failed.");
        } catch (Exception e) {
            System.out.println("Correctly caught empty league name error: " + e.getMessage());
        }
    }

    /**
     * Test retrieving all stored leagues
     */
    private static void testRetrieveLeagues() {
        System.out.println("\n--- Running Retrieve League Tests ---");

        try {
            List<League> leagues = League.getLeagues();
            System.out.println("Stored Leagues:");
            for (League l : leagues) {
                System.out.println("League ID: " + l.getId() + ", Name: " + l.getLeagueName() +
                        ", Owner IDs: " + l.getOwnerIds() + ", Game Type: " + l.getGameType());
            }
        } catch (Exception e) {
            System.out.println("Error while fetching leagues: " + e.getMessage());
        }
    }

    /**
     * Test retrieving league IDs
     */
    private static void testLeagueIDs() {
        System.out.println("\n--- Running Get League IDs Test ---");

        try {
            int[] leagueIDs = leagueSystem.getLeagueIds();
            System.out.println("Stored League IDs: " + Arrays.toString(leagueIDs));
        } catch (Exception e) {
            System.out.println("Error while fetching league IDs: " + e.getMessage());
        }
    }

    /**
     * Test retrieving league names
     */
    private static void testRetrieveLeagueName() {
        System.out.println("\n--- Running Retrieve League Name Tests ---");

        try {
            System.out.println("League ID 0 name: " + leagueSystem.getLeagueName(0));
        } catch (Exception e) {
            System.out.println("Error retrieving league name for ID 0: " + e.getMessage());
        }

        try {
            System.out.println("League ID 99 name: " + leagueSystem.getLeagueName(99));
        } catch (Exception e) {
            System.out.println("Correctly caught invalid league ID error: " + e.getMessage());
        }
    }

    /**
     * Test updating league names
     */
    private static void testUpdateLeagueName() {
        System.out.println("\n--- Running Update League Name Tests ---");

        try {
            leagueSystem.updateLeagueName(0, "Super League");
            System.out.println("Updated League Name for ID 0 to 'Super League'");

            String updatedName = leagueSystem.getLeagueName(0);
            System.out.println("Retrieved Updated League Name: " + updatedName);
        } catch (Exception e) {
            System.out.println("Error while updating league name: " + e.getMessage());
        }
    }

    /**
     * Test inviting players and managing invites
     */
    private static void testLeagueInvites() {
        System.out.println("\n--- Running League Invite Tests ---");

        int leagueId = 0;
        String registeredEmail = "player2@example.com";
        int registeredPlayerId = 1;
        String unregisteredEmail = "newplayer@example.com";


        // initial invites before inviting anyone
        try {
            System.out.println("Initial Email Invites: " + Arrays.toString(leagueSystem.getLeagueEmailInvites(leagueId)));
            System.out.println("Initial Player Invites: " + Arrays.toString(leagueSystem.getLeaguePlayerInvites(leagueId)));
        } catch (Exception e) {
            System.out.println("Error retrieving initial invites: " + e.getMessage());
        }

        // invite a registered player
        try {
            leagueSystem.invitePlayerToLeague(leagueId, registeredEmail);
        } catch (Exception e) {
            System.out.println("Error inviting registered player: " + e.getMessage());
        }

        // invite an unregistered player
        try {
            leagueSystem.invitePlayerToLeague(leagueId, unregisteredEmail);
        } catch (Exception e) {
            System.out.println("Error inviting unregistered player: " + e.getMessage());
        }

        // invites after inviting players
        try {
            System.out.println("Updated Email Invites: " + Arrays.toString(leagueSystem.getLeagueEmailInvites(leagueId)));
            System.out.println("Updated Player Invites: " + Arrays.toString(leagueSystem.getLeaguePlayerInvites(leagueId)));
        } catch (Exception e) {
            System.out.println("Error retrieving updated invites: " + e.getMessage());
        }

        // accept invite for registered player and check results
        try {
            leagueSystem.acceptInviteToLeague(leagueId, registeredPlayerId);
        } catch (Exception e) {
            System.out.println("Error accepting invite: " + e.getMessage());
        }

        // retrieve and print final player list to confirm acceptance
        try {
            System.out.println("Final League Players: " + Arrays.toString(leagueSystem.getLeaguePlayers(leagueId)));
        } catch (Exception e) {
            System.out.println("Error retrieving league players: " + e.getMessage());
        }
    }

    /**
     * Test league owner management (add, remove, retrieve)
     */
    private static void testLeagueOwners() {
        System.out.println("\n--- Running League Owner Management Tests ---");

        int leagueId = 0;
        int newOwnerId = 1;
        int invalidOwnerId = 99;

        try {
            // Before adding new owner
            System.out.println("Initial Owners: " + Arrays.toString(leagueSystem.getLeagueOwners(leagueId)));

            // Attempt to add new owner
            leagueSystem.addOwner(leagueId, newOwnerId);
            System.out.println("Added new owner (Player ID " + newOwnerId + ")");
            System.out.println("Updated Owners: " + Arrays.toString(leagueSystem.getLeagueOwners(leagueId)));

            // Attempt to remove the new owner
            leagueSystem.removeOwner(leagueId, newOwnerId);
            System.out.println("Removed owner (Player ID " + newOwnerId + ")");
            System.out.println("Final Owners: " + Arrays.toString(leagueSystem.getLeagueOwners(leagueId)));

        } catch (Exception e) {
            System.out.println("Owner management test failed: " + e.getMessage());
        }

        // Try removing last remaining owner (should throw exception)
        try {
            leagueSystem.removeOwner(leagueId, 0); // Assuming 0 is the original owner
            System.out.println("Error: Should not allow removal of last owner.");
        } catch (Exception e) {
            System.out.println("Correctly caught error removing last owner: " + e.getMessage());
        }

        // Try adding an invalid player
        try {
            leagueSystem.addOwner(leagueId, invalidOwnerId);
            System.out.println("Error: Invalid player should not be added as owner.");
        } catch (Exception e) {
            System.out.println("Correctly caught error for invalid player: " + e.getMessage());
        }
    }

    /**
     * Test league status and scheduling
     */
    private static void testLeagueStatusAndDates() {
        System.out.println("\n--- Running League Status & Scheduling Tests ---");

        int leagueId = 0;

        try {
            System.out.println("Initial league status: " + leagueSystem.getLeagueStatus(leagueId));

            int futureEpoch = (int) LocalDate.now().plusDays(5).toEpochDay();
            leagueSystem.setLeagueStartDate(leagueId, futureEpoch);
            System.out.println("Set future start date. Status now: " + leagueSystem.getLeagueStatus(leagueId));

            int pastEpoch = (int) LocalDate.now().minusDays(5).toEpochDay();
            leagueSystem.setLeagueStartDate(leagueId, pastEpoch);
            System.out.println("Set past start date. Status now: " + leagueSystem.getLeagueStatus(leagueId));

            int futureEndEpoch = (int) LocalDate.now().plusDays(10).toEpochDay();
            leagueSystem.setLeagueEndDate(leagueId, futureEndEpoch);
            System.out.println("Set future end date. Status now: " + leagueSystem.getLeagueStatus(leagueId));
        } catch (Exception e) {
            System.out.println("Error in League Scheduling tests: " + e.getMessage());
        }
    }
}