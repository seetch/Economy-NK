package xyz.minerune.economy.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import xyz.minerune.economy.Economy;
import me.seetch.format.Format;

public class BalanceCommand extends Command {

    public BalanceCommand() {
        super("balance", "§r§uПоказывает ваш текущий баланс.");
        this.setPermission("economy.command.balance");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", true, CommandParamType.TARGET)});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length >= 2) {
            commandSender.sendMessage(Format.MATERIAL_GOLD.colorize("Используйте: %0","/balance [игрок]"));
            return true;
        }

        if (strings.length == 1) {
            String player = strings[0];
            Player p = Server.getInstance().getPlayerExact(player);
            if (p != null) {
                player = p.getName();
            }

            if (!Economy.hasAccount(player)) {
                commandSender.sendMessage(Format.MATERIAL_REDSTONE.colorize("Игрок никогда не играл на сервере."));
                return true;
            }

            int balance = Economy.getMoney(strings[0]);

            commandSender.sendMessage(Format.MATERIAL_EMERALD.colorize("У игрока %0 на балансе %1$", strings[0], Economy.formatMoney(balance)));
            return true;
        }

        if (commandSender instanceof Player) {
            commandSender.sendMessage(Format.MATERIAL_AMETHYST.colorize("Ваш баланс: %0$", Economy.formatBalance((Player) commandSender)));
        } else {
            commandSender.sendMessage(Format.MATERIAL_GOLD.colorize("Используйте: %0","/balance [игрок]"));
        }

        return true;
    }
}
