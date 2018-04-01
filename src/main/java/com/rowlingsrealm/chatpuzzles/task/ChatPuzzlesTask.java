package com.rowlingsrealm.chatpuzzles.task;

import com.rowlingsrealm.chatpuzzles.ChatPuzzleManager;
import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import com.rowlingsrealm.chatpuzzles.event.PuzzleAnnounceEvent;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Copyright Tyler Grissom 2018
 */
public class ChatPuzzlesTask extends BukkitRunnable {

    @Getter private ChatPuzzlesPlugin plugin;

    public ChatPuzzlesTask(ChatPuzzlesPlugin plugin) {
        this.plugin = plugin;
    }

    private String scramble(String inputString) {
        char a[] = inputString.toCharArray();

        for (int i = 0; i < a.length; i++) {
            int j = ThreadLocalRandom.current().nextInt(a.length);

            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }

        return new String(a);
    }

    public void run() {
        ChatPuzzleManager cpm = plugin.getChatPuzzleManager();

        boolean completed = true;

        if (!cpm.isCompleted()) {
            completed = false;

            Bukkit.broadcastMessage("§8(§aPuzzle§8) §4§lNo one got the word! It was §c§l" + cpm.getCurrentWord());
        }

        String lastWord = cpm.getCurrentWord();

        String word = "";

        PuzzleAnnounceEvent event;

        do {
            String str = cpm.getPuzzles().get(ThreadLocalRandom.current().nextInt(cpm.getPuzzles().size()));

            event = new PuzzleAnnounceEvent(str, lastWord, completed);

            Bukkit.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) break;

            str = event.getWord();

            cpm.setCurrentWord(str);
            cpm.setCompleted(false);

            String[] split = cpm.getCurrentWord().toLowerCase().split(" ");

            if (split.length <= 1)  word = scramble(cpm.getCurrentWord().toLowerCase());
            else {
                StringBuilder b = new StringBuilder();

                for (String s :
                        split) {
                    b.append(scramble(s.toLowerCase())).append(" ");
                }

                word = b.toString().trim();
            }
        } while (word.trim().equalsIgnoreCase(cpm.getCurrentWord().trim()));

        if (event.isCancelled()) return;

        String format = cpm.format(word);

        Bukkit.getOnlinePlayers()
                .stream()
                .filter((Predicate<Player>) player -> player.hasPermission("puzzles.view"))
                .forEach((Consumer<Player>) player -> player.sendMessage(format));
    }
}
