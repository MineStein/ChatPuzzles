package com.rowlingsrealm.chatpuzzles.listener;

import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import com.rowlingsrealm.chatpuzzles.menu.MenuGUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class MenuListener implements Listener {

    @Getter
    private ChatPuzzlesPlugin plugin;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        MenuGUI.checkForMenuClick(getPlugin(), event, false);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        MenuGUI.checkForMenuClose(getPlugin(), event);
    }
}
