package com.rowlingsrealm.chatpuzzles.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Copyright Tyler Grissom 2018
 */
public class PuzzleAnnounceEvent extends Event implements Cancellable {

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private String word;

    @Getter
    private String lastWord;

    @Getter
    private boolean lastWordCompleted;

    public PuzzleAnnounceEvent(String word, String lastWord, boolean lastWordCompleted) {
        this.word = word;
        this.lastWord = lastWord;
        this.lastWordCompleted = lastWordCompleted;
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
