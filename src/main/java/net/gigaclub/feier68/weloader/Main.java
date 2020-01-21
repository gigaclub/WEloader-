package net.gigaclub.feier68.weloader;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import net.gigaclub.feier68.weloader.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public final class Main extends JavaPlugin {
    private static Main plugin;

    public static boolean loadIslandSchematic() throws IOException {
        String pl = "[WEloader] ";
        FileConfiguration config = Main.getPlugin().getConfig();
        if (!(config.isSet("Load.world"))) {
            System.out.println("Load World Config Wurde gesetzt");
            config.set("Load.world", true);
            Main.getPlugin().saveConfig();
        }
        String worldSchem = config.getString("Schematic.World");
        boolean worldSpawn = config.getBoolean("Load.world");
        if (worldSpawn) {
            File file = new File("plugins/WEloader/schematics/" + worldSchem);
            if (file.exists()) {
                System.out.println(pl + "World");
                System.out.println(pl + "File exists");
            } else {
                System.out.println(pl + "File " + worldSchem + " dont exists");
                return true;
            }


            World world = Bukkit.getWorld("world");
            ClipboardFormat format = ClipboardFormats.findByFile(file);
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                Clipboard clipboard = reader.read();

                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((BukkitAdapter.adapt(world)), -1)) {
                    Operation operation = new ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BlockVector3.at(0, 125, 0))
                            .ignoreAirBlocks(false)
                            .build();
                    Operations.complete(operation);
                    System.out.println(pl + "Schematic Geladen");
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            }

        }

        return true;
    }
    public static boolean nehterSchematic() throws IOException {
        String pl = "[WEloader] ";
        FileConfiguration config = Main.getPlugin().getConfig();
        if (!(config.isSet("Load.nether"))) {
            System.out.println(pl + "Load Nether Config Wurde gesetzt");
            config.set("Load.nether", false);
            Main.getPlugin().saveConfig();
        }
        String nehterSchem = config.getString("Schematic.Nether");
        boolean worldSpawn = config.getBoolean("Load.world");
        boolean nehterSpawn = config.getBoolean("Load.nether");

        if (nehterSpawn) {
            File file = new File("plugins/WEloader/schematics/" + nehterSchem);

            if (file.exists()) {
                System.out.println(pl + "Nehter");
                System.out.println(pl + "File exists");
            } else {
                System.out.println(pl + "File " + nehterSchem + " dont exists");
                return true;
            }
            if (!worldSpawn) {
                World world = Bukkit.getWorld("world_nether");
                ClipboardFormat format = ClipboardFormats.findByFile(file);
                try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                    Clipboard clipboard = reader.read();

                    try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((BukkitAdapter.adapt(world)), -1)) {
                        Operation operation = new ClipboardHolder(clipboard)
                                .createPaste(editSession)
                                .to(BlockVector3.at(0, 100, 0))
                                .ignoreAirBlocks(false)
                                .build();
                        Operations.complete(operation);
                        System.out.println(pl + "Schematic Geladen");
                    } catch (WorldEditException e) {
                        e.printStackTrace();
                    }
                }

            }

        }


        return true;
    }
    @Override
    public void onDisable() {
        String pl = "[WEloader] ";
        System.out.println(pl + "BB");
    }

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        String pl = "[WEloader] ";
        new File("plugins/WEloader/schematics").mkdir();
        BlockVector3 min = BlockVector3.at(-30, 150, -30);
        BlockVector3 max = BlockVector3.at(30, -8, 30);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion("spawn", min, max);

        System.out.println(pl + "WELoader Geladen");

        FileConfiguration config = Main.getPlugin().getConfig();
        if (!(config.isSet("Schematic.Nether"))) {
            config.set("Schematic.Nether", "NeterSpawn.schem");
        }
        Main.getPlugin().saveConfig();
        if (!(config.isSet("Schematic.World"))) {
            config.set("Schematic.World", "island.schem");
        }
        Main.getPlugin().saveConfig();

        boolean nehterSpawn = config.getBoolean("Load.nether");
        boolean worldSpawn = config.getBoolean("Load.world");
        if (nehterSpawn)
            if (worldSpawn)
                System.out.println(pl + "es sind beide activ");
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        try {
            loadIslandSchematic();
            nehterSchematic();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



