package xyz.minerune.economy.provider;

import java.util.LinkedHashMap;

public interface Provider {

    void createAccount(String playerName, int defaultMoney);

    boolean hasAccount(String playerName);

    int getMoney(String playerName);

    void setMoney(String playerName, int amount);

    LinkedHashMap<String, Integer> getAll();

    String getName();
}
