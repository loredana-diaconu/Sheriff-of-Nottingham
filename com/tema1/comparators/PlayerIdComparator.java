package com.tema1.comparators;

import com.tema1.players.Player;

import java.util.Comparator;

/**
 * Comparator pentru id-ul jucatorilor.
 */
public final class PlayerIdComparator implements Comparator<Player> {
    public int compare(final Player p1, final Player p2) {
        if (p1.getId() <= p2.getId()) {
            return -1;
        } else {
            return 1;
        }
    }
}
