package xyz.minerune.economy.provider.sqlite;

import me.hteppl.data.database.SQLiteDatabase;
import org.sql2o.Connection;
import xyz.minerune.economy.provider.Provider;

import java.util.LinkedHashMap;

public class SQLiteProvider extends SQLiteDatabase implements Provider {

    public SQLiteProvider() {
        super("economy");
        executeScheme("CREATE TABLE IF NOT EXISTS players (playerName VARCHAR(20) NOT NULL, balance INTEGER NOT NULL);");
    }

    @Override
    public void create(String playerName, int defaultMoney) {
        if (!has(playerName)) {
            String sql = "INSERT INTO players (playerName, balance) VALUES (:playerName, :balance);";

            try (Connection connection = getConnection()) {
                connection.createQuery(sql)
                        .addParameter("playerName", playerName)
                        .addParameter("balance", defaultMoney)
                        .executeUpdate();
            }
        }
    }

    @Override
    public boolean has(String playerName) {
        String sql = "SELECT playerName FROM players WHERE UPPER(playerName)=UPPER(:playerName);";

        try (Connection connection = getConnection()) {
            return connection.createQuery(sql)
                    .addParameter("playerName", playerName)
                    .executeScalar(String.class) != null;
        }
    }

    @Override
    public int get(String playerName) {
        String sql = "SELECT balance FROM players WHERE UPPER(playerName)=UPPER(:playerName);";

        try (Connection connection = getConnection()) {
            return connection.createQuery(sql)
                    .addParameter("playerName", playerName)
                    .executeScalar(Integer.class);
        }
    }

    @Override
    public void set(String playerName, int amount) {
        if (has(playerName)) {
            String sql = "UPDATE players SET balance=:balance WHERE UPPER(playerName)=UPPER(:playerName);";

            try (Connection connection = getConnection()) {
                connection.createQuery(sql)
                        .addParameter("balance", amount)
                        .addParameter("playerName", playerName)
                        .executeUpdate();
            }
        }
    }

    @Override
    public LinkedHashMap<String, Integer> getAll() {
        return null;
    }

    @Override
    public String getName() {
        return "SQLite";
    }
}
