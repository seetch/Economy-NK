package xyz.minerune.economy.provider;

import java.util.LinkedHashMap;

public interface Provider {

    void create(String playerName, Double defaultMoney);

    boolean has(String playerName);

    Double get(String playerName);

    void set(String playerName, Double amount);

    LinkedHashMap<String, Double> getAll();

    String getName();
}
