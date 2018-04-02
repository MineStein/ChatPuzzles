package com.rowlingsrealm.chatpuzzles.listener;

import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class QuitListener implements Listener {

    @Getter
    private ChatPuzzlesPlugin plugin;

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        Player p = event.getPlayer();

        ChatListener.getCreatingWords().remove(p.getUniqueId());
    }
}
