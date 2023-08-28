package xyz.minerune.economy.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import xyz.minerune.economy.Economy;
import me.seetch.format.Format;

public class PayCommand extends Command {

    public PayCommand() {
        super("pay", "Переводит указанную сумму денег игроку.");
        setPermission("economy.command.pay");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", false, CommandParamType.TARGET), CommandParameter.newType("amount", false, CommandParamType.INT)});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Format.RED.message("Эта команда должна быть выполнена в игре."));
            return true;
        }

        if (strings.length > 3 || strings.length < 2) {
            commandSender.sendMessage(Format.YELLOW.message("Используйте: %0","/pay <игрок> <сумма>"));
            return true;
        }

        String player = strings[0];
        Player p = Server.getInstance().getPlayerExact(player);
        if (p != null) {
            player = p.getName();
        }

        try {
            int amount = Integer.parseInt(strings[1]);

            if (amount < 0) {
                commandSender.sendMessage(Format.RED.message("Некорректное число."));
                return true;
            }

            if (Economy.getMoney(commandSender.getName()) < amount) {
                commandSender.sendMessage(Format.RED.message("Недостаточно денег, чтобы передать %0$ игроку %1.", Economy.formatMoney(amount), player));
                return true;
            }

            if (commandSender.getName().equals(player)) {
                commandSender.sendMessage(Format.RED.message("Вы не можете передать деньги самому себе."));
                return true;
            }

            Economy.addMoney(player, amount);
            Economy.deductMoney((Player) commandSender, amount);

            commandSender.sendMessage(Format.GREEN.message("Вы перевели %0$ игроку %1.", Economy.formatMoney(amount), player));
            if (p != null) {
                p.sendMessage(Format.GOLD.message("Игрок %0 перевел Вам %1$.", commandSender.getName(), Economy.formatMoney(amount)));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Format.RED.message("Сумма должна быть числом."));
        }
        return true;
    }
}
