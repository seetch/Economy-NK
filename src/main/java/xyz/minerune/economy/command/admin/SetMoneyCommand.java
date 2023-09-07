package xyz.minerune.economy.command.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import me.seetch.format.Format;
import xyz.minerune.economy.Economy;

public class SetMoneyCommand extends Command {

    public SetMoneyCommand() {
        super("setmoney", "§r§cУстанавливает баланс указанному игроку.");
        setPermission("economy.command.setmoney");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", false, CommandParamType.TARGET), CommandParameter.newType("amount", false, CommandParamType.INT)});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!commandSender.hasPermission(this.getPermission())) {
            commandSender.sendMessage(Format.MATERIAL_REDSTONE.colorize("У Вас недостаточно прав для выполнения этой команды."));
            return true;
        }

        if (strings.length > 3 || strings.length < 2) {
            commandSender.sendMessage(Format.YELLOW.colorize("Используйте: %0","/setmoney <игрок> <сумма>"));
            return true;
        }

        String player = strings[0];
        Player p = Server.getInstance().getPlayerExact(player);
        if (p != null) {
            player = p.getName();
        }

        if (!Economy.hasAccount(player)) {
            commandSender.sendMessage(Format.MATERIAL_REDSTONE.colorize("Игрок никогда не играл на сервере."));
            return true;
        }

        try {
            int amount = Integer.parseInt(strings[1]);

            if (amount < 0) {
                commandSender.sendMessage(Format.MATERIAL_REDSTONE.colorize("Некорректное число."));
                return true;
            }

            Economy.setMoney(player, amount);

            commandSender.sendMessage(Format.GREEN.colorize("Баланс игрока %0 установлен на %1$", player, Economy.formatMoney(amount)));
            if (p != null) {
                p.sendMessage(Format.YELLOW.colorize("Игрок %0 установил Ваш баланс на %1$", commandSender.getName(), Economy.formatMoney(amount)));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Format.MATERIAL_REDSTONE.colorize("Сумма должна быть числом."));
        }
        return true;
    }
}
