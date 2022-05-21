package xyz.minerune.economy.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import me.hteppl.tools.format.Message;
import xyz.minerune.economy.Economy;

public class BalanceCommand extends Command {

    public BalanceCommand() {
        super("balance", "Ваш текущий баланс.");
        this.setPermission("economy.command.balance");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", true, CommandParamType.TARGET)});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length >= 2) {
            commandSender.sendMessage(Message.usage("/balance [игрок]"));
            return true;
        }

        Economy api = Economy.getInstance();

        if (strings.length == 1) {
            String player = strings[0];
            Player p = Server.getInstance().getPlayerExact(player);
            if (p != null) {
                player = p.getName();
            }

            if (!api.hasAccount(player)) {
                commandSender.sendMessage(Message.red("Игрок никогда не играл на сервере."));
                return true;
            }

            int balance = api.get(strings[0]);

            commandSender.sendMessage(Message.green("У игрока %0 на балансе %1$", strings[0], api.formatMoney(balance)));
            return true;
        }

        if (commandSender instanceof Player) {
            commandSender.sendMessage(Message.green("Ваш баланс: %0$", api.formatBalance((Player) commandSender)));
        } else {
            commandSender.sendMessage(Message.usage("/balance [игрок]"));
        }

        return true;
    }
}
