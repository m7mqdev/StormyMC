package dev.m7mqd.stormymc.menus;

import dev.m7mqd.stormymc.menus.abstraction.Menu;
import dev.m7mqd.stormymc.menus.abstraction.RankInventoryHolder;
import dev.m7mqd.stormymc.menus.utils.ItemStackBuilder;
import dev.m7mqd.stormymc.ranks.RanksEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;

public class SelectableRankMenu extends Menu {
    private final @Getter SelectableDurationMenu selectableDurationMenu;

    public SelectableRankMenu(){
        LuckPerms luckPerms = LuckPermsProvider.get();
        setDefaultContents(getDefaultContents(), luckPerms);
        selectableDurationMenu = new SelectableDurationMenu(luckPerms);
    }
    @Override
    public String getName() {
        return "Select the rank for this user";
    }
    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public int getSize() {
        return getRows()*5;
    }

    @Override
    public Inventory build() {
        throw new UnsupportedOperationException("You should use the custom SelectableRankMenu's building method, this one is not supported!");
    }

    @Override
    public void setDefaultContents(HashMap<Integer, ItemStackBuilder> defaultContents) {
    }

    public Inventory build(String playerName){
        RankInventoryHolder inventoryHolder = new RankInventoryHolder();
        inventoryHolder.setSelectedPlayerName(playerName);
        return buildItems(Bukkit.createInventory(inventoryHolder, InventoryType.HOPPER, getName()));
    }

    public void setDefaultContents(HashMap<Integer, ItemStackBuilder> defaultContents, LuckPerms luckPerms){
        for(RanksEnum rank : RanksEnum.values()){
            defaultContents.put(rank.getPosition(), new ItemStackBuilder(rank.getMaterial()).
                    setDisplayName(rank.getDisplayName(luckPerms)).
                    setEnchantment(Enchantment.LUCK, 1).
                    setItemFlags(ItemFlag.HIDE_ENCHANTS));
        }
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if(event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        RanksEnum ranksEnum = RanksEnum.getFromPosition(event.getSlot());
        if(ranksEnum == null) return;
        RankInventoryHolder inventoryHolder = (RankInventoryHolder) inventory.getHolder();
        inventoryHolder.setSelectedRank(ranksEnum.name());
        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        player.openInventory(selectableDurationMenu.build(inventoryHolder));
    }
}
