package xyz.minerune.economy;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.plugin.PluginBase;

import java.util.Arrays;

public class Economy extends PluginBase {

    @Override
    public void onEnable() {
        //todo: register provider
        registerCommands();
    }

    private void registerCommands() {
        Command[] commands = new Command[]{
            //todo: add commands
        };

        Server.getInstance().getCommandMap().registerAll("economy", Arrays.asList(commands));
    }
}
