package x.Entt.RevCompass.Utils;

import net.md_5.bungee.api.ChatColor;

public class MSG {
    public static String color(String message) {
        if (message == null) {
            return ChatColor.RED + "message error: null";
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}