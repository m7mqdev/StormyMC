package dev.m7mqd.stormymc.menus.utils;

import dev.m7mqd.stormymc.utils.TextHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemStackBuilder {
    private String displayName;
    private Material material;
    private HashMap<Enchantment, Integer> enchantments;
    private List<String> lore;
    private short dur;
    private int amount;
    private ItemFlag[] itemFlags;
    private boolean unbreakable = false;

    public ItemStackBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public ItemStackBuilder setItemFlags(ItemFlag... itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }

    public ItemFlag[] getItemFlags() {
        return itemFlags;
    }

    public int getAmount() {
        return amount > 0 ? amount : 1;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }


    public ItemStackBuilder setDur(short dur){
        this.dur = dur;
        return this;
    }
    public ItemStackBuilder(Material material){
        this.material = material;
        this.enchantments = new HashMap<>();
    }

    public ItemStackBuilder clearEnchantments(){
        enchantments.clear();
        return this;
    }
    public ItemStackBuilder removeEnchantment(Enchantment enchantment){
        enchantments.remove(enchantment);
        return this;
    }
    public ItemStackBuilder setEnchantment(Enchantment enchantment, int level){
        enchantments.put(enchantment, level);
        return this;
    }
    public HashMap<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public List<String> getLore() {
        return lore;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        this.displayName = TextHelper.format(displayName);
        return this;
    }

    public ItemStackBuilder setEnchantments(HashMap<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemStackBuilder setLore(String... lore) {
        this.lore = Arrays.stream(lore).map(TextHelper::format).collect(Collectors.toList());
        return this;
    }

    public ItemStackBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }
    public ItemStackBuilder setEnchantments(Enchantment... enchantments) {
        if(enchantments.length == 0) return this;
        for(Enchantment enchantment : enchantments){
            getEnchantments().put(enchantment, 1);
        }
        return this;
    }


    public short getDur() {
        return dur;
    }
    public ItemStack getItemStack(){
        return new ItemStack(getMaterial(), getAmount(), getDur());
    }
    public ItemStack build(){
        ItemStack itemStack = getItemStack();
        if(enchantments != null && !enchantments.isEmpty()){
            ItemMeta finalMeta = itemStack.getItemMeta();
            enchantments.forEach((ench , level) -> finalMeta.addEnchant(ench, level, true));
            itemStack.setItemMeta(finalMeta);
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(isUnbreakable()) meta.spigot().setUnbreakable(true);
        if(displayName != null)meta.setDisplayName(displayName);
        List<String> lore = getLore();
        if(lore != null && !lore.isEmpty())meta.setLore(lore);
        if(getItemFlags() != null && getItemFlags().length > 0){
            meta.addItemFlags(getItemFlags());
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
