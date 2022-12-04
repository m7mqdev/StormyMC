package dev.m7mqd.stormymc.menus.abstraction;

import dev.m7mqd.stormymc.menus.utils.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public abstract class Menu {
    private final HashMap<Integer, ItemStackBuilder> defaultContents = new HashMap<>();
    public abstract String getName();
    public abstract int getRows();
    public void handleClick(InventoryClickEvent event) {}
    public void handleClose(InventoryCloseEvent event) {}
    public Menu(){
        setDefaultContents(defaultContents);
    }

    public HashMap<Integer, ItemStackBuilder> getDefaultContents() {
        return defaultContents;
    }

    public abstract void setDefaultContents(HashMap<Integer, ItemStackBuilder> defaultContents);
    public Inventory buildItems(Inventory inventory){
        if(defaultContents.isEmpty()) return inventory;
        defaultContents.forEach((k, v) -> inventory.setItem(k, v.build()));
        return inventory;
    }
    public Inventory build(){
        return buildItems(Bukkit.createInventory(null, getSize(), getName()));
    }

    public int getSize(){
        return getRows()*9;
    }
}
