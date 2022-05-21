package xyz.minerune.economy;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.plugin.PluginBase;
import xyz.minerune.economy.command.BalanceCommand;
import xyz.minerune.economy.command.PayCommand;
import xyz.minerune.economy.command.admin.GiveMoneyCommand;
import xyz.minerune.economy.command.admin.SetMoneyCommand;
import xyz.minerune.economy.command.admin.TakeMoneyCommand;
import xyz.minerune.economy.provider.Provider;
import xyz.minerune.economy.provider.sqlite.SQLiteProvider;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class Economy extends PluginBase {

    private static Economy instance;

    private Provider provider;

    @Override
    public void onLoad() {
        instance = this;
    }

    public static Economy getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        this.provider = new SQLiteProvider();
        registerCommands();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        this.getLogger().debug("Database provider set to " + provider.getName());
    }

    private void registerCommands() {
        Command[] commands = new Command[]{
                new GiveMoneyCommand(),
                new SetMoneyCommand(),
                new TakeMoneyCommand(),

                new BalanceCommand(),
                new PayCommand(),
        };

        Server.getInstance().getCommandMap().registerAll("economy", Arrays.asList(commands));
    }

    public Provider getProvider() {
        return provider;
    }

    public void create(Player player) {
        getProvider().create(player.getName(), 0);
    }

    public boolean hasAccount(String playerName) {
        return getProvider().has(playerName);
    }

    public boolean has(Player player, int amount) {
        return get(player) >= amount;
    }

    public boolean has(String playerName, int amount) {
        return get(playerName) >= amount;
    }

    public int get(Player player) {
        return getProvider().get(player.getName());
    }

    public int get(String playerName) {
        return getProvider().get(playerName);
    }

    public void set(Player player, int amount) {
        getProvider().set(player.getName(), amount);
    }

    public void set(String playerName, int amount) {
        getProvider().set(playerName, amount);
    }

    public void deduct(Player player, int amount) {
        set(player, get(player) - amount);
    }

    public void deduct(String playerName, int amount) {
        set(playerName, get(playerName) - amount);
    }

    public void add(Player player, int amount) {
        set(player, get(player) + amount);
    }

    public void add(String playerName, int amount) {
        set(playerName, get(playerName) + amount);
    }

    public String formatBalance(Player player) {
        return format(get(player));
    }

    public String formatBalance(String playerName) {
        return format(get(playerName));
    }

    public String formatMoney(int amount) {
        return format(amount);
    }

    private String format(int number) {
        return new DecimalFormat(",###", new DecimalFormatSymbols(Locale.US)).format(number);
    }
}
