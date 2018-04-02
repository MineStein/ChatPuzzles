package me.tylergrissom.chatpuzzles.command;

import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import me.tylergrissom.chatpuzzles.menu.ManagePuzzlesGUI;
import me.tylergrissom.chatpuzzles.menu.MenuGUI;
import me.tylergrissom.chatpuzzles.menu.StatsGUI;
import me.tylergrissom.chatpuzzles.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class PuzzlesCommand extends CommandBase {

    @Getter
    private ChatPuzzlesPlugin plugin;

    List<String> tab(CommandSender sender, Command command, String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length == 0) {
            list.add("reload");
            list.add("manage");
            list.add("stats");
        }

        return list;
    }

    void execute(CommandSender sender, Command command, String[] args) {
        String[] usage = (String[]) Arrays.asList(
                "§7ChatPuzzles §6Help",
                "  §8/puzzles stats - view your statistics and the puzzles leaderboard",
                "  §8/puzzles manage - manage puzzles",
                "  §8/puzzles reload - reload ChatPuzzles"
        ).toArray();

        if (args.length == 0) {
            sender.sendMessage(usage);
        } else {
            String arg = args[0];

            if (arg.equalsIgnoreCase("reload")) {
                if (sender.hasPermission("puzzles.reload")) {
                    if (sender.hasPermission("chatpuzzles.reload")) {
                        plugin.reloadConfig();

                        sender.sendMessage(Message.RELOADED_CONFIG.get());
                    } else {
                        sender.sendMessage(Message.NO_PERMISSION.get());
                    }
                } else {
                    sender.sendMessage(Message.NO_PERMISSION.get());
                }
            } else if (arg.equalsIgnoreCase("manage")) {
                if (sender.hasPermission("puzzles.manage")) {
                    if (sender instanceof Player) {
                        Player p = ((Player) sender);
                        MenuGUI gui = new ManagePuzzlesGUI(plugin);

                        gui.show(p);
                    } else {
                        sender.sendMessage(Message.ONLY_PLAYERS.get());
                    }
                } else {
                    sender.sendMessage(Message.NO_PERMISSION.get());
                }
            } else if (arg.equalsIgnoreCase("stats")) {
                if (sender.hasPermission("puzzles.stats")) {
                    if (sender instanceof Player) {
                        Player p = ((Player) sender);

                        new StatsGUI(getPlugin(), p).show(p);
                    }
                } else {
                    sender.sendMessage(Message.NO_PERMISSION.get());
                }
            }
        }
    }
}
