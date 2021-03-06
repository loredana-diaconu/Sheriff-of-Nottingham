package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.comparators.IdComparator;
import com.tema1.main.GameInput;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.goods.GoodsFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Descrie un jucator oarecare.
 */
public abstract class Player {
    private int id;
    private int coins;
    private int bribe;
    private boolean honest;
    private String strategy;
    private PlayerRole role;
    private Goods declaredGood;
    private ArrayList<Goods> possessedGoods;
    private ArrayList<Goods> goodsInBag;
    private ArrayList<Goods> goodsOnStand;
    private HashMap<Goods, Integer> countStand;

    Player() {
        bribe = 0;
        honest = true;
        coins = Constants.STANDARD_SUM;
        role = PlayerRole.Merchant;
        possessedGoods = new ArrayList<>();
        goodsInBag = new ArrayList<>();
        goodsOnStand = new ArrayList<>();
        countStand = new HashMap<>();
    }

    public final HashMap<Goods, Integer> getCountStand() {
        return countStand;
    }

    public final ArrayList<Goods> getGoodsOnStand() {
        return goodsOnStand;
    }

    public final void setGoodsOnStand(final ArrayList<Goods> goodsOnStand) {
        this.goodsOnStand = goodsOnStand;
    }

    final ArrayList<Goods> getGoodsInBag() {
        return goodsInBag;
    }

    final void setGoodsInBag(final ArrayList<Goods> goodsInBag) {
        this.goodsInBag = goodsInBag;
    }

    final int getBribe() {
        return bribe;
    }

    final void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    final Goods getDeclaredGood() {
        return declaredGood;
    }

    final void setDeclaredGood(final Goods declaredGood) {
        this.declaredGood = declaredGood;
    }

    private boolean isHonest() {
        return honest;
    }

    final void setHonest(final boolean isHonest) {
        this.honest = isHonest;
    }

    public final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }

    public final String getStrategy() {
        return strategy;
    }

    public final void setStrategy(final String strategy) {
        this.strategy = strategy;
    }

    public final int getCoins() {
        return coins;
    }

    public final void setCoins(final int coins) {
        this.coins = coins;
    }

    public final ArrayList<Goods> getPossessedGoods() {
        return possessedGoods;
    }

    public final void setPossessedGoods(final ArrayList<Goods> possessedGoods) {
        this.possessedGoods = possessedGoods;
    }

    public final PlayerRole getRole() {
        return role;
    }

    public final void setRole(final PlayerRole role) {
        this.role = role;
    }

    public abstract void addToBag(int round);

    public abstract void playSheriff(ArrayList<Player> playerArray);

    /**
     * Completeaza bunurile din mana si le sterge din pachet.
     */
    public void drawCards(final GameInput input, final GoodsFactory instance) {
        for (int i = 0; i < Constants.CARDS_IN_HAND; i++) {
            int cardId = input.getAssetIds().get(i);
            possessedGoods.add(instance.getGoodsById(cardId));
        }
        input.getAssetIds().subList(0, Constants.CARDS_IN_HAND).clear();
    }

    /**
     * Verifica daca un jucator poseda bunuri legale.
     */
    final boolean hasLegalGoods() {
        IdComparator idComp = new IdComparator();
        possessedGoods.sort(idComp);
        int smallestId = Constants.CARDS_IN_HAND - 1;
        Goods lastGood = possessedGoods.get(smallestId);
        // Daca cel mai mic bun ca id este ilegal, inseamna ca nu are bunuri
        // legale.
        return !lastGood.getType().equals(GoodsType.Illegal);
    }

    /**
     * Verificarea jucatorului p de catre Sheriff.
     */
    final void checkPlayer(final Player p) {
        // Suma schimbata.
        int sum;
        if (p.isHonest()) {
            // Sherifful plateste.
            int numGoods = p.getGoodsInBag().size();
            sum = numGoods * p.getDeclaredGood().getPenalty();
            p.setCoins(p.getCoins() + sum);
            this.setCoins(this.getCoins() - sum);
        } else {
            // Comerciantul plateste
            ArrayList<Goods> toRemove = new ArrayList<>();
            for (Goods good : p.getGoodsInBag()) {
                GoodsType type = good.getType();
                Goods declared = p.getDeclaredGood();
                if (!good.equals(declared) || type.equals(GoodsType.Illegal)) {
                    toRemove.add(good);
                }
            }
            sum = 0;
            for (Goods good : toRemove) {
                sum += good.getPenalty();
            }
            p.setCoins(p.getCoins() - sum);
            this.setCoins(getCoins() + sum);
            ArrayList<Goods> inBag = p.getGoodsInBag();
            // Confisca bunurile.
            inBag.removeAll(toRemove);
            p.setGoodsInBag(inBag);
        }
    }

    /**
     * Pune pe taraba bunurile din sac.
     */
    public final void fillStand() {
        goodsOnStand.addAll(goodsInBag);
        goodsInBag.clear();
    }

    /**
     * Pune bunurile de pe taraba intr-un HashMap de frecventa.
     */
    public void updateStandFrequency() {
        for (Goods good : goodsOnStand) {
            countStand.put(good, countStand.getOrDefault(good, 0) + 1);
        }
    }
}
