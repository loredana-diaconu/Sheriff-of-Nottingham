package com.tema1.comparators;

import com.tema1.goods.Goods;
import com.tema1.players.Player;

import java.util.Comparator;

/**
 * Comparator pentru sortarea jucatorilor in functie de frecventa bunului
 * "good" de pe taraba. Utilizat pentru calcularea bonusului pentru bunurile
 * ilegale.
 */
public final class PlayerComparator implements Comparator<Player> {
    private Goods good;

    public PlayerComparator(final Goods good) {
        this.good = good;
    }

    @Override
    public int compare(final Player p1, final Player p2) {
        PlayerIdComparator idComp = new PlayerIdComparator();
        if (p1.getCountStand().get(good) == null) {
            if (p2.getCountStand().get(good) != null) {
                return 1;
            } else {
                return idComp.compare(p1, p2);
            }
        } else {
            // Tratare caz null.
            if (p2.getCountStand().get(good) == null) {
                return -1;
            } else {
                if (p1.getCountStand().get(good) < p2.getCountStand().get(good)) {
                    return 1;
                } else {
                    if (p1.getCountStand().get(good) > p2.getCountStand().get(good)) {
                        return -1;
                    } else {
                        return idComp.compare(p1, p2);
                    }
                }
            }
        }
    }
}
