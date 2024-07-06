package x.Entt.RevCompass;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import x.Entt.RevCompass.Events.Events;
import x.Entt.RevCompass.Utils.MSG;

public class RC extends JavaPlugin{
   public HashMap<Player, Player> find = new HashMap();
   public HashMap<Player, ItemStack> item = new HashMap();
   public String prefix = "&4&lRev&c&lCompass ";
   public String version = getDescription().getVersion();

   @Override
   public void onEnable() {
      registerEvents();
      registerFiles();

      Bukkit.getConsoleSender().sendMessage
              (MSG.color(prefix + "&av" + version + " &2Enabled!"));
   }

   @Override
   public void onDisable() {
      Bukkit.getConsoleSender().sendMessage
              (MSG.color(prefix + "&av" + version + " &cDisabled"));
   }

   public void registerFiles() {
      File configFile = new File(this.getDataFolder(), "config.yml");
      FileConfiguration config = this.getConfig();

      if (!configFile.exists()) {
         this.saveDefaultConfig();
         config.options().copyDefaults(true);
         this.saveConfig();
      }
   }

   public void registerEvents() {
      this.getServer().getPluginManager().registerEvents(new Events(this), (this));
   }
}