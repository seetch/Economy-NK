package xyz.minerune.economy.command.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import xyz.minerune.economy.Economy;
import me.seetch.format.Format;

public class GiveMoneyCommand extends Command {

    public GiveMoneyCommand() {
        super("givemoney", "§r§cВыдает деньги указанному игроку.");
        setPermission("economy.command.givemoney");

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
            commandSender.sendMessage(Format.YELLOW.colorize("\uE113", "Используйте: %0","/givemoney <игрок> <сумма>"));
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

            Economy.addMoney(player, amount);

            commandSender.sendMessage(Format.GREEN.colorize("\uE111", "Вы выдали %0 игроку %1.", Economy.formatMoney(amount), player));
            if (p != null) {
                p.sendMessage(Format.YELLOW.colorize("\uE113", "Игрок %0 выдал Вам %1", commandSender.getName(), Economy.formatMoney(amount)));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Format.RED.colorize("\uE112", "Сумма должна быть числом."));
        }
        return true;
    }
}
