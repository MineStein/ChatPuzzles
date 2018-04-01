package com.rowlingsrealm.chatpuzzles.listener;

import com.rowlingsrealm.chatpuzzles.ChatPuzzleManager;
import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import com.rowlingsrealm.chatpuzzles.config.PlayerData;
import com.rowlingsrealm.chatpuzzles.event.PuzzleCompletionEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        Player p = event.getPlayer();

        Bukkit.getLogger().info("Debug 1");

        if (cpm.getCurrentWord() == null) return;
        if (cpm.isCompleted()) return;

        Bukkit.getLogger().info("Debug 2");

        if (event.getMessage().equalsIgnoreCase(cpm.getCurrentWord())) {
            Bukkit.getLogger().info("Debug 3");

            PuzzleCompletionEvent e = new PuzzleCompletionEvent(cpm.getCurrentWord(), p);

            Bukkit.getPluginManager().callEvent(e);

            if (e.isCancelled()) return;

            p = e.getWinner();

            event.setFormat("");
            event.setCancelled(true);

            Bukkit.broadcastMessage("§8(§aPuzzle§8) §e" + p.getName() + " §7solved the word! The word was §e§l" + cpm.getCurrentWord());

            PlayerData data = new PlayerData(p);

            data.incrementCorrectGuesses();

            List<String> rewards = getPlugin().getConfigurationManager().getRewards();

            for (String command : rewards) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%target%", p.getName()));
            }

            cpm.setCurrentWord(null);
            cpm.setCompleted(true);
        }
    }
}
