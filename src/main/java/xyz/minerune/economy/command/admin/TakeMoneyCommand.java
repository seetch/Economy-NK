package xyz.minerune.economy.command.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import xyz.minerune.api.utils.Message;
import xyz.minerune.economy.Economy;

public class TakeMoneyCommand extends Command {

    public TakeMoneyCommand() {
        super("takemoney", "Забрать деньги у указанного игрока.");
        setPermission("economy.command.takemoney");

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
            commandSender.sendMessage(Message.usage("/takemoney <игрок> <сумма>"));
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

            int balance = api.get(player);

            if (amount > balance) {
                commandSender.sendMessage(Message.red("У игрока %0 недостаточно денег. Баланс игрока %0: %1$", player, api.formatBalance(player)));
                return true;
            }

            api.deduct(player, amount);

            commandSender.sendMessage(Message.green("Вы забрали %0$ игроку %1.", api.formatMoney(amount), player));
            if (p != null) {
                p.sendMessage(Message.yellow("Игрок %0 забрал у Вас %1$", commandSender.getName(), api.formatMoney(amount)));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Message.red("Сумма должна быть числом."));
        }
        return true;
    }
}
