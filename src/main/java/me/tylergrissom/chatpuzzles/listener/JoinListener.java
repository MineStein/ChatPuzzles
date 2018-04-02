package me.tylergrissom.chatpuzzles.listener;

import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import me.tylergrissom.chatpuzzles.config.PlayerData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class JoinListener implements Listener {

    @Getter
    private ChatPuzzlesPlugin plugin;

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        PlayerData data = new PlayerData(event.getPlayer());

        data.update();
    }
}
