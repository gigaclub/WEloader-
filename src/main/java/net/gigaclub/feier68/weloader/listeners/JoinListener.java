package net.gigaclub.feier68.weloader.listeners;

import net.gigaclub.feier68.weloader.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;


public class JoinListener implements Listener {

   HashMap<Player, Integer> fallcount = new HashMap<>();

   @EventHandler
   public void handlePlayerJoin(PlayerJoinEvent event) {
      FileConfiguration config = Main.getPlugin().getConfig();
      boolean worldSpawn = config.getBoolean("Load.world");
      if (worldSpawn) {
         Player player = event.getPlayer();

         if (fallcount.get(player) != null) {
            fallcount.remove(player);
         }
         fallcount.put(player, 0);

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
         if (fallcount.get(player) != null) {
            fallcount.remove(player);
         }
         fallcount.put(player, 0);
         World world = Bukkit.getWorld("world_nether");
         Location location = new Location(world, 0.5, 101, 0.5);
         player.teleport(location);

      }
   }

   @EventHandler
   public void endPlayerJoin(PlayerJoinEvent event) {
      FileConfiguration config = Main.getPlugin().getConfig();
      boolean worldSpawn = config.getBoolean("Load.end");
      if (worldSpawn) {
         Player player = event.getPlayer();
         if (fallcount.get(player) != null) {
            fallcount.remove(player);
         } else {
            fallcount.put(player, 0);
         }

         World world = Bukkit.getWorld("world_the_end");
         Location location = new Location(world, 0.0, 71, 80.0);
         player.teleport(location);

      }
   }


   @EventHandler
   public void dmg(final EntityDamageEvent event) {
      FileConfiguration config = Main.getPlugin().getConfig();


      if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
      } else {

         Entity e = event.getEntity();
         if (e instanceof Player) {
            Player player = (Player) e;


            fallcount.replace(player, fallcount.get(player) + 1);
            if (fallcount.get(player) == 1) {
               event.setCancelled(true);
               player.sendMessage("Healing spell");
            }
         }
      }
   }


   @EventHandler
   public void PlayerMove(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      Location from = event.getFrom();
      Location to = event.getTo();
      if (!to.getWorld().getBlockAt(to).equals(from.getWorld().getBlockAt(from))) // checks if you moved at least one block
      {
         Block checkWater = event.getPlayer().getLocation().getBlock();
         if (checkWater.getType() == Material.WATER) {
            fallcount.replace(player, fallcount.get(player) + 1);
         }

      }
   }

   @EventHandler(priority = EventPriority.HIGHEST)
   public void PlayerRespawnEvent(PlayerRespawnEvent event) {
      Player player = event.getPlayer();
      FileConfiguration config = Main.getPlugin().getConfig();
      boolean nehterSpawn = config.getBoolean("Load.nether");
      boolean worldSpawn = config.getBoolean("Load.world");
      boolean endSpawn = config.getBoolean("Load.end");
      if (worldSpawn) {
         World world = Bukkit.getWorld("world");
         Location location = new Location(world, 0.0, 126, 0.0);
         event.setRespawnLocation(location);
      } else if (nehterSpawn) {
         World world = Bukkit.getWorld("world_nether");
         Location location = new Location(world, 0.5, 101, 0.5);
         event.setRespawnLocation(location);
      } else if (endSpawn) {
         World world = Bukkit.getWorld("world_the_end");
         Location location = new Location(world, 0.0, 71, 80.0);
         event.setRespawnLocation(location);
      }

   }

   @EventHandler
   public void PlayerDeathEvent(PlayerDeathEvent event) {
      Player player = event.getEntity().getPlayer();
      if (event.getEntity() == player) {
         fallcount.put(player, 0);
      }

   }


}




