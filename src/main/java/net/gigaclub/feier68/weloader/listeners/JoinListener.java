package net.gigaclub.feier68.weloader.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

   @EventHandler
   public void handlePlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      World world = Bukkit.getWorld("world");
      Location location = new Location(world, 0.0, 101, 0.0);

      player.teleport(location);


   }

   @EventHandler
   public void NehterhandlePlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      World world = Bukkit.getWorld("world_nether");
      Location location = new Location(world, 0.0, 101, 0.0);

      player.teleport(location);


   }

}
