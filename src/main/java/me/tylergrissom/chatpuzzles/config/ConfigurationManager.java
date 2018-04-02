package me.tylergrissom.chatpuzzles.config;

import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class ConfigurationManager {

    @Getter
    private ChatPuzzlesPlugin plugin;

    public List<String> getRewards() {
        FileConfiguration c = getPlugin().getConfig();

        return c.getStringList("rewards");
    }

    public List<String> getWords() {
        FileConfiguration c = getPlugin().getConfig();

        return c.getStringList("words");
    }

    public long getInterval() {
        FileConfiguration c = getPlugin().getConfig();

        return c.getLong("interval");
    }
}

