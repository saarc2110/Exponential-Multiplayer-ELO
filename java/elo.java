package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player in an ELO rating system.
 */
public class ELOPlayer {
    private String name;
    private int place;
    private int eloPre;
    private int eloPost;
    private int eloChange;

    public ELOPlayer() {
    }

    public ELOPlayer(String name, int place, int eloPre) {
        this.name = name;
        this.place = place;
        this.eloPre = eloPre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getEloPre() {
        return eloPre;
    }

    public void setEloPre(int eloPre) {
        this.eloPre = eloPre;
    }

    public int getEloPost() {
        return eloPost;
    }

    public void setEloPost(int eloPost) {
        this.eloPost = eloPost;
    }

    public int getEloChange() {
        return eloChange;
    }

    public void setEloChange(int eloChange) {
        this.eloChange = eloChange;
    }
}

class ELOMatch {
    private ArrayList<ELOPlayer> players = new ArrayList<ELOPlayer>();

    
    /*
     * Adds a player to the match.
     * @param name The name of the player.
     * @param place The place the player finished in the match.
     * @param elo The ELO of the player before the match.
     */
    
    public void addPlayer(String name, int place, int elo) {
        ELOPlayer player = new ELOPlayer(name, place, elo);
        players.add(player);
    }

    /*
     * Returns the ELO of a player.
     * If the player is not in the match, returns 1500.
     * @param name The name of the player.
     * @return The ELO of the player.
     */
    public int getELO(String name) {
        for (ELOPlayer p : players) {
            if (p.getName().equals(name)) {
                return p.getEloPost();
            }
        }
        return 1500;
    }
    
    /*
     * Returns the change in ELO for a player.
     */

    public int getELOChange(String name) {
        for (ELOPlayer p : players) {
            if (p.getName().equals(name)) {
                return p.getEloChange();
            }
        }
        return 0;
    }

    /*
     * Calculates the ELOs for all players in the match.
     */

    public void calculateELOs() {
        int n = players.size();
        float k = 32 / (float) (n - 1);
        double alpha = 1.25;

        for (int i = 0; i < n; i++) {
            int curPlace = players.get(i).getPlace();
            int curELO = players.get(i).getEloPre();
            double numerator = 0;
            double den = 0;

            for (int j = 0; j < n; j++) {
                int opponentPlace = players.get(j).getPlace();
                den += Math.pow(alpha, n - opponentPlace) - 1;

                if (i != j) {
                    int opponentELO = players.get(j).getEloPre();
                    numerator += 1 / (1 + Math.pow(10, (opponentELO - curELO) * 0.0025));
                }
            }
            
            double ea = numerator / ((double) (n * (n - 1)) / 2);
            double sa = (Math.pow(alpha, n - curPlace) - 1) / den;
            int eloPost = (int) Math.round(curELO + k * (n - 1) * (sa - ea));
            int eloChange = eloPost - curELO;

            players.get(i).setEloPost(eloPost);
            players.get(i).setEloChange(eloChange);
        }
    }
}
