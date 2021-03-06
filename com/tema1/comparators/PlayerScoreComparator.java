package com.tema1.comparators;

import com.tema1.players.Player;

import java.util.Comparator;

/**
 * Comparator pentru scorul jucatorilor.
 */
public final class PlayerScoreComparator implements Comparator<Player> {
    @Override
    public int compare(final Player p1, final Player p2) {
        PlayerIdComparator comp = new PlayerIdComparator();
        if (p1.getCoins() > p2.getCoins()) {
            return -1;
        } else {
            if (p1.getCoins() < p2.getCoins()) {
                return 1;
            } else {
                return comp.compare(p1, p2);
            }
        }
    }
}
