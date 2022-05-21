package xyz.minerune.economy.command.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import xyz.minerune.api.utils.Message;
import xyz.minerune.economy.Economy;

public class SetMoneyCommand extends Command {

    public SetMoneyCommand() {
        super("setmoney", "Установить баланс указанному игроку.");
        setPermission("economy.command.setmoney");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", false, CommandParamType.TARGET), CommandParameter.newType("amount", false, CommandParamType.FLOAT)});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Economy api = Economy.getInstance();

        if (!commandSender.hasPermission(this.getPermission())) {
            commandSender.sendMessage(Message.permission);
            return true;
        }

        if (strings.length > 3 || strings.length < 2) {
            commandSender.sendMessage(Message.usage("/setmoney <игрок> <сумма>"));
            return true;
        }

        String player = strings[0];
        Player p = Server.getInstance().getPlayerExact(player);
        if (p != null) {
            player = p.getName();
        }

        if (!api.hasAccount(player)) {
            commandSender.sendMessage(Message.red("Игрок никогда не играл на сервере."));
            return true;
        }

        try {
            int amount = Integer.parseInt(strings[1]);

            if (amount < 0) {
                commandSender.sendMessage(Message.red("Некорректное число."));
                return true;
            }

            api.set(player, amount);

            commandSender.sendMessage(Message.green("Баланс игрока %0 установлен на %1$", player, api.formatMoney(amount)));
            if (p != null) {
                p.sendMessage(Message.yellow("Игрок %0 установил Ваш баланс на %1$", commandSender.getName(), api.formatMoney(amount)));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Message.red("Сумма должна быть числом."));
        }
        return true;
    }
}
