import gamesleague.*;
import java.util.Arrays;

public class Test01PlayerBasic {
    public static void main(String[] args) {
        System.out.println("\n#### Running Test01PlayerBasic...\n");

        GamesLeague gl = new GamesLeague();

        int p1 = gl.createPlayer("wardlejj@wardle.com",  "BigJW", "Josh Wardle", "123-456-7890");
        System.out.println("Created Player with id: " + p1);
        int p2 = gl.createPlayer("annakw@wardle.com", "AKAnna", "Anna Karl-Witham",  "");
        System.out.println("Created Player with id: " + p2);

        System.out.println("Player ids now stored in GamesLeague backend:");
        int[] playerIds = gl.getPlayerIds();
        System.out.println("Players:" + Arrays.toString(playerIds));
        for (int id : playerIds) {
            String pEmail = gl.getPlayerEmail(id);
            String pDisplayName = gl.getPlayerDisplayName(id);       
            String infoString = String.format("Player[id=%d, displayName=%s, email=%s]", id, pDisplayName, pEmail);
            System.out.println(infoString);
        }

        System.out.println("Deactivating player with id: " + p1);
        gl.deactivatePlayer(p1);
        for (int id : playerIds) {
            String pEmail = gl.getPlayerEmail(id);
            String pDisplayName = gl.getPlayerDisplayName(id);       
            String infoString = String.format("Player[id=%d, displayName=%s, email=%s]", id, pDisplayName, pEmail);
            System.out.println(infoString);
        }

        for (int id : playerIds) {
            if ( gl.isDeactivatedPlayer(id) ) {
                System.out.println("Player with id " + id + " is deactivated");
            } else {
                System.out.println("Player with id " + id + " is active");
            }
        }
    }
}