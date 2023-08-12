package xyz.minerune.economy.provider.sqlite;

import lombok.AccessLevel;
import lombok.Getter;
import me.hteppl.data.database.SQLiteDatabase;
import org.sql2o.Connection;
import xyz.minerune.economy.provider.Provider;

import java.util.LinkedHashMap;

public class SQLiteProvider extends SQLiteDatabase implements Provider {

    @Getter(AccessLevel.PRIVATE)
    private final Connection connection;

    public SQLiteProvider() {
        super("economy");
        this.executeScheme("CREATE TABLE IF NOT EXISTS players (username VARCHAR(16) NOT NULL, balance INTEGER NOT NULL);");

        this.connection = openConnection();
    }

    @Override
    public void createAccount(String username, int defaultMoney) {
        if (!hasAccount(username)) {
            String sql = "INSERT INTO players (username, balance) VALUES (:username, :balance);";

            try (Connection connection = getConnection()) {
                connection.createQuery(sql).addParameter("username", username).addParameter("balance", defaultMoney).executeUpdate();
            }
        }
    }

    @Override
    public boolean hasAccount(String username) {
        String sql = "SELECT username FROM players WHERE UPPER(username)=UPPER(:username);";

        try (Connection connection = getConnection()) {
            return connection.createQuery(sql).addParameter("username", username).executeScalar(String.class) != null;
        }
    }

    @Override
    public int getMoney(String username) {
        String sql = "SELECT balance FROM players WHERE UPPER(username)=UPPER(:username);";

        try (Connection connection = getConnection()) {
            return connection.createQuery(sql).addParameter("username", username).executeScalar(Integer.class);
        }
    }

    @Override
    public void setMoney(String username, int amount) {
        if (hasAccount(username)) {
            String sql = "UPDATE players SET balance=:balance WHERE UPPER(username)=UPPER(:username);";

            try (Connection connection = getConnection()) {
                connection.createQuery(sql).addParameter("balance", amount).addParameter("username", username).executeUpdate();
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
