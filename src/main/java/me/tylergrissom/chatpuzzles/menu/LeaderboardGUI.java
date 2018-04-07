package me.tylergrissom.chatpuzzles.menu;

import javafx.util.Pair;
import lombok.Getter;
import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import me.tylergrissom.chatpuzzles.item.ItemBuilder;
import me.tylergrissom.chatpuzzles.message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

/**
 * Copyright Tyler Grissom 2018
 */
public class LeaderboardGUI extends MenuGUI {

    @Getter
    private ChatPuzzlesPlugin plugin;

    @Getter
    private ItemStack placeholder;

    public LeaderboardGUI(ChatPuzzlesPlugin plugin) {
        super("Leaderboard", 54, plugin);

        this.plugin = plugin;

        List<Pair<UUID, Integer>> guesses = getPlugin().getConfigurationManager().getCorrectGuesses();

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
