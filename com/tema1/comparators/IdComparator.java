package com.tema1.comparators;

import com.tema1.goods.Goods;

import java.util.Comparator;

/**
 * Comparator pentru id-ul bunurilor.
 */
public final class IdComparator implements Comparator<Goods> {
    public int compare(final Goods g1, final Goods g2) {
        if (g1.getId() >= g2.getId()) {
            return -1;
        } else {
            return 1;
        }
    }
}
