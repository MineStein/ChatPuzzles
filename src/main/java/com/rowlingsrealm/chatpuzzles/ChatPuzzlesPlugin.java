package com.rowlingsrealm.chatpuzzles;

import com.rowlingsrealm.chatpuzzles.listener.ChatListener;
import com.rowlingsrealm.chatpuzzles.task.ChatPuzzlesTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Tyler Grissom 2018
 *
 * TODO:
 *   - Convert to Puzzle object in config
 *   - Add hints on hover
 *   - Convert messages to configuration based
 *   - Make sure words never repeat with last message stuff
 *   - Implement in-game adding and removal of puzzles
 *   - Implement a rewards system (maybe certain words have more weight)
 */
public class ChatPuzzlesPlugin extends JavaPlugin {

    @Getter private ChatPuzzlesPlugin plugin;
    @Getter private ChatPuzzleManager chatPuzzleManager;

    @Override
    public void onEnable() {
        plugin = this;
        chatPuzzleManager = new ChatPuzzleManager(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ChatPuzzlesTask(this), 0, chatPuzzleManager.getInterval());
    }
}
