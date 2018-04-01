package com.rowlingsrealm.chatpuzzles.menu;

import com.rowlingsrealm.chatpuzzles.ChatPuzzleManager;
import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import com.rowlingsrealm.chatpuzzles.item.ItemBuilder;
import com.rowlingsrealm.chatpuzzles.message.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Tyler Grissom 2018
 */
public class ManagePuzzlesGUI extends MenuGUI {

    private String truncate(String str, int length) {
        if (length <= str.length()) return str;

        return str.substring(0, length) + "...";
    }

    public ManagePuzzlesGUI(ChatPuzzlesPlugin plugin, int page) {
        super("Manage Puzzles", 54, plugin);

        ChatPuzzleManager cpm = plugin.getChatPuzzleManager();

        ItemStack previousPage = new ItemBuilder()
                .type(Material.ARROW)
                .name("§7§oPrevious page")
                .build();

        ItemStack nextPage = new ItemBuilder()
                .type(Material.ARROW)
                .name("§7§oNext page")
                .build();

        ItemStack create = new ItemBuilder()
                .type(Material.INK_SACK)
                .data((byte) 10)
                .name("§a§lCreate puzzle")
                .build();

        addOption(previousPage, 45);
        addOption(create, 46);
        addOption(nextPage, 51);

//        ChatPuzzleManager cpm = plugin.getChatPuzzleManager();
//        List<String> puzzles = cpm.getPuzzles();
//        int pageIndex = page - 1;
//        int pageCount;
//        Map<Integer, List<String>> pages = new HashMap<>();
//
//        if (puzzles.size() < 36) pageCount = 1;
//        else if (puzzles.size() % 36 > 0) pageCount = ((puzzles.size() / 36) + 1);
//        else pageCount = puzzles.size() / 36;
//
//        if (pageIndex > pageCount) pageIndex = 0;
//
//        for (int i = 0; i < pageCount; i++) {
//            List<String> sublist = puzzles.subList(0, 36);
//
//            pages.put(i, sublist);
//
//            puzzles.removeAll(sublist);
//        }
//
//        setTitle("Manage Puzzles (" + page + "/" + pageCount + ")");
//
//        List<String> currentPuzzles = pages.get(pageIndex);
//
//        for (String str :
//                currentPuzzles) {
//            ItemStack item = new ItemBuilder()
//                    .type(Material.PAPER)
//                    .name("§dPuzzle #" + (cpm.getPuzzles().indexOf(str) + 1))
//                    .lore("", "§5§o" + truncate(str, 12), "", "§c§lClick §7to remove")
//                    .build();
//
//            addOption(item);
//        }
//
//        ItemStack previousPage = new ItemBuilder()
//                .type(Material.ARROW)
//                .name("§7§oPrevious page")
//                .build();
//
//        ItemStack nextPage = new ItemBuilder()
//                .type(Material.ARROW)
//                .name("§7§oNext page")
//                .build();
//
//        ItemStack create = new ItemBuilder()
//                .type(Material.INK_SACK)
//                .data((byte) 10)
//                .name("§a§lCreate puzzle")
//                .build();
//
//        addOption(previousPage, 45);
//        addOption(create, 48);
//        addOption(nextPage, 53);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Player p = ((Player) e.getWhoClicked());

        if (p.hasPermission("puzzles.manage")) {
            p.sendMessage(Message.IN_PROGRESS.get());
        } else {
            p.sendMessage(Message.NO_PERMISSION.get());
            p.closeInventory();
        }
    }
}
