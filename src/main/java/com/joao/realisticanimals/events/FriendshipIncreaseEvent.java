package com.joao.realisticanimals.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FriendshipIncreaseEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    private final Player player;
    private final Entity animal;
    private final int friendship;

    public Player getPlayer() { return player; }
    public Entity getAnimal() { return animal; }
    public int getFriendship() { return friendship; }

    public FriendshipIncreaseEvent(Player player, Entity animal, int friendship){
        cancelled = false;
        this.player = player;
        this.animal = animal;
        this.friendship = friendship;
    }

    @Override
    public HandlerList getHandlers() { return HANDLERS; }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() { return cancelled; }

    @Override
    public void setCancelled(boolean b)  { this.cancelled = b; }


}
