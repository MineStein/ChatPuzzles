package me.tylergrissom.chatpuzzles.config;

import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class PlayerData {

    @Getter
    private Player player;

    private ChatPuzzlesPlugin getPlugin() {
        return ChatPuzzlesPlugin.getInstance();
    }

    public ConfigurationSection getSection() {
        FileConfiguration c = getPlugin().getConfig();

        if (c.getConfigurationSection("stats." + player.getUniqueId().toString()) == null) {
            ConfigurationSection section = c.createSection("stats." + player.getUniqueId().toString());

            section.set("name", player.getName());
            section.set("correct_guesses", 0);

            getPlugin().saveConfig();
        }

        return c.getConfigurationSection("stats." + player.getUniqueId().toString());
    }

    public void update() {
        ConfigurationSection section = getSection();

        if (!section.getString("name").equals(player.getName())) {
            section.set("name", player.getName());

            getPlugin().saveConfig();
        }
    }

    public int getCorrectGuesses() {
        ConfigurationSection section = getSection();

        return section.getInt("correct_guesses", 0);
    }

    public void incrementCorrectGuesses() {
        ConfigurationSection section = getSection();

        section.set("correct_guesses", getCorrectGuesses() + 1);

        getPlugin().saveConfig();
    }
}
