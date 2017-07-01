package me.mcaeolus.magicinduction.data;

import me.mcaeolus.magicinduction.MagicInduction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * YML data system. Handles large file systems primarily.
 */
public class Data {
    private final String FILE_NAME;
    private final String FILE_LOCATION;
    private final String EXTENSION = ".yml";

    private File FILE;
    private FileConfiguration FILE_CONF;

    protected Data(String FILE_NAME, String FILE_LOCATION, boolean PARALLEL) {
        this.FILE_NAME = FILE_NAME;
        this.FILE_LOCATION = FILE_LOCATION;

        if(PARALLEL) try {
            parallelLoad();
        } catch (InterruptedException e) {
            MagicInduction.getInstance().getLogger().warning("ERROR: thread interrupted while loading file ("+ FILE_NAME + ") in parallel mode.\n--- Stack printed below ---");
            e.printStackTrace();
            MagicInduction.getInstance().getLogger().warning("--- END STACK ---");

            MagicInduction.getInstance().getLogger().warning("WILL CONTINUE IN NORMAL MODE.");
            loadFile(false);
        }
        else loadFile(false);
    }

    private synchronized void loadFile(boolean inParallel) {
        if (FILE == null)
            FILE = new File(fixPath(MagicInduction.getLocalServer().getWorldContainer().getAbsolutePath())
                    + FILE_LOCATION + FILE_NAME + EXTENSION);
        saveResource(FILE_NAME + EXTENSION, FILE_LOCATION, false);
        FILE_CONF = YamlConfiguration.loadConfiguration(this.FILE);

        if(!inParallel)save();
    }

    public synchronized void save(){
        if (FILE == null) loadFile(false);
        try {
            FILE_CONF.save(FILE);
        } catch (IOException ex) {
            MagicInduction.getInstance().getLogger().warning("ERROR: could not save file: " + FILE_NAME + "\n--- Stack printed below ---");
            ex.printStackTrace();
            MagicInduction.getInstance().getLogger().warning("--- END STACK ---");
        }
    }

    public void parallelSave() throws InterruptedException {
        Thread SAVE_THREAD = new Thread("SAVE_CONF");
        SAVE_THREAD.start();
        save();
        SAVE_THREAD.join();
    }

    public void parallelLoad() throws InterruptedException {
        Thread LOAD_THREAD = new Thread("LOAD_CONF");
        LOAD_THREAD.start();
        loadFile(true);
        LOAD_THREAD.join();
        parallelSave();
    }


    public void set(Object VALUE, String PATH){
        FILE_CONF.set(PATH, VALUE);
    }

    public Object get(String PATH){
        return FILE_CONF.get(PATH);
    }

    public ConfigurationSection getSection(String PATH){
        return FILE_CONF.getConfigurationSection(PATH);
    }

    public File getFile(){
        if(FILE == null) loadFile(false);
        return FILE;
    }

    public FileConfiguration getConfiguration(){
        if(FILE_CONF == null) loadFile(false);
        return FILE_CONF;
    }

    private String fixPath(String path){
        return (path.endsWith(".")?path.substring(0,path.length()-1):path);
    }

    /*
    THIS BELOW CODE IS TAKEN FROM THE #saveResource() METHOD IN JAVAPLUGIN AND HAS BEEN MODIFIED.
     */
    private void saveResource(String resourcePath, String saveLocation, boolean replace) {
        if(resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = MagicInduction.getInstance().getResource(resourcePath);
            if(in == null) {
                throw new IllegalArgumentException("The embedded resource \'" + resourcePath + "\' cannot be found!");
            } else {
                File outFile = new File(fixPath(MagicInduction.getLocalServer().getWorldContainer().getAbsolutePath())
                        + saveLocation, resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(saveLocation, resourcePath.substring(0, lastIndex >= 0?lastIndex:0));

                if(!outDir.exists()) outDir.mkdirs();

                try {
                    if(outFile.exists() && !replace) {
                        //MedievalGuilds.forLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        FileOutputStream ex = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            ex.write(buf, 0, len);
                        }

                        ex.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    MagicInduction.getInstance().getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }
}
