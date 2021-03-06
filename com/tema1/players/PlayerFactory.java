package com.tema1.players;

public final class PlayerFactory {
    public Player getPlayer(final String role) {
        if (role.equals("basic")) {
            return new Basic();
        }
        if (role.equals("bribed")) {
            return new Bribed();
        }
        if (role.equals("greedy")) {
            return new Greedy();
        }
        return null;
    }
}
