package xyz.minerune.economy;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import me.seetch.format.Format;
import xyz.minerune.economy.command.BalanceCommand;
import xyz.minerune.economy.command.PayCommand;
import xyz.minerune.economy.command.admin.GiveMoneyCommand;
import xyz.minerune.economy.command.admin.SetMoneyCommand;
import xyz.minerune.economy.command.admin.TakeMoneyCommand;
import xyz.minerune.economy.provider.Provider;
import xyz.minerune.economy.provider.sqlite.SQLiteProvider;

import java.util.Arrays;

public class EconomyPlugin extends PluginBase implements Listener {

    @Getter
    private static EconomyPlugin instance;
    protected static Provider provider;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        provider = new SQLiteProvider();
        registerCommands();
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getLogger().debug(Format.AQUA.message("Database provider set to %0.", provider.getName()));
    }

    private void registerCommands() {
        Command[] commands = new Command[]{new GiveMoneyCommand(), new SetMoneyCommand(), new TakeMoneyCommand(), new BalanceCommand(), new PayCommand()};

        Server.getInstance().getCommandMap().registerAll("economy", Arrays.asList(commands));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        provider.createAccount(event.getPlayer().getName(), 0);
    }
}
