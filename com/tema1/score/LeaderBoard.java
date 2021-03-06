package com.tema1.score;

import com.tema1.common.SortFunctions;
import com.tema1.players.Player;

import java.util.ArrayList;

/**
 * Sorteaza jucatorii dupa scor si afiseaza clasamentul.
 */
public final class LeaderBoard {
    private LeaderBoard() {
    }
    public static void display(final ArrayList<Player> playerArray) {
        SortFunctions.sortByScore(playerArray);
        for (Player p : playerArray) {
            System.out.println(p.getId() + " " + p.getStrategy() + " " + p.getCoins());
        }
    }
}
