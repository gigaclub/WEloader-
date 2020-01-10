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
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.printf("PL lääääääääd");
       try{
           loadIslandSchematic();

       } catch (IOException e) {
           e.printStackTrace();
       }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static boolean loadIslandSchematic() throws IOException {

        File file = new File("plugins/WorldEdit/schematics/island.schem");

        if (file.exists()) {
            System.out.println("File exists");
        } else {
            System.out.println("Nothing....");
        }

        World world = (World) Bukkit.getWorld("world");
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
            } catch (WorldEditException e) {
                e.printStackTrace();
            }
        }


        return true;
    }

}