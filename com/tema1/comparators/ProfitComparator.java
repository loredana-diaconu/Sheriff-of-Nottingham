package com.tema1.comparators;

import com.tema1.goods.Goods;

import java.util.Comparator;

/**
 * Comparator pentru profitul bunurilor.
 */
public final class ProfitComparator implements Comparator<Goods> {
    private IdComparator idComp = new IdComparator();
    public int compare(final Goods g1, final Goods g2) {
        if (g1.getProfit() < g2.getProfit()) {
            return 1;
        } else {
            if (g1.getProfit() > g2.getProfit()) {
                return -1;
            } else {
                return idComp.compare(g1, g2);
            }
        }
    }
}
