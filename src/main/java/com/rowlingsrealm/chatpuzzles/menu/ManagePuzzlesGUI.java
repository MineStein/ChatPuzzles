package com.rowlingsrealm.chatpuzzles.menu;

import com.rowlingsrealm.chatpuzzles.ChatPuzzleManager;
import com.rowlingsrealm.chatpuzzles.ChatPuzzlesPlugin;
import com.rowlingsrealm.chatpuzzles.item.ItemBuilder;
import com.rowlingsrealm.chatpuzzles.listener.ChatListener;
import com.rowlingsrealm.chatpuzzles.message.Message;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Tyler Grissom 2018
 */
public class ManagePuzzlesGUI extends MenuGUI {

    @Getter
    private ItemStack previousPage, nextPage, create;

    public ManagePuzzlesGUI(ChatPuzzlesPlugin plugin) {
        super("Manage Puzzles", 54, plugin);

        ChatPuzzleManager cpm = plugin.getChatPuzzleManager();

        for (String puzzle : cpm.getPuzzles()) {
            ItemStack item = new ItemBuilder()
                    .type(Material.PAPER)
                    .name("§d" + puzzle)
                    .lore(
                            "",
                            "§c§lClick §7to remove"
                    )
                    .build();

            addOption(item);
        }

        previousPage = new ItemBuilder()
                .type(Material.ARROW)
                .name("§7§oPrevious page")
                .build();

        nextPage = new ItemBuilder()
                .type(Material.ARROW)
                .name("§7§oNext page")
                .build();

        create = new ItemBuilder()
                .type(Material.INK_SACK)
                .data((byte) 10)
                .name("§a§lCreate puzzle")
                .build();

        addOption(previousPage, 46);
        addOption(create, 49);
        addOption(nextPage, 52);

        for (int i = 36; i < 54; i++) {
            if (getInventory().getContents()[i] == null) {
                addOption(new ItemBuilder().type(Material.STAINED_GLASS_PANE).name(" ").data((byte) 9).build(), i);
            }
        }



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
        ChatPuzzleManager cpm = ChatPuzzlesPlugin.getInstance().getChatPuzzleManager();

        e.setCancelled(true);

        if (p.hasPermission("puzzles.manage")) {

            ItemStack item = e.getCurrentItem();

            if (item == null) return;

            if (item.getType().equals(Material.ARROW)) {
                p.sendMessage(Message.IN_PROGRESS.get());
                p.closeInventory();
            } else if (item.getType().equals(Material.INK_SACK)) {
                p.sendMessage(Message.CHAT_TO_CREATE.get());
                p.closeInventory();

                if (!ChatListener.getCreatingWords().contains(p.getUniqueId())) ChatListener.getCreatingWords().add(p.getUniqueId());
            } else if (item.getType().equals(Material.PAPER)) {
                String word = ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase());

                cpm.removePuzzle(word);

                p.sendMessage(Message.WORD_REMOVED.get());
                p.closeInventory();
            }

        } else {
            p.sendMessage(Message.NO_PERMISSION.get());
            p.closeInventory();
        }
    }
}
