package me.tylergrissom.chatpuzzles.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
public abstract class CommandBase implements CommandExecutor, TabCompleter {

    abstract List<String> tab(CommandSender sender, Command command, String[] args);

    abstract void execute(CommandSender sender, Command command, String[] args);

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return tab(commandSender, command, strings);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        execute(commandSender, command, strings);

        return true;
    }
}
