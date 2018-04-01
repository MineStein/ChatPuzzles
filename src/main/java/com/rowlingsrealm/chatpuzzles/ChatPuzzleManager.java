package com.rowlingsrealm.chatpuzzles;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
public class ChatPuzzleManager {

    @Getter
    private ChatPuzzlesPlugin plugin;
    @Getter @Setter
    private String currentWord;
    @Getter @Setter
    private boolean completed;

    public ChatPuzzleManager(ChatPuzzlesPlugin plugin) {
        this.plugin = plugin;
    }

    public String getFormat() {
        return plugin.getConfig().getString("format", "&8(&aPuzzle&8) &b&lUnscramble the puzzle! &7The word is &e$word");
    }

    public String format(String word) {
        return ChatColor.translateAlternateColorCodes('&', getFormat()).replace("$word", word);
    }

    public String format() {
        return format(getCurrentWord());
    }

    public long getInterval() {
        return plugin.getConfig().getLong("interval", 400);
    }

    public List<String> getPuzzles() {
        FileConfiguration config = plugin.getConfig();

        List<String> words;

        if (config.get("words") == null) words = Arrays.asList("wand", "harry potter", "crystal ball", "magic", "spell", "broom");
        else words = config.getStringList("words");

        return words;
    }
}
