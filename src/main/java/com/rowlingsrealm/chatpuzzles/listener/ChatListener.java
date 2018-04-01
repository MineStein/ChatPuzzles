package com.rowlingsrealm.chatpuzzles.listener;

import com.rowlingsrealm.chatpuzzles.ChatPuzzleManager;
import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import com.rowlingsrealm.chatpuzzles.config.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
public class ChatListener implements Listener {

    @Getter private ChatPuzzlesPlugin plugin;

    public ChatListener(ChatPuzzlesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(final PlayerChatEvent event) {
        ChatPuzzleManager cpm = plugin.getChatPuzzleManager();

        if (cpm.getCurrentWord() == null) return;
        if (cpm.isCompleted()) return;

        if (event.getMessage().equalsIgnoreCase(cpm.getCurrentWord())) {
            event.setFormat("");
            event.setCancelled(true);

            Bukkit.broadcastMessage("§8(§aPuzzle§8) §e" + event.getPlayer().getDisplayName() + " §7solved the word! The word was §e§l" + cpm.getCurrentWord());

            PlayerData data = new PlayerData(event.getPlayer());

            data.incrementCorrectGuesses();

            List<String> rewards = getPlugin().getConfigurationManager().getRewards();

            rewards.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%target%", event.getPlayer().getName())));

            cpm.setCurrentWord(null);
            cpm.setCompleted(true);
        }
    }
}
