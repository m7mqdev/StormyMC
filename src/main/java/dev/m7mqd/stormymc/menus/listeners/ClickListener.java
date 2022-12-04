package dev.m7mqd.stormymc.menus.listeners;

import dev.m7mqd.stormymc.menus.SelectableRankMenu;
import dev.m7mqd.stormymc.menus.abstraction.RankInventoryHolder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class ClickListener implements Listener {
    private final @Getter SelectableRankMenu selectableRankMenu;
    public ClickListener(SelectableRankMenu selectableRankMenu){
        this.selectableRankMenu = selectableRankMenu;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)) return;
        Inventory inventory = event.getClickedInventory();
        if(inventory == null || inventory.getTitle() == null || !(inventory.getHolder() instanceof RankInventoryHolder)) return;
        // I can make a menus system based on a MenusManager but this is a simple plugin of 2 menus, lazy to create a whole manager for it.
        if(inventory.getTitle().equals(selectableRankMenu.getName())) selectableRankMenu.handleClick(event);
        else if(inventory.getTitle().equals(selectableRankMenu.getSelectableDurationMenu().getName())) selectableRankMenu.getSelectableDurationMenu().handleClick(event);
    }
    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(!(event.getPlayer() instanceof Player)) return;
        Inventory inventory = event.getInventory();
        if(inventory == null || !(inventory.getHolder() instanceof RankInventoryHolder)) return;
        // I can make a menus system based on a MenusManager but this is a simple plugin of 2 menus, lazy to create a whole manager for it.
        if(inventory.getTitle().equals(selectableRankMenu.getName())) selectableRankMenu.handleClose(event);
        else if(inventory.getTitle().equals(selectableRankMenu.getSelectableDurationMenu().getName())) selectableRankMenu.getSelectableDurationMenu().handleClose(event);
    }
}
