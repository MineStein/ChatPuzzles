package com.rowlingsrealm.chatpuzzles.task;

import com.rowlingsrealm.chatpuzzles.ChatPuzzleManager;
import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright Tyler Grissom 2018
 */
public class ChatPuzzlesTask extends BukkitRunnable {

    @Getter private ChatPuzzlesPlugin plugin;

    public ChatPuzzlesTask(ChatPuzzlesPlugin plugin) {
        this.plugin = plugin;
    }

    public void pickWord() {
        ChatPuzzleManager cpm = plugin.getChatPuzzleManager();

        String str = cpm.getPuzzles().get(ThreadLocalRandom.current().nextInt(cpm.getPuzzles().size()));

        cpm.setCurrentWord(str);
        cpm.setCompleted(false);
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
        
        if (!cpm.isCompleted()) {
            Bukkit.broadcastMessage("§8(§aPuzzle§8) §4§lNo one got the word! It was §c§l" + cpm.getCurrentWord());
        }

        String word;

        do {
            pickWord();

            word = scramble(cpm.getCurrentWord().toLowerCase().replace(" ", ""));
        } while (word.trim().equalsIgnoreCase(cpm.getCurrentWord().trim()));

        String format = cpm.format(word);

        Bukkit.broadcastMessage(format);
    }
}
