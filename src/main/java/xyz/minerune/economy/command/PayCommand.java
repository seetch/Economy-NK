package xyz.minerune.economy.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import xyz.minerune.api.utils.Message;
import xyz.minerune.economy.Economy;

public class PayCommand extends Command {

    public PayCommand() {
        super("pay", "Перевести указанную сумму денег игроку.");
        setPermission("economy.command.pay");

        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{CommandParameter.newType("player", false, CommandParamType.TARGET), CommandParameter.newType("amount", false, CommandParamType.FLOAT)});
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Message.ingame);
            return true;
        }

        if (strings.length > 3 || strings.length < 2) {
            commandSender.sendMessage(Message.usage("/pay <игрок> <сумма>"));
            return true;
        }

        Economy api = Economy.getInstance();

        String player = strings[0];
        Player p = Server.getInstance().getPlayerExact(player);
        if (p != null) {
            player = p.getName();
        }

        try {
            Double amount = Double.parseDouble(strings[1]);

            if (amount < 0) {
                commandSender.sendMessage(Message.red("Некорректное число."));
                return true;
            }

            if (api.get(commandSender.getName()) < amount) {
                commandSender.sendMessage(Message.red("Недостаточно денег, чтобы передать %0$ игроку %1.", amount.toString(), player));
                return true;
            }

            if (commandSender.getName().equals(player)) {
                commandSender.sendMessage(Message.red("Вы не можете передать деньги самому себе."));
                return true;
            }

            api.add(player, amount);
            api.deduct((Player) commandSender, amount);

            commandSender.sendMessage(Message.green("Вы перевели %0$ игроку %1.", amount.toString(), player));
            if (p != null) {
                p.sendMessage(Message.gold("Игрок %0 перевел Вам %1$.", commandSender.getName(), amount.toString()));
            }
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Message.red("Сумма должна быть числом."));
        }
        return true;
    }
}
