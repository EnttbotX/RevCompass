package x.Entt.RevCompass.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import x.Entt.RevCompass.RC;

import java.util.ArrayList;
import java.util.Iterator;

public class Events implements Listener {
    private RC plugin;

    public Events(RC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        plugin.find.put(event.getPlayer(), event.getPlayer().getKiller());
        if (plugin.find.get(event.getPlayer()) instanceof Player) {
            Player player = event.getPlayer();
            Player killer = event.getPlayer().getKiller();
            ItemStack Compass = new ItemStack(Material.COMPASS);
            ItemMeta CompassMeta = Compass.getItemMeta();
            ArrayList<String> Lore = new ArrayList();
            Iterator var8 = plugin.getConfig().getStringList("Lore").iterator();

            while(var8.hasNext()) {
                String s = (String)var8.next();
                Lore.add(s.replaceAll("&", "§").replaceAll("%player%", player.getName()).replaceAll("%killer%", killer.getName()));
            }

            CompassMeta.setLore(Lore);
            Compass.setItemMeta(CompassMeta);
            player.getInventory().addItem(new ItemStack[]{Compass});
            plugin.item.put(player, Compass);
        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getInventory().contains(Material.COMPASS) && plugin.find.containsKey(event.getPlayer())) {
            Inventory inv = event.getPlayer().getInventory();
            if (plugin.item.get(event.getPlayer()) != null) {
                ItemStack Compass = (ItemStack)plugin.item.get(event.getPlayer());
                ItemMeta CompassMeta = Compass.getItemMeta();
                Player Killer = (Player)plugin.find.get(event.getPlayer());
                int Distance = (int)event.getPlayer().getLocation().distance(Killer.getLocation());
                String DisplayName = plugin.getConfig().getString("Compass-Name").replaceAll("&", "§").replaceAll("%killer%", Killer.getName()).replaceAll("%distance%", String.valueOf(Distance));
                CompassMeta.setDisplayName(DisplayName);
                Compass.setItemMeta(CompassMeta);
                event.getPlayer().setCompassTarget(Killer.getLocation());
                plugin.item.put(event.getPlayer(), Compass);

                for(int i = 0; i < inv.getSize(); ++i) {
                    if (inv.getItem(i) != null && inv.getItem(i).getType() == Material.COMPASS) {
                        inv.setItem(i, Compass);
                    }
                }
            }
        }

    }
}