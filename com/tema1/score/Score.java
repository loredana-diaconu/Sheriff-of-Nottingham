package com.tema1.score;

import com.tema1.common.Constants;
import com.tema1.common.SortFunctions;
import com.tema1.players.Player;
import com.tema1.goods.Goods;
import com.tema1.goods.IllegalGoods;
import com.tema1.goods.LegalGoods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.ArrayList;
import java.util.Map;

/**
 * Calculeaza scorul final pentru fiecare jucator.
 */
public final class Score {
    private Score() {
    }
    /**
     * Calculeaza scorul pentru fiecare jucator.
     */
    public static void getScore(final ArrayList<Player> players) {
        computeIllegalBonus(players);
        computeProfit(players);
        computeKingQueen(players);
    }

    /**
     * Calculeaza profitul provenit din bunurile de pe taraba.
     */
    private static void computeProfit(final ArrayList<Player> playerArray) {
        for (Player player : playerArray) {
            for (Goods good : player.getGoodsOnStand()) {
                int coins = player.getCoins();
                coins += good.getProfit();
                player.setCoins(coins);
            }
        }
    }

    /**
     * Calculeaza bonusul provenit din bunuri ilegale.
     */
    private static void computeIllegalBonus(final ArrayList<Player> playerArray) {
        for (Player player : playerArray) {
            // bunurile legale ce vor fi adaugate pe taraba
            ArrayList<Goods> addToStand = new ArrayList<>();
            ArrayList<Goods> onStand = player.getGoodsOnStand();
            for (Goods good : onStand) {
                if (good.getType().equals(GoodsType.Illegal)) {
                    Map<Goods, Integer> illegalBonus = ((IllegalGoods) good).getIllegalBonus();
                    for (Map.Entry<Goods, Integer> entry : illegalBonus.entrySet()) {
                        // adaugarea bunurilor legale corespunzatoare pe taraba
                        Goods legalGood = entry.getKey();
                        Integer value = entry.getValue();
                        for (int i = 0; i < value; i++) {
                            addToStand.add(legalGood);
                        }
                    }
                }
            }
            onStand.addAll(addToStand);
            player.setGoodsOnStand(onStand);
        }
    }

    /**
     * Calculeaza bonusurile de King si Queen.
     */
    private static void computeKingQueen(final ArrayList<Player> players) {
        for (Player p : players) {
            // Numara bunurile de fiecare tip de pe taraba jucatorului p.
            p.updateStandFrequency();
        }
        for (int id = 0; id <= Constants.LAST_LEGAL_GOOD; id++) {
            Goods good = GoodsFactory.getInstance().getGoodsById(id);
            ArrayList<Player> sorted = SortFunctions.sortKing(players, good);
            Player king;
            Player queen;
            // Daca exista jucatori cu bunul respectiv pe taraba, iau bonusul
            // de King si Queen primii doi.
            if (sorted.get(0).getCountStand().containsKey(good)) {
                king = sorted.get(0);
                int kCoins = king.getCoins();
                kCoins += ((LegalGoods) good).getKingBonus();
                king.setCoins(kCoins);
            }
            if (sorted.get(1).getCountStand().containsKey(good)) {
                queen = sorted.get(1);
                int qCoins = queen.getCoins();
                qCoins += ((LegalGoods) good).getQueenBonus();
                queen.setCoins(qCoins);
            }
        }
    }
}
