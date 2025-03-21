package gamesleague;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.ArrayList;

/**
 * GamesLeague Class Template
 * You can use this template and add the corect method code.
 * At present it is a skeleton template with placeholder methods 
 * so that it can compile and implements all methods required by GamesLeagueInterface
 *
 * @author Philip Lewis
 * @version 0.3.1
 *
 */

public class GamesLeague implements GamesLeagueInterface {


    // Players

    /**
     * Get the players currently created in the platform.
     *
     * @return An array of player IDs in the system or an empty array if none exists.
     */
    public int[] getPlayerIds(){
        ArrayList<Player> players = Player.getPlayers();
        int[] playerIDs = new int[players.size()];

        for (int i = 0; i < players.size(); i++) {
            playerIDs[i] = players.get(i).getId();
        }
        return playerIDs;
    }

    /**
     * Creates a player entry.
     *
     * @param email The email of the player (unique).
     * @param displayName The name of the player.
     * @param name The name of the player.
     * @param phone The contact phone number of the player or empty string.
     * @return The ID of the rider in the system.
     * @throws InvalidNameException If the displayName/name is null or starts/ends with whitespace,
     *                              or if the name is less than 5 char / more than 50 char.
     *                              or if displayName is less than 1 char/more than 20 char.
     * @throws InvalidEmailException If the email is null, empty, or does not contain an '@' character,
     * @throws IllegalEmailException if it duplicates an existing email of a player
     */
    public int createPlayer(String email, String displayName, String name, String phone) throws InvalidEmailException, IllegalEmailException, InvalidNameException {

        // trim to get rid of leading and trailing white space
        email = email.trim();
        displayName = displayName.trim();
        name = name.trim();

        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new InvalidEmailException("The email entered is invalid. Please try again");
        }

        ArrayList<Player> players = Player.getPlayers();
        for (Player p : players) {
            if(p.getEmail().equalsIgnoreCase(email)){
                throw new IllegalEmailException("The email entered is already in use. Please try again.");
            }
        }

        if (displayName.isEmpty() || displayName.length() < 1 || displayName.length() > 20) {
            throw new InvalidNameException("The display name entered is invalid, please try again");
        }

        if (name.isEmpty() || name.length() < 5 || name.length() > 50) {
            throw new InvalidNameException("The name entered is invalid. Please try again");
        }

