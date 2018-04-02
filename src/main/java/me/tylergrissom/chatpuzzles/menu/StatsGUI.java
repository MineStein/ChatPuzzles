package me.tylergrissom.chatpuzzles.menu;

import lombok.Getter;
import me.tylergrissom.chatpuzzles.ChatPuzzlesPlugin;
import me.tylergrissom.chatpuzzles.config.PlayerData;
import me.tylergrissom.chatpuzzles.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Tyler Grissom 2018
 */
public class StatsGUI extends MenuGUI {

    @Getter
    private ChatPuzzlesPlugin plugin;

    @Getter
    private Player player;

    @Getter
    private ItemStack correctGuesses, leaderboard;

    public StatsGUI(ChatPuzzlesPlugin plugin, Player player) {
        super("Stats", 27, plugin);

        this.plugin = plugin;
        this.player = player;

        PlayerData pd = new PlayerData(player);

        correctGuesses = new ItemBuilder()
                .type(Material.BOOK)
                .name("§bPlayer Statistics")
                .lore(
                        "",
                        "§7Name§8: §e" + player.getName(),
                        "§7Correct guesses§8: §e" + pd.getCorrectGuesses()
                )
                .build();

        leaderboard = new ItemBuilder()
                .type(Material.MAP)
                .name("§bLeaderboard")
                .lore(
                        "",
                        "§6§lClick §7to view"
                )
                .build();

        addOption(correctGuesses, 12);
        addOption(leaderboard, 14);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Player p = ((Player) e.getWhoClicked());
        ItemStack item = e.getCurrentItem();

        e.setCancelled(true);

        if (item.getType().equals(Material.MAP)) {
            new LeaderboardGUI(getPlugin()).show(p);
        }
    }
}
