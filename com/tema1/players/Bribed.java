package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.common.SortFunctions;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;

import java.util.ArrayList;

/**
 * Descrie jucatorul Bribed.
 */
public final class Bribed extends Basic {
    private ArrayList<Goods> legal;
    private ArrayList<Goods> illegal;
    private Player left;
    private Player right;

    Bribed() {
        super();
        this.legal = new ArrayList<>();
        this.illegal = new ArrayList<>();
    }

    /**
     * Imparte bunurile din mana in bunuri legale si ilegale.
     */
    private void splitGoodsByLegality() {
        for (Goods good : getPossessedGoods()) {
            if (good.getType().equals(GoodsType.Illegal)) {
                illegal.add(good);
            } else {
                legal.add(good);
            }
        }
    }

    /**
     * Adauga bunurile in sac. Procedeaza ca Basic cand nu are bunuri ilegale sau
     * nu isi permite sa riste.
     */
    @Override
    public void addToBag(final int round) {
        splitGoodsByLegality();
        if (illegal.isEmpty() || getCoins() <= Constants.SMALL_BRIBE) {
            setBribe(0);
            super.addToBag(round);
        } else {
            setHonest(false);
            ArrayList<Goods> toAdd = getGoodsInBag();
            int maxGoods;
            if (getCoins() < Constants.GREAT_BRIBE) {
                // Isi permite maxim 2 bunuri ilegale.
                maxGoods = Constants.SMALL_BRIBE_QUANTITY;
            } else {
                // Poate pune maxim 8 bunuri ilegale.
                maxGoods = Constants.MAX_BAG;
            }
            // Declara mere.
            setDeclaredGood(GoodsFactory.getInstance().getGoodsById(0));
            SortFunctions.sortByProfit(illegal);
            SortFunctions.sortByProfit(legal);
            // Bunuri ilegale adaugate.
            int noGoods = 0;
            int penalty = Constants.ILLEGAL_PENALTY;
            // Adauga cate bunuri ilegale are, in limita spatiului si banilor.
            for (int i = 0; i < maxGoods && i < illegal.size(); i++) {
                if (penalty < getCoins()) {
                    penalty = penalty + Constants.ILLEGAL_PENALTY;
                    noGoods++;
                    toAdd.add(illegal.get(i));
                }
            }
            // Procedeaza asemanator pentru bunuri legale, daca mai
            // poate completa.
            int noLegal = maxGoods - noGoods;
            penalty = Constants.LEGAL_PENALTY;
            int coinsLeft = getCoins() - noGoods * Constants.ILLEGAL_PENALTY;
            for (int i = 0; i < legal.size() & i < noLegal; i++) {
                if (penalty < coinsLeft) {
                    penalty = penalty + Constants.LEGAL_PENALTY;
                    Goods good = legal.get(i);
                    toAdd.add(good);
                }
            }
            setGoodsInBag(toAdd);
            // Decide mita in funtie de numarul bunurilor ilegale.
            if (noGoods > 2) {
                setBribe(Constants.GREAT_BRIBE);
            } else {
                setBribe(Constants.SMALL_BRIBE);
            }
        }
        illegal.clear();
        legal.clear();
    }

    /**
     * Afla jucatorul din stanga sa.
     */
    private void setLeft(final ArrayList<Player> playerArray) {
        int id = getId();
        if (id == 0) {
            left = playerArray.get(playerArray.size() - 1);
        } else {
            left = playerArray.get(id - 1);
        }
    }

    /**
     * Afla jucatorul din dreapta sa.
     */
    private void setRight(final ArrayList<Player> playerArray) {
        int id = getId();
        if (id == playerArray.size() - 1) {
            right = playerArray.get(0);
        } else {
            right = playerArray.get(id + 1);
        }
    }

    /**
     * Strategia jucatorului Bribed in rolul de Sheriff.
     */
    @Override
    public void playSheriff(final ArrayList<Player> playerArray) {
        setLeft(playerArray);
        setRight(playerArray);
        if (getCoins() >= Constants.MIN_SUM) {
            checkPlayer(left);
        }
        if (getCoins() >= Constants.MIN_SUM) {
            // Se asigura ca nu verifica acelasi jucator de 2 ori.
            if (left != right) {
                checkPlayer(right);
            }
        }
        for (Player p : playerArray) {
            // Ia mita de la ceilalti jucatori.
            if (p != this && p != left && p != right) {
                acceptBribe(p);
            }
        }
    }
}
