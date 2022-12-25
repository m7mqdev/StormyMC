package dev.m7mqd.stormymc.menus;

import dev.m7mqd.stormymc.menus.abstraction.Menu;
import dev.m7mqd.stormymc.menus.abstraction.RankInventoryHolder;
import dev.m7mqd.stormymc.menus.utils.ItemStackBuilder;
import dev.m7mqd.stormymc.ranks.DurationsEnum;
import dev.m7mqd.stormymc.ranks.RanksEnum;
import dev.m7mqd.stormymc.utils.TextHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class SelectableDurationMenu extends Menu {
    private final LuckPerms luckPerms;

    @Override
    public String getName() {
        return "Select the duration for this user";
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
        throw new UnsupportedOperationException("You should use the custom SelectableDurationMenu's building method, this one is not supported!");
    }

    public Inventory build(InventoryHolder inventoryHolder){
        return buildItems(Bukkit.createInventory(inventoryHolder, InventoryType.HOPPER, getName()));
    }

    @Override
    public void setDefaultContents(HashMap<Integer, ItemStackBuilder> defaultContents){
        for(DurationsEnum duration : DurationsEnum.values()){
            defaultContents.put(duration.getPosition(), new ItemStackBuilder(Material.BOOK_AND_QUILL).
                    setDisplayName(duration.toString()).
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
        DurationsEnum durationsEnum = DurationsEnum.getFromPosition(event.getSlot());
        if(durationsEnum == null) return;
        RankInventoryHolder inventoryHolder = (RankInventoryHolder) inventory.getHolder();
        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        String rankName = inventoryHolder.getSelectedRank();
        long duration = durationsEnum.getDuration();
        TimeUnit timeUnit = durationsEnum.getTimeUnit();
        User loadedUser = luckPerms.getUserManager().getUser(inventoryHolder.getSelectedPlayerName());
        Group group = luckPerms.getGroupManager().getGroup(rankName);
        if(group == null) throw new NullPointerException("Group doesn't exist!");
        player.closeInventory();
        if(loadedUser != null){
            setRank(loadedUser, group, duration, timeUnit);
            luckPerms.getUserManager().saveUser(loadedUser);
            player.sendMessage(TextHelper.format("&aSuccessfully set the rank of the player to: " + group.getDisplayName()));
            return;
        }
        UserManager userManager = luckPerms.getUserManager();
        userManager.lookupUniqueId(inventoryHolder.getSelectedPlayerName()).whenComplete((userUUID, error) ->{
                if(error != null || userUUID == null){
                    player.sendMessage(TextHelper.format("&cPlayer not found")); //Can be accessible in other threads because it's only a packet that doesn't access Bukkit API
                    return;
                }
                userManager.modifyUser(userUUID, (user) -> {
                    setRank(user, group, duration, timeUnit);
                    player.sendMessage(TextHelper.format("&aSuccessfully set the rank of the player to: " + group.getDisplayName()));
                });
        });
    }

    public void setRank(User user, Group group, long duration, TimeUnit timeUnit){
        // Remove all inherited groups the user had before.
        user.data().clear(NodeType.INHERITANCE::matches);
        // Create a node to add to the player.
        Node node = InheritanceNode.builder(group).expiry(duration, timeUnit).build();
        // Add the node to the user.
        user.data().add(node);
    }
}
