package me.tylergrissom.chatpuzzles.extensionexample;

import me.tylergrissom.chatpuzzles.event.PuzzleAnnounceEvent;
import me.tylergrissom.chatpuzzles.event.PuzzleCompletionEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Tyler Grissom 2018
 */
public class ExamplePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onAnnounce(final PuzzleAnnounceEvent event) {
        Bukkit.getLogger().info("Puzzle announce event fired!");

        String word = event.getWord();
        String lastWord = event.getLastWord();
        boolean lastCompleted = event.isLastWordCompleted();

        Bukkit.getLogger().info("Word: " + word);
        Bukkit.getLogger().info("Last word: " + lastWord);
        Bukkit.getLogger().info("Last completed: " + lastCompleted);

        if (word.contains(" ")) {
            event.setWord("new");

            Bukkit.getLogger().info("Word changed!");
        }
    }

    @EventHandler
    public void onComplete(PuzzleCompletionEvent event) {
        Bukkit.getLogger().info("Puzzle completion event fired!");

        Bukkit.getLogger().info("Word: " + event.getWord());
        Bukkit.getLogger().info("Winner: " + event.getWinner().getName());

//        if (event.getWord().equals("new")) {
//            event.setCancelled(true);
//
//            Bukkit.getLogger().info("Event cancelled!");
//        }
    }
}
