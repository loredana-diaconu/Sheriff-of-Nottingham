package com.tema1.common;

import com.tema1.comparators.FrequencyComparator;
import com.tema1.comparators.PlayerComparator;
import com.tema1.comparators.PlayerScoreComparator;
import com.tema1.comparators.ProfitComparator;
import com.tema1.goods.Goods;
import com.tema1.players.Player;

import java.util.ArrayList;
import java.util.HashMap;

public final class SortFunctions {
    private SortFunctions() {
    }

    /**
     * Sorteaza bunurile din array dupa frecventa lor.
     */
    public static void sortByFrequency(final ArrayList<Goods> goodsArray) {
        // HashMap cu frecventa bunurilor detinute.
        HashMap<Goods, Integer> freq = new HashMap<>();
        FrequencyComparator comp = new FrequencyComparator(freq);
        for (Goods good : goodsArray) {
            freq.put(good, freq.getOrDefault(good, 0) + 1);
        }
        goodsArray.sort(comp);
    }

    public static void sortByProfit(final ArrayList<Goods> goodsArray) {
        ProfitComparator profitComp = new ProfitComparator();
        goodsArray.sort(profitComp);
    }

    /**
     * Sorteaza jucatorii dupa frecventa bunului "good" de pe taraba.
     * @return Lista de jucatori sortata.
     */
    public static ArrayList<Player> sortKing(final ArrayList<Player> players, final Goods good) {
        PlayerComparator comp = new PlayerComparator(good);
        players.sort(comp);
        return players;
    }

    public static void sortByScore(final ArrayList<Player> playerArray) {
        PlayerScoreComparator comp = new PlayerScoreComparator();
        playerArray.sort(comp);
    }
}
