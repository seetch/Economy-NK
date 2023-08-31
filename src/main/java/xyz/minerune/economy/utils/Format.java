package xyz.minerune.economy.utils;

import lombok.Setter;

import java.util.List;

public enum Format {

    BLACK("§0"),
    DARK_BLUE("§1"),
    DARK_GREEN("§2"),
    DARK_AQUA("§3"),
    DARK_RED("§4"),
    DARK_PURPLE("§5"),
    GOLD("§6"),
    GRAY("§7"),
    DARK_GRAY("§8"),
    BLUE("§9"),
    GREEN("§a"),
    AQUA("§b"),
    RED("§c"),
    LIGHT_PURPLE("§d"),
    YELLOW("§e"),
    WHITE("§f"),
    MINECOIN_GOLD("§g"),
    MATERIAL_QUARTZ("§h"),
    MATERIAL_IRON("§i"),
    MATERIAL_NETHERITE("§j"),
    MATERIAL_REDSTONE("§m"),
    MATERIAL_COPPER("§n"),
    MATERIAL_GOLD("§p"),
    MATERIAL_EMERALD("§q"),
    MATERIAL_DIAMOND("§s"),
    MATERIAL_LAPIS("§t"),
    MATERIAL_AMETHYST("§u");

    @Setter
    private static String character = "►";
    @Setter
    private static String defaultColorMessage = "§f";
    @Setter
    private static String defaultColorForm = "§7";

    private final String color;

    Format(String color) {
        this.color = color;
    }

    private String getColor() {
        return color;
    }

    public String colorize(String message, String... variables) {
        int i = 0;
        for (String variable : variables) {
            message = message.replace("%" + i, color + variable + defaultColorMessage);
            i++;
        }
        return color + character + " " + defaultColorMessage + message;
    }

    public String lines(List<String> lines, String... variables) {
        StringBuilder result = new StringBuilder("");
        for (String line : lines) {
            int i = 0;
            for (String variable : variables) {
                line = line.replace("%" + i, color + variable + defaultColorForm);
                i++;
            }
            result.append(defaultColorForm).append(line).append("\n");
        }
        return String.valueOf(result.append("\n"));
    }

    public String clean(String message, String... variables) {
        int i = 0;
        for (String variable : variables) {
            message = message.replace("%" + i, color + variable + defaultColorMessage);
            i++;
        }
        return defaultColorMessage + message;
    }

    public String reverseColorize(String message, String... variables) {
        int i = 0;
        for (String variable : variables) {
            message = message.replace("%" + i, defaultColorMessage + variable + color);
            i++;
        }
        return color + character + " " + color + message;
    }

    public String reverseLines(List<String> lines, String... variables) {
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            int i = 0;
            for (String variable : variables) {
                line = line.replace("%" + i, defaultColorForm + variable + color);
                i++;
            }
            result.append(color).append(line).append("\n");
        }
        return String.valueOf(result.append("\n"));
    }

    public String reverseClean(String message, String... variables) {
        int i = 0;
        for (String variable : variables) {
            message = message.replace("%" + i, defaultColorMessage + variable + color);
            i++;
        }
        return color + message;
    }
}
