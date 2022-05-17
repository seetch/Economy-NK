package xyz.minerune.economy.command.admin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import xyz.minerune.api.utils.Message;
import xyz.minerune.economy.Economy;

public class GiveMoneyCommand extends Command {

    public GiveMoneyCommand() {
        super("givemoney", "Выдать деньги указанному игроку.");
        setPermission("economy.command.givemoney");

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
            commandSender.sendMessage(Message.usage("/givemoney <игрок> <сумма>"));
            return true;
        }

        String player = strings[0];
        Player p = Server.getInstance().getPlayerExact(player);
        if (p != null) {
            player = p.getName();
        }

        try {
            double amount = Double.parseDouble(strings[1]);

            if (amount < 0) {
                commandSender.sendMessage(Message.red("Некорректное число."));
                return true;
            }

            api.add(player, amount);

            commandSender.sendMessage(Message.green("Вы выдали %0$ игроку %1.", Double.toString(amount), player));
            if (p != null) {
                p.sendMessage(Message.yellow("Игрок %0 выдал Вам %1$", commandSender.getName(), Double.toString(amount)));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Message.red("Сумма должна быть числом."));
        }
        return true;
    }
}
