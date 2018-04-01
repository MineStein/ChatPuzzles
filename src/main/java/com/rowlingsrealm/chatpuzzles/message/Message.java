package com.rowlingsrealm.chatpuzzles.message;

import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Copyright Tyler Grissom 2018
 */
public enum Message {

    FORMAT,
    NO_PERMISSION,
    ONLY_PLAYERS,
    RELOADED_CONFIG;

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
