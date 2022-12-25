package dev.m7mqd.stormymc.commands;

import dev.m7mqd.stormymc.menus.SelectableRankMenu;
import dev.m7mqd.stormymc.utils.TextHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TempRank implements CommandExecutor {
    private final @Getter SelectableRankMenu selectableRankMenu;
    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(TextHelper.format("You are not allowed to use graphic user interface commands such as this one."));
            return true;
        }
        Player player = (Player) commandSender;
        if(!player.hasPermission("temprank.use")){
            player.sendMessage(TextHelper.format("&cYou are not allowed to use this command."));
            return true;
        }
        if(args.length != 1){
            player.sendMessage(TextHelper.format("&cUsage: /temprank <playername>"));
            return true;
        }
        String playerName = args[0];
        int length = playerName.length();
        if(length > 16 || length < 3){
            player.sendMessage(TextHelper.format("&cThis is not a real player name"));
        }
        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        player.openInventory(selectableRankMenu.build(playerName));
        return true;
    }
}
