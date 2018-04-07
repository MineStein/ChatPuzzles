package me.tylergrissom.chatpuzzles.task;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.Getter;
import me.tylergrissom.chatpuzzles.ChatPuzzleManager;
import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import me.tylergrissom.chatpuzzles.event.PuzzleAnnounceEvent;
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

        if (cpm.getPuzzles().isEmpty()) return;

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
                .filter(player -> player.hasPermission("puzzles.view"))
                .forEach(player -> player.sendMessage(format));
    }
}
