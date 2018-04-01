package com.rowlingsrealm.chatpuzzles.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Copyright Tyler Grissom 2018
 */
public class PuzzleCompletionEvent extends Event implements Cancellable {

    private boolean cancelled;
    private static HandlerList handlers = new HandlerList();

    @Getter
    private String word;

    @Getter
    @Setter
    private Player winner;

    public PuzzleCompletionEvent(String word, Player winner) {
        this.word = word;
        this.winner = winner;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
