package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.common.SortFunctions;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.goods.GoodsFactory;

import java.util.ArrayList;

/**
 * Descrie jucatorul Basic.
 */
public class Basic extends Player {
    private ArrayList<Goods> illegal;

    Basic() {
        super();
        illegal = new ArrayList<>();
    }

    final ArrayList<Goods> getIllegal() {
        return illegal;
    }

    final void setIllegal(final ArrayList<Goods> illegal) {
        this.illegal = illegal;
    }

    /**
     * Strategia de joc daca poseda carti legale.
     */
    private void playHonest() {
        setHonest(true);
        ArrayList<Goods> toRemove = new ArrayList<>();
        ArrayList<Goods> goods = getPossessedGoods();
        ArrayList<Goods> toAdd = getGoodsInBag();
        for (Goods good : goods) {
            if (good.getType().equals(GoodsType.Illegal)) {
                // Nu ii mai trebuie bunurile ilegale.
                toRemove.add(good);
            }
        }
        goods.removeAll(toRemove);
        // Alege bunul cu frecventa cea mai mare si le adauga pe toate de
        // acel tip.
        SortFunctions.sortByFrequency(goods);
        setDeclaredGood(goods.get(0));
        for (Goods good : goods) {
            if (good.getId() == getDeclaredGood().getId()) {
                toAdd.add(good);
            }
        }
        setPossessedGoods(goods);
        setGoodsInBag(toAdd);
        illegal = toRemove;
    }

    /**
     * Strategia de joc daca are doar carti ilegale.
     */
    private void playLiar() {
        ArrayList<Goods> goods = getPossessedGoods();
        ArrayList<Goods> toAdd = getGoodsInBag();
        illegal = goods;
        setHonest(false);
        // Alege bunul cu profitul cel mai mare.
        SortFunctions.sortByProfit(goods);
        Goods addedGood = goods.get(0);
        toAdd.add(addedGood);
        goods.remove(addedGood);
        setDeclaredGood(GoodsFactory.getInstance().getGoodsById(0));
        setPossessedGoods(goods);
        setGoodsInBag(toAdd);
    }

    /**
     * Adaugarea bunurilor in sac in rolul de Comerciant.
     * @param round Utilizata de jucatorul Greedy, care este un tip de Basic.
     */
    public void addToBag(final int round) {
        if (!hasLegalGoods()) {
            playLiar();
        } else {
            playHonest();
        }
    }

    /**
     * Strategia in rolul de Sheriff.
     */
    public void playSheriff(final ArrayList<Player> playerArray) {
        for (Player p : playerArray) {
            if (p.getRole().equals(PlayerRole.Merchant)) {
                if (getCoins() >= Constants.MIN_SUM) {
                    checkPlayer(p);
                }
            }
        }
    }

    /**
     * Ia mita de la jucatorul p.
     * @param player Ofera mita.
     */
    final void acceptBribe(final Player player) {
        // Suma schimbata.
        int sum = player.getBribe();
        int coins = player.getCoins();
        coins -= sum;
        player.setCoins(coins);
        coins = this.getCoins();
        coins += sum;
        this.setCoins(coins);
    }
}
