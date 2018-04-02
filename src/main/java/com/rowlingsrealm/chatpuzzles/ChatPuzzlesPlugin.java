package com.rowlingsrealm.chatpuzzles;

import com.rowlingsrealm.chatpuzzles.command.CommandBase;
import com.rowlingsrealm.chatpuzzles.command.PuzzlesCommand;
import com.rowlingsrealm.chatpuzzles.config.ConfigurationManager;
import com.rowlingsrealm.chatpuzzles.listener.ChatListener;
import com.rowlingsrealm.chatpuzzles.listener.JoinListener;
import com.rowlingsrealm.chatpuzzles.listener.MenuListener;
import com.rowlingsrealm.chatpuzzles.listener.QuitListener;
import com.rowlingsrealm.chatpuzzles.task.ChatPuzzlesTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Tyler Grissom 2018
 */
public class ChatPuzzlesPlugin extends JavaPlugin {

    @Getter
    private ChatPuzzlesPlugin plugin;

    @Getter
    private static ChatPuzzlesPlugin instance;

    @Getter
    private ChatPuzzleManager chatPuzzleManager;

    @Getter
    private ConfigurationManager configurationManager;

    private void registerCommand(String cmd, CommandBase base) {
        getCommand(cmd).setExecutor(base);
        getCommand(cmd).setTabCompleter(base);
    }

    @Override
    public void onEnable() {
        plugin = this;
        instance = this;
        chatPuzzleManager = new ChatPuzzleManager(this);
        configurationManager = new ConfigurationManager(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        {
            registerCommand("puzzles", new PuzzlesCommand(this));
        }

        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(this), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ChatPuzzlesTask(this), 0, chatPuzzleManager.getInterval());
    }
}
