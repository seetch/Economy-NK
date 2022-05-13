package xyz.minerune.economy.provider;

import java.util.LinkedHashMap;

public interface Provider {

    public void create(String playerName, Double defaultMoney);

    public boolean has(String playerName);

    public Double get(String playerName);

    public void set(String playerName, Double amount);

    public LinkedHashMap<String, Double> getAll();

    public String getName();
}
