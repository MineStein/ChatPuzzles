package me.tylergrissom.chatpuzzles.message;

import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Copyright Tyler Grissom 2018
 */
public enum Message {

    FORMAT,
    NO_PERMISSION,
    ONLY_PLAYERS,
    RELOADED_CONFIG,
    IN_PROGRESS,
    CHAT_TO_CREATE,
    WORD_CREATED,
    WORD_REMOVED,
    WORD_CANCELLED;

    public String get() {
        FileConfiguration config = ChatPuzzlesPlugin.getInstance().getConfig();

        return ChatColor.translateAlternateColorCodes('&', config.getString("message." + this.toString().toLowerCase()));
    }

    public String get(String replace, String with) {
        String str = get();

        if (str == null) return null;

        return str.replace("$" + replace, with);
    }
}
