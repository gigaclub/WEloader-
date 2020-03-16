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
import net.gigaclub.feier68.weloader.listeners.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

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

    int count = 0;

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

    public static boolean endSchematic() throws IOException {
        String pl = "[WEloader] ";
        FileConfiguration config = Main.getPlugin().getConfig();
        if (!(config.isSet("Load.end"))) {
            System.out.println(pl + "Load End Config Wurde gesetzt");
            config.set("Load.end", false);
            Main.getPlugin().saveConfig();
        }
        String endSchem = config.getString("Schematic.End");
        boolean worldSpawn = config.getBoolean("Load.world");
        boolean endSpawn = config.getBoolean("Load.end");

        if (endSpawn) {
            File file = new File("plugins/WEloader/schematics/" + endSchem);

            if (file.exists()) {
                System.out.println(pl + "End");
                System.out.println(pl + "File exists");
            } else {
                System.out.println(pl + "File " + endSchem + " dont exists");
                return true;
            }
            if (!worldSpawn) {
                World world = Bukkit.getWorld("world_the_end");
                ClipboardFormat format = ClipboardFormats.findByFile(file);
                try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                    Clipboard clipboard = reader.read();

                    try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((BukkitAdapter.adapt(world)), -1)) {
                        Operation operation = new ClipboardHolder(clipboard)
                                .createPaste(editSession)
                                .to(BlockVector3.at(0, 70, 80))
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

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        String pl = "[WEloader] ";
        System.out.println(pl + "auf wider sehen");
    }

    @Override
    public void onEnable() {
        plugin = this;
        String pl = "[WEloader] ";

        System.out.println(pl + "WELoader Geladen");

        FileConfiguration config = Main.getPlugin().getConfig();
        if (!(config.isSet("Schematic.Nether"))) {
            config.set("Schematic.Nether", "NetherSpawn.schem");
        }
        Main.getPlugin().saveConfig();
        if (!(config.isSet("Schematic.World"))) {
            config.set("Schematic.World", "WorldSpawn.schem");
        }
        Main.getPlugin().saveConfig();
        if (!(config.isSet("Schematic.end"))) {
            config.set("Schematic.End", "EndSpawn.schem");
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
            endSchematic();
        } catch (IOException e) {
            e.printStackTrace();
        }


        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                int mincount = count / 60;
                if (count % 360 * 60 == 0 || count == 1260 * 60 || count == 1320 * 60 || count == 1380 * 60) {
                    Bukkit.broadcastMessage("Die Farmwelt resettet in " + (24 - (mincount / 60)) + " Stunden!");
                } else if (count == 1395 * 60 || count == 1410 * 60 || count == 1425 * 60 || count == 1430 * 60 || count == 1435 * 60 || count == 1439 * 60) {
                    Bukkit.broadcastMessage("Die Farmwelt resettet in " + (1440 - mincount) + " Minuten!");
                } else if (count > 86394) {
                    Bukkit.broadcastMessage("Die Farmwelt resettet in " + (86400 - count) + " Sekunden!");
                } else if (mincount == 1440) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
                }
                count++;

            }
        }, 0, 20);

    }

}



