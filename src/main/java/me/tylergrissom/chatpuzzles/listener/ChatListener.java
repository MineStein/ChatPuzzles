package me.tylergrissom.chatpuzzles.listener;

import me.tylergrissom.chatpuzzles.ChatPuzzleManager;
import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import me.tylergrissom.chatpuzzles.config.PlayerData;
import me.tylergrissom.chatpuzzles.event.PuzzleCompletionEvent;
import me.tylergrissom.chatpuzzles.message.Message;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Copyright Tyler Grissom 2018
 */
public class ChatListener implements Listener {

    @Getter
    private ChatPuzzlesPlugin plugin;

    @Getter
    private static List<UUID> creatingWords;

    public ChatListener(ChatPuzzlesPlugin plugin) {
        this.plugin = plugin;
    }

    static {
        creatingWords = new ArrayList<>();
    }

    @EventHandler
    public void onChat(final PlayerChatEvent event) {
        ChatPuzzleManager cpm = plugin.getChatPuzzleManager();
        Player p = event.getPlayer();
        String msg = event.getMessage();

        if (creatingWords.contains(p.getUniqueId())) {
            if (msg.equalsIgnoreCase("!cancel")) {
                getCreatingWords().remove(p.getUniqueId());

                p.sendMessage(Message.WORD_CANCELLED.get());

                event.setCancelled(true);
            } else {
                cpm.addPuzzle(msg.toLowerCase());

                p.sendMessage(Message.WORD_CREATED.get());

                getCreatingWords().remove(p.getUniqueId());

                event.setCancelled(true);
            }
        }

        if (cpm.getCurrentWord() == null) return;
        if (cpm.isCompleted()) return;

        if (event.getMessage().equalsIgnoreCase(cpm.getCurrentWord())) {
            if (!p.hasPermission("puzzles.answer")) return;

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
