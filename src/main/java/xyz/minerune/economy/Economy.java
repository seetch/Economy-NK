package xyz.minerune.economy;

import cn.nukkit.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Economy {

    public static boolean hasAccount(String playerName) {
        return EconomyPlugin.provider.hasAccount(playerName);
    }

    public static boolean hasMoney(Player player, int amount) {
        return getMoney(player) >= amount;
    }

    public static boolean hasMoney(String playerName, int amount) {
        return getMoney(playerName) >= amount;
    }

    public static int getMoney(Player player) {
        return EconomyPlugin.provider.getMoney(player.getName());
    }

    public static int getMoney(String playerName) {
        return EconomyPlugin.provider.getMoney(playerName);
    }

    public static void setMoney(Player player, int amount) {
        EconomyPlugin.provider.setMoney(player.getName(), amount);
    }

    public static void setMoney(String playerName, int amount) {
        EconomyPlugin.provider.setMoney(playerName, amount);
    }

    public static void deductMoney(Player player, int amount) {
        setMoney(player, getMoney(player) - amount);
    }

    public static void deductMoney(String playerName, int amount) {
        setMoney(playerName, getMoney(playerName) - amount);
    }

    public static void addMoney(Player player, int amount) {
        setMoney(player, getMoney(player) + amount);
    }

    public static void addMoney(String playerName, int amount) {
        setMoney(playerName, getMoney(playerName) + amount);
    }

    public static String formatBalance(Player player) {
        return formatMoney(getMoney(player));
    }

    public static String formatBalance(String playerName) {
        return formatMoney(getMoney(playerName));
    }

    public static String legacyFormatMoney(int number) {
        return "$" + new DecimalFormat(",###", new DecimalFormatSymbols(Locale.US)).format(number);
    }

    public static String formatMoney(int number) {
        return "$" + NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT).format(number);
    }
}
