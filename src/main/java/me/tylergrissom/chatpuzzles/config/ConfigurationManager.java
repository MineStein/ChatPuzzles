package me.tylergrissom.chatpuzzles.config;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<Pair<UUID, Integer>> getCorrectGuesses() {
        ConfigurationSection sec = getPlugin().getConfig().getConfigurationSection("stats");
        List<Pair<UUID, Integer>> list = new ArrayList<>();

        for (String key : sec.getKeys(false)) {
            list.add(new Pair<>(Bukkit.getOfflinePlayer(key).getUniqueId(), sec.getConfigurationSection(key).getInt("correct_guesses")));
        }

        return list;
    }
}

