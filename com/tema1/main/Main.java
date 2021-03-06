package com.tema1.main;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.players.Player;
import com.tema1.players.PlayerFactory;
import com.tema1.players.PlayerRole;
import com.tema1.score.LeaderBoard;
import com.tema1.score.Score;

import java.util.ArrayList;

public final class Main {
    private Main() {
        // just to trick checkstyle
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        ArrayList<Player> playerArray = new ArrayList<>();
        GoodsFactory instance = GoodsFactory.getInstance();
        PlayerFactory playerFactory = new PlayerFactory();
        int p = 0;
        // Creeaza toti jucatorii.
        for (String role : gameInput.getPlayerNames()) {
            Player player = playerFactory.getPlayer(role);
            player.setStrategy(role.toUpperCase());
            playerArray.add(player);
            player.setId(p);
            p++;
        }
        for (int i = 0; i < gameInput.getRounds(); i++) {
            // Desfasurare runda.
            for (int j = 0; j < playerArray.size(); j++) {
                // Desfasurare subrunda.
                playerArray.get(j).setRole(PlayerRole.Sheriff);
                // Imparte cartile.
                for (Player player : playerArray) {
                    if (player.getRole() == PlayerRole.Merchant) {
                        player.drawCards(gameInput, instance);
                        // Adauga bunuri in sac.
                        player.addToBag(i + 1);
                    }
                }
                // Inspectia.
                playerArray.get(j).playSheriff(playerArray);
                playerArray.get(j).setRole(PlayerRole.Merchant);
                // Arde cartile si pune bunurile pe taraba.
                for (Player player : playerArray) {
                    ArrayList<Goods> posGoods = player.getPossessedGoods();
                    posGoods.clear();
                    player.setPossessedGoods(posGoods);
                    player.fillStand();
                }
            }
        }
        // Calculeaza scorul si afiseaza clasamentul.
        Score.getScore(playerArray);
        LeaderBoard.display(playerArray);
    }
}
