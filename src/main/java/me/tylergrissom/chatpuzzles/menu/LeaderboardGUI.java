package me.tylergrissom.chatpuzzles.menu;

import lombok.Getter;
import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import me.tylergrissom.chatpuzzles.item.ItemBuilder;
import me.tylergrissom.chatpuzzles.message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Tyler Grissom 2018
 */
public class LeaderboardGUI extends MenuGUI {

    @Getter
    private ItemStack placeholder;

    public LeaderboardGUI(ChatPuzzlesPlugin plugin) {
        super("Leaderboard", 54, plugin);

        placeholder = new ItemBuilder()
                .type(Material.STAINED_GLASS_PANE)
                .data((byte) 14)
                .name("§c§lComing soon!")
                .build();

        for (int i = 0; i < getSize(); i++) {
            addOption(placeholder, i);
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Player p = ((Player) e.getWhoClicked());

        p.closeInventory();
        p.sendMessage(Message.IN_PROGRESS.get());
    }
}