        // once all validation checks are passed, create new player object
        Player player = new Player(email, displayName, name, phone);
        player.addPlayerToList();
        return player.getId();
    }


    public static void main(String[] args) {
        GamesLeague league = new GamesLeague();
        for (int id : league.getPlayerIds()) {
            System.out.println(id);
        }
    }

    /**
     * Permenantly deactivates player account.
     * <p>
     * Note to preserve the integrity of the league tables the removal process should follow:
     * i) all personal player anonymised as below:
     *     - name & displayName to "anonymousplayerX" where X is playerId
     *     - email is set to "" and phone is set to ""
     * ii) all player gameplay reports are set to empty strings
     * iii) player is set as inactive in all their league memberships
     *
     * @param playerId The ID of the player to be deactivated.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     * @throws IllegalOperationException If the player is the sole owner of a league.
     */
    public void deactivatePlayer(int playerId)
        throws IDInvalidException, IllegalOperationException {

        return; // placeholder so class compiles
    };


    /**
     * Check if a player has been deactivated.
     *
     * @param playerId The ID of the player.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     *
     * @return true if player has been deactivated or false otherwise
     */
    public boolean isDeactivatedPlayer(int playerId)
        throws IDInvalidException{

            return false; // placeholder so class compiles
        };


    /**
     * Updates the player's display name.
     *
     * @param playerId The ID of the player to be updated.
     * @param displayName The new display name of the player.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     * @throws InvalidNameException If the name is null, starts/ends with whitespace,
     *                              is less than 1 characters or more than 20 characters.
     */
    public void updatePlayerDisplayName(int playerId, String displayName)
        throws  IDInvalidException, InvalidNameException {

        displayName = displayName.trim();
        if (displayName.isEmpty() || displayName == null|| displayName.length() < 1 || displayName.length() > 20) {
            throw new InvalidNameException("The display name entered is invalid, please try again");
        }

        ArrayList<Player> players = Player.getPlayers();
        for (Player p : players) {
            if(p.getId() == playerId) {
                p.setDisplayName(displayName);
                Player.serialisePlayers(players);
                return;
            }
        }

        throw new IDInvalidException("ID does not match to any player in the system");
    }

    /**
     * Get the player id from the email.
     *
     * @param email The email of the player.
     * @return The ID of the player in the system or -1 if the player does not exist.
     */
    public int getPlayerId(String email){
        ArrayList<Player> players = Player.getPlayers();
        for (Player p : players){
            if(p.getEmail().equals(email)) {
                return p.getId();
            }
        }

        return -1;
    }


    /**
     * Get the player's display name.
     *
     * @param playerId The ID of the player being queried.
     * @return The display name of the player.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     *
     */
    public String getPlayerDisplayName(int playerId) throws IDInvalidException{
        ArrayList<Player> players = Player.getPlayers();
        for (Player p : players) {
            if(p.getId() == playerId) {
                return p.getDisplayName();

            }
        }
        throw new IDInvalidException("ID does not match any player in the system");
    }


    /**
     * Get the player's email.
     *
     * @param playerId The ID of the player being queried.
     * @return The email of the player.
     * @throws IllegalEmailException If the email does not match to any player in the system.
     *
     */
    public String getPlayerEmail(int playerId) throws IllegalEmailException{

        ArrayList<Player> players = Player.getPlayers();
        for (Player p : players) {
            if(p.getId() == playerId) {
                return p.getEmail();
            }
        }
        throw new IllegalEmailException("ID does not match to any email in the system");
    }


    /**
     * Get the in progress leagues a player is currently a member.
     *
     * @param playerId The ID of the player being queried.
     * @return An array of league IDs the player is in or an empty array if none exists.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     */
    public int[] getPlayerLeagues(int playerId) throws IDInvalidException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the leagues a player owns.
     *
     * @param playerId The ID of the player being queried.
     * @return An array of league IDs the player owns or an empty array if none exists.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     */
    public int[] getPlayerOwnedLeagues(int playerId) throws IDInvalidException{

        return new int[0]; // placeholder so class compiles
    };

    /**
     * Get the user's invites
     *
     * @param playerId The ID of the player being queried.
     * @return An array of league IDs the player has invites to or an empty array if none exists.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     */
    public int[] getPlayerInvites(int playerId) throws IDInvalidException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the user's rounds played game stat across all leagues
     * (includes closed/removed leagues)
     *
     * @param playerId The ID of the player being queried.
     * @return number of rounds played by the player.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     */
    public int getPlayerRoundsPlayed(int playerId) throws IDInvalidException{

        return 0; // placeholder so class compiles
    };


    /**
     * Get the user's round participation percentage stat
     *  i.e. if they play all games in all their leagues every day this will be 100
     *  (includes closed/removed leagues)
     *
     * @param playerId The ID of the player being queried.
     * @return percentage of rounds (0-100) played by the player across all leagues.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     */
    public double getPlayerRoundsPercentage(int playerId) throws IDInvalidException{

        return 0; // placeholder so class compiles
    };

    /**
     * Get the date that the user joined the site
     * 
     * @param playerId The ID of the player being queried.
     * @return LocalDate that stores the date the player created their account.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     */
    public LocalDate getPlayerJoinDate(int playerId) throws IDInvalidException{

        ArrayList<Player> players = Player.getPlayers();
        for (Player p : players) {
            if(p.getId() == playerId) {
                return p.getJoinDate();
            }
        }
        throw new IDInvalidException("ID does not match to any player in the system");
    };

    // Leagues

    /**
     * Get the leagues currently created in the platform.
     *
     * @return An array of leagues IDs in the system or an empty array if none exists.
     */
    public int[] getLeagueIds(){
        ArrayList<League> leagues = League.getLeagues();
        int[] leagueIds = new int[leagues.size()];

        for (int i = 0; i < leagues.size(); i++) {
            leagueIds[i] = leagues.get(i).getId();
        }
        return leagueIds;
    };

    /**
     * Creates a league.
     * <p>
     * 
     *
     * @param owner PlayerId of the league owner.
     * @param name The name of the league.
     * @param gameType The game for which the league is set up.
     * @return The ID of the league in the system.
     * @throws IDInvalidException If the ID does not match to any player in the system.
     * @throws InvalidNameException If the name is null, starts/ends with whitespace, 
     *                              is less than 1 characters or more than 20 characters.
     * @throws IllegalNameException if it duplicates an existing league name
     */
    public int createLeague(int owner, String name, GameType gameType ) 
        throws  IDInvalidException, 
                InvalidNameException, 
                IllegalNameException{

        // trim to get rid of leading and trailing white spaces
        name = name.trim();

        // check if ID is valid
        int[] playerIds = getPlayerIds();
        boolean validOwner = false;
        for (int id : playerIds) {
            if (id == owner) {
                validOwner = true;
                break;
            }
        }
        if (!validOwner) {
            throw new IDInvalidException("ID does not match any player in the system.");
        }

        // check if name is valid
        if (name.isEmpty() || name == null || name.length() < 1 || name.length() > 20) {
            throw new InvalidNameException("The name entered is invalid, please try again");
        }

        // check if name is already in use
        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if(l.getLeagueName().equalsIgnoreCase(name)){
                throw new IllegalNameException("The name entered is already in use. Please try again.");
            }
        }

        // once all validation checks are passed, create new league object
        League league = new League(owner, name, gameType);
        league.addPlayerToLeague(owner);
        league.addLeagueToList();
        return league.getId();
    }

    /**
     * Removes a league and all associated game data from the system.
     * <p>
     * 
     *
     * @param leagueId The ID of the league to be removed.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public void removeLeague(int leagueId) throws IDInvalidException{

        return; // placeholder so class compiles
    };

    /**
     * Get name of league
     * 
     * @param leagueId The ID of the league being queried.
     * @return The name of the league.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public String getLeagueName(int leagueId) throws IDInvalidException{
        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if(l.getId() == leagueId) {
                return l.getLeagueName();

            }
        }
        throw new IDInvalidException("ID does not match any player in the system");
    };

    /**
     * Update the name of a league
     * 
     * @param leagueId The ID of the league to be updated.
     * @param newName The new name of the league.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidNameException If the name is null, starts/ends with whitespace, 
     *                              is less than 1 characters or more than 20 characters.
     * @throws IllegalNameException if it duplicates an existing league name.
     * 
     */
    public void updateLeagueName(int leagueId, String newName) 
        throws IDInvalidException, 
                InvalidNameException, 
                IllegalNameException{

        newName = newName.trim();
        if (newName.isEmpty() || newName.length() < 1 || newName.length() > 20) {
            throw new InvalidNameException("The name entered is invalid. Please try again.");
        }

        ArrayList<League> leagues = League.getLeagues();
        boolean leagueFound = false;

        for (League l : leagues) {
            if (l.getLeagueName().equalsIgnoreCase(newName)) {
                throw new IllegalNameException("The name entered is already in use. Please try again.");
            }
            if (l.getId() == leagueId) {
                l.setLeagueName(newName);
                leagueFound = true;
            }
        }

        if (!leagueFound) {
            throw new IDInvalidException("ID does not match any league in the system.");
        }

        League.serialiseLeagues(leagues);
    };


    /**
     * Invites a potential player (may not yet be site member) to a league.
     * <p>
     * 
     * @param leagueId The ID of the league to invite the player to.
     * @param email The email of the player to be invited.
     * @throws IDInvalidException If the ID does not match to any league or player in the system.
     * @throws InvalidEmailException If the email is null, empty, or does not contain an '@' character.
     */
    public void invitePlayerToLeague(int leagueId, String email)
            throws IDInvalidException, InvalidEmailException {

        // validate email
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new InvalidEmailException("The email entered is invalid. Please try again.");
        }

        // retrieve leagues
        ArrayList<League> leagues = League.getLeagues();
        League targetLeague = null;

        // find the target league
        for (int i = 0; i < leagues.size(); i++) {
            if (leagues.get(i).getId() == leagueId) {
                targetLeague = leagues.get(i);
                break;
            }
        }

        if (targetLeague == null) {
            throw new IDInvalidException("ID does not match any league in the system.");
        }

        // retrieve players
        ArrayList<Player> players = Player.getPlayers();
        boolean isRegisteredPlayer = false;
        int playerId = -1;

        // check if the email belongs to an existing player
        for (Player p : players) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                isRegisteredPlayer = true;
                playerId = p.getId();
                break;
            }
        }

        if (isRegisteredPlayer) {
            // if the player is already in the league, prevent duplicate invites
            if (targetLeague.getLeaguePlayerIds().contains(playerId)) {
                System.out.println("Player with email " + email + " is already in the league.");
                return;
            }

            // if player is registered, add to player invites
            if (!targetLeague.getPlayerInvites().contains(playerId)) {
                targetLeague.addPlayerInvite(playerId);
            }
        } else {
            // if player is not registered add to email invites
            if (!targetLeague.getEmailInvites().contains(email)) {
                targetLeague.addEmailInvite(email);
            }
        }

        League.serialiseLeagues(leagues);
    }

    /**
     * Accepts an invite to a league.
     *
     * @param leagueId The ID of the league to accept the invite to.
     * @param playerId The ID of the player.
     * 
     * @throws IDInvalidException If the ID does not match to any league or player in the system.
     * @throws IllegalOperationException If the player email does not have an active invitation.
     */
    public void acceptInviteToLeague(int leagueId, int playerId) 
        throws IDInvalidException, IllegalOperationException {

        ArrayList<League> leagues = League.getLeagues();
        ArrayList<Player> players = Player.getPlayers();
        League targetLeague = null;
        Player targetPlayer = null;

        // find the league
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                targetLeague = l;
                break;
            }
        }
        if (targetLeague == null) {
            throw new IDInvalidException("ID does not match any league in the system.");
        }

        // find the player
        for (Player p : players) {
            if (p.getId() == playerId) {
                targetPlayer = p;
                break;
            }
        }
        if (targetPlayer == null) {
            throw new IDInvalidException("ID does not match any player in the system.");
        }

        // check if the player was invited
        boolean invitedById = targetLeague.getPlayerInvites().contains(playerId);
        boolean invitedByEmail = targetLeague.getEmailInvites().contains(targetPlayer.getEmail());

        if (!invitedById && !invitedByEmail) {
            throw new IllegalOperationException("This player does not have an active invitation.");
        }

        // remove invitation
        targetLeague.getPlayerInvites().remove(Integer.valueOf(playerId));
        targetLeague.getEmailInvites().remove(targetPlayer.getEmail());

        // add player to the league
        targetLeague.getLeaguePlayerIds().add(playerId);
        League.serialiseLeagues(leagues);

    }



    /**
     * Removes invite from league
     * 
     * @param leagueId The ID of the league to remove the invite from.
     * @param email The email of the player to remove the invite from.
     * @throws IDInvalidException If the ID does not match to any league or player in the system.
     * @throws IllegalEmailException If the email does not have an active invitation.
     */
    public void removeInviteFromLeague(int leagueId, String email)
            throws IDInvalidException, IllegalEmailException {

        // get all leagues
        ArrayList<League> leagues = League.getLeagues();
        League targetLeague = null;

        // validate email
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new IllegalEmailException("The email entered is invalid.");
        }

        // find the target league
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                targetLeague = l;
                break;
            }
        }

        // ff league does not exist, throw exception
        if (targetLeague == null) {
            throw new IDInvalidException("ID does not match any league in the system.");
        }

        // get the league invitation lists
        ArrayList<String> emailInvites = targetLeague.getEmailInvites();
        ArrayList<Integer> playerInvites = targetLeague.getPlayerInvites();

        boolean removed = false;

        // remove email from email invites list if it exists
        if (emailInvites.contains(email)) {
            emailInvites.remove(email);
            removed = true;
        }

        // also check if the email belongs to a registered player and remove them from playerInvites
        ArrayList<Player> players = Player.getPlayers();
        for (Player p : players) {
            if (p.getEmail().equals(email)) {
                if (playerInvites.contains(p.getId())) {
                    playerInvites.remove(Integer.valueOf(p.getId()));
                    removed = true;
                }
                break;
            }
        }

        // if nothing was removed, the email was not found in either invite list
        if (!removed) {
            throw new IllegalEmailException("No active invitation found for this email.");
        }

        League.serialiseLeagues(leagues);
    }


    /**
     * Get league invitations for non-existing players (email addresses)
     * 
     * @param leagueId The ID of the league being queried.
     * @return An array of email addresses of players with pending invites or an empty array if none exists.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public String[] getLeagueEmailInvites(int leagueId) throws IDInvalidException{

        // find the target league
        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                // return email invites as an array
                return l.getEmailInvites().toArray(new String[0]);
            }
        }

        // if league is not found, throw an exception
        throw new IDInvalidException("ID does not match any league in the system.");
    };


    /**
     * Get league invitations made to existing players (player IDs)
     * 
     * @param leagueId The ID of the league being queried.
     * @return An array of player IDs who have pending invites or an empty array if none exists.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public int[] getLeaguePlayerInvites(int leagueId) throws IDInvalidException{

        // find the target league
        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                // return player invites as an array
                return l.getPlayerInvites().stream().mapToInt(Integer::intValue).toArray();
            }
        }

        // ff league is not found, throw an exception
        throw new IDInvalidException("ID does not match any league in the system.");
    };


    /**
     * Get the players in a league. 
     * The order of players should be the user that created the league,
     * (i.e. original owner), followed by other players in the order they accepted 
     * the league invitations.
     * 
     * @param leagueId The ID of the league being queried.
     * @return An array of player IDs in the league or an empty array if none exists.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public int[] getLeaguePlayers(int leagueId) throws IDInvalidException{

        // find the target league
        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                // return league players as an array
                return l.getLeaguePlayerIds().stream().mapToInt(Integer::intValue).toArray();
            }
        }

        // if league is not found, throw an exception
        throw new IDInvalidException("ID does not match any league in the system.");
    };


    /**
     * Get the owners of a league.
     * 
     * @param leagueId The ID of the league being queried.
     * @return An array of player IDs that are owners of the league or empty array if none exists.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public int[] getLeagueOwners(int leagueId) throws IDInvalidException{

        // find the target league
        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                // return league owners as an array
                return l.getOwnerIds().stream().mapToInt(Integer::intValue).toArray();
            }
        }

        // if league is not found, throw an exception
        throw new IDInvalidException("ID does not match any league in the system.");
    };

    /**
     * Get the status of a league. Your code should look at the current local date
     * as epoch day and compare it with any start and end dates that have been set for the league
     * Note that leagues are created without a specified start/end date
     *
     *  - If the league has start date is in the future (or no start date specified)
     *    the status should be PENDING
     *
     *  - If the league has a specified start date in the past and
     *    a specified end date in the future (or no specified end date) then
     *    the status should be IN_PROGRESS
     *
     *  - If the league has a specified end date in the past then
     *    the status should be CLOSED
     *
     * @param leagueId The ID of the league being queried.
     *
     * @return The status of the league as enum as above
     *          PENDING       not yet started
     *          IN_PROGRESS   league is active
     *          CLOSED        current date is after specified league end date
     *
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public Status getLeagueStatus(int leagueId )
        throws IDInvalidException{

        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                LocalDate today = LocalDate.now();
                LocalDate startDate = l.getStartDate();
                LocalDate endDate = l.getEndDate();

                if (startDate == null || startDate.isAfter(today)) {
                    return Status.PENDING;
                } else if (!startDate.isAfter(today) && (endDate == null || endDate.isAfter(today))) {
                    return Status.IN_PROGRESS;
                } else if (endDate != null && endDate.isBefore(today)) {
                    if (l.getCloseDate() == null) {
                        l.setCloseDate(endDate);
                        League.serialiseLeagues(leagues);
                    }
                    return Status.CLOSED;
                }
            }
        }

        throw new IDInvalidException("ID does not match any league in the system.");

    };


    /**
     * Start league
     *
     * @param leagueId The ID of the league to start.
     * @param day Should be set to the epoch day when league will be made active.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws IllegalOperationException If the league is already started.
     */
    public void setLeagueStartDate(int leagueId, int day)
            throws IDInvalidException, IllegalOperationException {

        ArrayList<League> leagues = League.getLeagues();
        boolean leagueFound = false;

        for (League l : leagues) {
            if (l.getId() == leagueId) {
                LocalDate requestedStartDate = LocalDate.ofEpochDay(day);

                l.setStartDate(requestedStartDate);
                leagueFound = true;
                break;
            }
        }

        if (!leagueFound) {
            throw new IDInvalidException("ID does not match any league in the system.");
        }

        League.serialiseLeagues(leagues);
    }

    /** 
     * Close league, day specified may be any date after the league start day
     * 
     * @param leagueId The ID of the league to close.
     * @param day Should be set to the epoch day when league will be closed.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws IllegalOperationException If the league is already closed or invalid day.
     */
    public void setLeagueEndDate(int leagueId, int day) 
        throws IDInvalidException, IllegalOperationException{

        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                if (getLeagueStatus(leagueId) == Status.CLOSED) {
                    throw new IllegalOperationException("This league has already been closed.");
                }

                LocalDate endDate = LocalDate.ofEpochDay(day);
                l.setEndDate(endDate);
                League.serialiseLeagues(leagues);
                return;
            }
        }
        throw new IDInvalidException("ID does not match any league in the system.");

    };


    /**
     * Get the league start date (as epoch day).
     * 
     * @param leagueId The ID of the league being queried.
     * @return The start date of the league as epoch day.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public int getLeagueStartDate(int leagueId) throws IDInvalidException {
        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                LocalDate startDate = l.getStartDate();
                if (startDate == null) {
                    return -1; //
                }
                return (int) startDate.toEpochDay();
            }
        }
        throw new IDInvalidException("ID does not match any league in the system.");
    }



        /**
         * Get the date a closed league closed date (as epoch day).
         *
         * @param leagueId The ID of the league being queried.
         * @return The closure date of the league as epoch day or -1 if not closed.
         * @throws IDInvalidException If the ID does not match to any league in the system.
         */
    public int getLeagueCloseDate(int leagueId) throws IDInvalidException{

        ArrayList<League> leagues = League.getLeagues();
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                if (getLeagueStatus(leagueId) != Status.CLOSED) {
                    return -1;
                }
                return (int) l.getCloseDate().toEpochDay();
            }
        }
        throw new IDInvalidException("ID does not match any league in the system.");
    };


    /**
     * Reset league by removing all gameplay history i.e. no scores, and gives it pending status. 
     * status of active/inactive players is unchanged.
     * 
     * @param leagueId The ID of the league to reset.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     */
    public void resetLeague(int leagueId) throws IDInvalidException{
        
        return; // placeholder so class compiles
    };


    /**
     * Clone a league to make a new league. 
     * Owners of the original league are also owners of the new league.
     * Invitations are created for all players in the original league.
     * League status is set to pending.
     * 
     * @param leagueId The ID of the league to clone.
     * @param newName The name of the new league.
     * @return The ID of the new league.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws IllegalNameException If the new name already exists in the platform.
     */
    public int cloneLeague(int leagueId, String newName) 
        throws IDInvalidException, IllegalNameException{
        
        return 0; // placeholder so class compiles
    };


    /**
     * Checks if player is active in the league.
     * 
     * @param leagueId The ID of the league.
     * @param playerId The ID of the player.
     * 
     * @throws IDInvalidException If the ID does not match to any league or player in the league.
     */
    public boolean isLeaguePlayerActive(int leagueId, int playerId) 
        throws IDInvalidException{

        return false; // placeholder so class compiles
    };

    /** 
     * Sets a player to be registered as inactive in the league.
     * 
     * @param leagueId The ID of the league.
     * @param playerId The ID of the player.
     * 
     * @throws IDInvalidException If the ID does not match to any league or player in the system.
     * @throws IllegalOperationException If the ID does not match to any player in the league.
     */
    public void setLeaguePlayerInactive(int leagueId, int playerId) 
        throws IDInvalidException, IllegalOperationException {

        return; // placeholder so class compiles
    };

    /** 
     * Sets a player to be registered as active in the league.
     * 
     * @param leagueId The ID of the league.
     * @param playerId The ID of the player.
     * 
     * @throws IDInvalidException If the ID does not match to any league or player in the system.
     * @throws IllegalOperationException If the ID does not match to any player in the league.
     */
    public void setLeaguePlayerActive(int leagueId, int playerId) 
        throws IDInvalidException, IllegalOperationException{

        return; // placeholder so class compiles
    };


    /** 
     * Add owner   
     * 
     * @param leagueId The ID of the league to add the owner to.
     * @param playerId The ID of the player to add as an owner.
     * 
     * @throws IDInvalidException If the ID does not match to any league or player in the system.
     * @throws IllegalOperationException If the player is not a member of the league.
     * 
     */
    public void addOwner(int leagueId, int playerId) 
        throws IDInvalidException, IllegalOperationException{

        ArrayList<League> leagues = League.getLeagues();
        ArrayList<Player> players = Player.getPlayers();
        League targetLeague = null;
        Player targetPlayer = null;

        // find the league
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                targetLeague = l;
                break;
            }
        }
        if (targetLeague == null) {
            throw new IDInvalidException("ID does not match any league in the system.");
        }

        // find the player
        for (Player p : players) {
            if (p.getId() == playerId) {
                targetPlayer = p;
                break;
            }
        }
        if (targetPlayer == null) {
            throw new IDInvalidException("ID does not match any player in the system.");
        }

        // validate membership before adding owner
        if (!targetLeague.getLeaguePlayerIds().contains(playerId)) {
            throw new IllegalOperationException("Player must be a member of the league to be added as an owner.");
        }
        targetLeague.addOwner(playerId);
        League.serialiseLeagues(leagues);

    };

    /** 
     * Remove owner
     * 
     * @param leagueId The ID of the league to remove the owner from.
     * @param playerId The ID of the player to remove as an owner.
     * 
     * @throws IDInvalidException If the ID does not match to any league or player in the system.
     * @throws IllegalOperationException If the process would leave the league without an owner.
     * 
     */
    public void removeOwner(int leagueId, int playerId) 
        throws IDInvalidException, IllegalOperationException{

        ArrayList<League> leagues = League.getLeagues();
        ArrayList<Player> players = Player.getPlayers();
        League targetLeague = null;
        Player targetPlayer = null;

        // find the league
        for (League l : leagues) {
            if (l.getId() == leagueId) {
                targetLeague = l;
                break;
            }
        }
        if (targetLeague == null) {
            throw new IDInvalidException("ID does not match any league in the system.");
        }

        // find the player
        for (Player p : players) {
            if (p.getId() == playerId) {
                targetPlayer = p;
                break;
            }
        }
        if (targetPlayer == null) {
            throw new IDInvalidException("ID does not match any player in the system.");
        }

        // check if player is actually an owner
        if (!targetLeague.getOwnerIds().contains(playerId)) {
            throw new IllegalOperationException("Player is not an owner of the league.");
        }

        // check if this is the last remaining owner
        if (targetLeague.getOwnerIds().size() == 1) {
            throw new IllegalOperationException("Cannot remove the last remaining owner of the league.");
        }

        // safe to remove
        targetLeague.removeOwner(playerId);
        League.serialiseLeagues(leagues);
    };


    // Results

    /**
     * Register gameplay by a player in a league. 
     * The status of the player should be set to IN_PROGRESS.
     * 
     * @param leagueId The ID of the league being queried.
     * @param playerId The ID of the player being queried.
     * @param day The epoch day the game was played.
     * @param gameReport A report detailing the gameplay, may be empty if no report made,
     *                   this may be updated e.g. after other players take actions that affect result
     *
     * @throws IDInvalidException If ID do not match to any league & player in the system.
     * @throws IllegalOperationException If the day is not a valid day for the league.
     */
    public void registerGameReport(int day, int leagueId,  int playerId, String gameReport ) 
        throws IDInvalidException, IllegalOperationException{

        return; // placeholder so class compiles
    };


    /** 
     * Get the game report for a player in a league.
     * 
     * @param leagueId The ID of the league being queried.
     * @param playerId The ID of the player being queried.
     * @param day The epoch day the game was played.
     * 
     * @return The game report for the player in the league on the day, or empty string if no report entry.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not a valid day for the league.
     */
    public String getGameReport(int day, int leagueId,  int playerId) 
        throws IDInvalidException, InvalidDateException{

        return ""; // placeholder so class compiles
    };


    /**
     * Register day game scores. Will be called when all play in a round is complete.
     * with scored ordered to match player array returned by getLeaguePlayers().
     * Once scores are registered the game status for each player should be set to CLOSED.
     * 
     * @param day The epoch day the game was played.
     * @param leagueId The ID of the league being queried.
     * @param scores The game scores with order to match the array returned by getLeaguePlayers().
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws IllegalArgumentException If the day specified has already been closed,
     *                                  or if current date is 2 days or more after the day being voided.
     */
    public void registerDayScores(int day, int leagueId, int[] scores) 
        throws IDInvalidException, IllegalArgumentException{

        return; // placeholder so class compiles
    };


    /**
     * Register a void day for a league - all points set to zero
     *
     * @param leagueId The ID of the league being queried.
     * @param playerId The ID of the league being queried.
     * @param day The epoch day being queried.
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws IllegalArgumentException If the day is not a valid day for the league,
     *                 or the current day is 2 days or more after the day being voided.
     */
    public void voidDayPoints(int day, int leagueId) 
        throws IDInvalidException, IllegalArgumentException{

        return; // placeholder so class compiles
    };  


    /**
     * Get the status of league games for a given day.
     * with results ordered to match player array returned by getLeaguePlayers().
     * @param leagueId The ID of the league being queried.
     * @param day The epoch day being queried.
     * 
     * @return The status of the day as enum
     *          PENDING       no gameplay yet
     *          IN_PROGRESS   active and still games to play
     *          CLOSED        when all players have played or day has ended   
     *  
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not a valid day for the league.
     */
    public Status getDayStatus(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return Status.PENDING; // placeholder so class compiles
    };


    /**
     * Get the scores of a round for a given day.
     * with results ordered to match player array returned by getLeaguePlayers().
     *
     * @param leagueId The ID of the league being queried.
     * @param day The epoch day being queried.
     * 
     * @return An array of registered score results for each player from the day 
     *         or empty array if no gameplay from any players yet.
     *         (where gameplay is in progress the returned scores will be 0)
     *          
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not a valid day for the league.
     */
    public int[] getDayScores(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the league points of a league for a given day.
     * with results ordered to match player array returned by getLeaguePlayers().
     *
     * @param leagueId The ID of the league being queried.
     * @param day The epoch day being queried.
     * 
     * @return An array of the league points for each player from the day 
     *         or empty array if league points have not been finalised yet. 
     *          
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not a valid day for the league.
     */
    public int[] getDayPoints(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the player rankings of a league for a given day, 
     * with results ordered to match player array returned by getLeaguePlayers().
     * 
     * @param leagueId The ID of the league being queried.
     * @param day The epoch day being queried.
     * 
     * @return an array containing the rankings of the players
     *         or empty array if rankings not yet available.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not a valid day for the league.
     */
    public int[] getDayRanking(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the status of a league for a given week.
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the week being queried.
     * 
     * @return The status of the day as enum
     *          PENDING       no gameplay yet
     *          IN_PROGRESS   active and still games to play
     *          CLOSED        ended all games played or week ended    
     *  
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not within a valid week for the league.
     */
    public Status getWeekStatus(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return Status.PENDING; // placeholder so class compiles
    };


    /**
     * Get the league points of a league for a given week,
     * with results ordered to match player array returned by getLeaguePlayers().
     *  
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the week being queried.
     * 
     * @return An array of the points of each players total for the week (or part week played) 
     *         or empty array if no points yet.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not within a valid week for the league.
     */
    public int[] getWeekPoints(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the ranking of a league for a given week.
     * with results ordered to match player array returned by getLeaguePlayers().
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the week being queried.
     * 
     * @return An array of the rankings of each players for the week (or part week played) 
     *         or empty array if no rankings yet.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException     If the day is not within a valid week for the league.
     */
    public int[] getWeekRanking(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the status of a league for a given month.
     * 
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the month being queried.
     * 
     * @return The status of the month as enum
     *          PENDING       no gameplay yet
     *          IN_PROGRESS   active and still games to play
     *          CLOSED        ended all games played or month ended    
     *  
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not within a valid month for the league.
     */
    public Status getMonthStatus(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return Status.PENDING; // placeholder so class compiles
    };


    /**
     * Get the league points of a league for a given month.
     * with results ordered to match player array returned by getLeaguePlayers().
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the month being queried.
     * 
     * @return An array of the points of each players total for the month (or part month played) 
     *         or empty array if no points yet.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not within a valid month for the league.
     */
    public int[] getMonthPoints(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the ranking of a league for a given month.
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the month being queried.
     * 
     * @return An array of the rankings of each players for the month (or part month played) 
     *         or empty array if no rankings yet.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException     If the day is not within a valid month for the league.
     */
    public int[] getMonthRanking(int leagueId, int day ) 
            throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the status of a league for a given year.
     * 
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the year being queried.
     * 
     * @return The status of the day as enum
     *          PENDING       no gameplay yet
     *          IN_PROGRESS   active and still games to play
     *          CLOSED        ended all games played or year has ended    
     *  
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not within a valid year for the league.
     */
    public Status getYearStatus(int leagueId, int day ) 
            throws IDInvalidException, InvalidDateException{

        return Status.PENDING; // placeholder so class compiles
    };


    /**
     * Get the league points of a league for a given year.
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the year being queried.
     * 
     * @return An array of the league points of each players total for the year (or part year played) 
     *         or empty array if no points yet.
     * 
     * @throws IDInvalidException If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not within a valid month for the league.
     */
    public int[] getYearPoints(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Get the ranking of a league for a given year.
     * 
     * @param leagueId  The ID of the league being queried.
     * @param day       Epoch day that is within the year being queried.
     * 
     * @return An array of the rankings of each players for the year (or part year played) 
     *         or empty array if no rankings yet.
     * 
     * @throws IDInvalidException   If the ID does not match to any league in the system.
     * @throws InvalidDateException If the day is not within a valid year for the league.
     **/
    public int[] getYearRanking(int leagueId, int day ) 
        throws IDInvalidException, InvalidDateException{

        return new int[0]; // placeholder so class compiles
    };


    /**
     * Method empties this GamesLeague instance of its contents and resets all
     * internal counters.
     */
    public void eraseGamesLeagueData(){

        return; // placeholder so class compiles
    };


    /**
     * Method saves this GamesLeague instance contents into a serialised file,
     * with the filename given in the argument.
     * <p>
     * The state of this GamesLeague instance must be unchanged if any
     * exceptions are thrown.
     *
     * @param filename Location of the file to be saved.
     * @throws IOException If there is a problem experienced when trying to save the 
     *                     contents to the file.
     */
    public void saveGamesLeagueData(String filename) throws IOException{

        return; // placeholder so class compiles
    };


    /**
     * Method should load and replace this GamesLeague instance contents with the
     * serialised contents stored in the file given in the argument.
     *
     * @param filename Location of the file to be loaded.
     * @throws IOException If there is a problem experienced when trying
     *                     to load the store contents from the file.
     * @throws ClassNotFoundException If required class files cannot be found when loading.
     */
    public void loadGamesLeagueData(String filename) throws IOException, ClassNotFoundException{

        return; // placeholder so class compiles
    };




}
