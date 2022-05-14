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

import java.util.Arrays;

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
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

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

    public void create(Player player, Double amount) {
        getProvider().create(player.getName(), amount);
    }

    public boolean hasAccount(String playerName) {
        return getProvider().has(playerName);
    }

    public boolean has(Player player, Double amount) {
        return get(player) >= amount;
    }

    public boolean has(String playerName, Double amount) {
        return get(playerName) >= amount;
    }

    public Double get(Player player) {
        return getProvider().get(player.getName());
    }

    public Double get(String playerName) {
        return getProvider().get(playerName);
    }

    public void set(Player player, Double amount) {
        getProvider().set(player.getName(), amount);
    }

    public void set(String playerName, Double amount) {
        getProvider().set(playerName, amount);
    }

    public void deduct(Player player, Double amount) {
        set(player, get(player) - amount);
    }

    public void deduct(String playerName, Double amount) {
        set(playerName, get(playerName) - amount);
    }

    public void add(Player player, Double amount) {
        set(player, get(player) + amount);
    }

    public void add(String playerName, Double amount) {
        set(playerName, get(playerName) + amount);
    }

    // TODO: Add format money func (1,000.52)
}
