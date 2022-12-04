package dev.m7mqd.stormymc.utils;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextHelper {
    public static String[] format(String... strings){
        return Arrays.stream(strings).map(TextHelper::format).toArray(String[]::new);
    }
    public static String format(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    public static List<String> format(List<String> strings){
        return strings.stream().map(TextHelper::format).collect(Collectors.toList());
    }
}
