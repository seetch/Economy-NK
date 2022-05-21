package xyz.minerune.economy.provider;

import java.util.LinkedHashMap;

public interface Provider {

    void create(String playerName, int defaultMoney);

    boolean has(String playerName);

    int get(String playerName);

    void set(String playerName, int amount);

    LinkedHashMap<String, Integer> getAll();

    String getName();
}
