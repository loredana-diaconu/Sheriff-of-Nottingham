package com.tema1.comparators;

import com.tema1.goods.Goods;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Comparator pentru frecventa bunurilor din sac.
 * Daca bunurile au aceeasi frecventa, compara dupa profit si apoi id.
 */
public final class FrequencyComparator implements Comparator<Goods> {
    // HashMap cu frecventa bunurilor.
    private HashMap<Goods, Integer> goodsMap;

    public FrequencyComparator(final HashMap<Goods, Integer> goodsMap) {
        this.goodsMap = goodsMap;
    }

    @Override
    public int compare(final Goods g1, final Goods g2) {
        ProfitComparator profitComp = new ProfitComparator();
        if (goodsMap.get(g1) < goodsMap.get(g2)) {
            return 1;
        } else {
            if (goodsMap.get(g1) > goodsMap.get(g2)) {
                return -1;
            } else {
                return profitComp.compare(g1, g2);
            }
        }
    }
}
