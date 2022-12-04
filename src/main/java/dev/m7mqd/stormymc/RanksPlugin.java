package dev.m7mqd.stormymc;

import dev.m7mqd.stormymc.commands.TempRank;
import dev.m7mqd.stormymc.menus.SelectableRankMenu;
import dev.m7mqd.stormymc.menus.listeners.ClickListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class RanksPlugin extends JavaPlugin {
    private @Getter SelectableRankMenu selectableRankMenu;
    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if(!pluginManager.isPluginEnabled("LuckPerms")){
            getLogger().log(Level.WARNING, "Could not find LuckPerms, this plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        selectableRankMenu = new SelectableRankMenu();
        pluginManager.registerEvents(new ClickListener(selectableRankMenu), this);
        this.getCommand("temprank").setExecutor(new TempRank(selectableRankMenu));
    }
}
