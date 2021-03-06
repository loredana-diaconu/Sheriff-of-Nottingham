package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.comparators.ProfitComparator;
import com.tema1.goods.Goods;

import java.util.ArrayList;

/**
 * Descrie jucatorul Greedy.
 */
public final class Greedy extends Basic {
    /**
     * Procedeaza ca Basic in rolul de Sheriff, dar accepta mita.
     */
    @Override
    public void playSheriff(final ArrayList<Player> playerArray) {
        for (Player p : playerArray) {
            if (p.getRole().equals(PlayerRole.Merchant)) {
                if (getCoins() >= Constants.MIN_SUM) {
                    if (p.getBribe() == 0) {
                        checkPlayer(p);
                    } else {
                        acceptBribe(p);
                    }
                }
            }
        }
    }

    /**
     * Adauga bunurile in sac.
     * Procedeaza ca Basic, cu exceptia rundelor pare.
     * @param round Utilizata de jucatorul Greedy, care este un tip de Basic.
     */
    @Override
    public void addToBag(final int round) {
        super.addToBag(round);
        ProfitComparator profitComp = new ProfitComparator();
        // Bunurile ilegale respinse in jocul onest.
        ArrayList<Goods> illegalGoods = getIllegal();
        if (round % 2 == 0) {
            ArrayList<Goods> toAdd = getGoodsInBag();
            if (getGoodsInBag().size() < Constants.MAX_BAG) {
                if (illegalGoods.size() > 0) {
                    setHonest(false);
                    illegalGoods.sort(profitComp);
                    toAdd.add(illegalGoods.get(0));
                }
            }
            setGoodsInBag(toAdd);
        }
        illegalGoods.clear();
        setIllegal(illegalGoods);
    }
}
