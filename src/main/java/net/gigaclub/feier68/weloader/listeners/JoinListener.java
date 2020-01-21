package net.gigaclub.feier68.weloader.listeners;

import net.gigaclub.feier68.weloader.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

   @EventHandler
   public void handlePlayerJoin(PlayerJoinEvent event) {
      FileConfiguration config = Main.getPlugin().getConfig();
      boolean worldSpawn = config.getBoolean("Load.world");
      if (worldSpawn) {
         Player player = event.getPlayer();
         World world = Bukkit.getWorld("world");
         Location location = new Location(world, 0.0, 126, 0.0);

         player.teleport(location);

      }
   }

   @EventHandler
   public void NehterhandlePlayerJoin(PlayerJoinEvent event) {
      FileConfiguration config = Main.getPlugin().getConfig();
      boolean nehterSpawn = config.getBoolean("Load.nether");
      boolean worldSpawn = config.getBoolean("Load.world");

      if (worldSpawn) {
         return;
      } else if (nehterSpawn) {
         Player player = event.getPlayer();
         World world = Bukkit.getWorld("world_nether");
         Location location = new Location(world, 0.5, 101, 0.5);

         player.teleport(location);

      }
   }

   }

