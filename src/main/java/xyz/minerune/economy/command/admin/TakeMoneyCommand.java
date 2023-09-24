package xyz.minerune.economy.command.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import me.seetch.format.Format;
import xyz.minerune.economy.Economy;

public class TakeMoneyCommand extends Command {

    public TakeMoneyCommand() {
        super("takemoney", "§r§cЗабирает деньги у указанного игрока.");
        setPermission("economy.command.takemoney");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", false, CommandParamType.TARGET), CommandParameter.newType("amount", false, CommandParamType.INT)});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!commandSender.hasPermission(this.getPermission())) {
            commandSender.sendMessage(Format.RED.colorize("\uE112", "У Вас недостаточно прав для выполнения этой команды."));
            return true;
        }

        if (strings.length > 3 || strings.length < 2) {
            commandSender.sendMessage(Format.YELLOW.colorize("\uE113", "Используйте: %0", "/takemoney <игрок> <сумма>"));
            return true;
        }

        String player = strings[0];
        Player p = Server.getInstance().getPlayerExact(player);
        if (p != null) {
            player = p.getName();
        }

        if (!Economy.hasAccount(player)) {
            commandSender.sendMessage(Format.RED.colorize("\uE112", "Игрок никогда не играл на сервере."));
            return true;
        }

        try {
            int amount = Integer.parseInt(strings[1]);

            if (amount < 0) {
                commandSender.sendMessage(Format.RED.colorize("\uE112", "Некорректное число."));
                return true;
            }

            int balance = Economy.getMoney(player);

            if (amount > balance) {
                commandSender.sendMessage(Format.RED.colorize("\uE112", "У игрока %0 недостаточно денег. Баланс игрока %0: %1", player, Economy.formatBalance(player)));
                return true;
            }

            Economy.deductMoney(player, amount);

            commandSender.sendMessage(Format.GREEN.colorize("\uE111", "Вы забрали %0 игроку %1.", Economy.formatMoney(amount), player));
            if (p != null) {
                p.sendMessage(Format.YELLOW.colorize("\uE113", "Игрок %0 забрал у Вас %1", commandSender.getName(), Economy.formatMoney(amount)));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Format.RED.colorize("\uE112", "Сумма должна быть числом."));
        }
        return true;
    }
}
