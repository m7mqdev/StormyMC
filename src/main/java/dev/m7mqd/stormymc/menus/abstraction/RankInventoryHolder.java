package dev.m7mqd.stormymc.menus.abstraction;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

@Getter
@Setter
public class RankInventoryHolder implements InventoryHolder {
    private String selectedPlayerName;
    private String selectedRank;
    @Override
    public Inventory getInventory() {
        return null;
    }
}
