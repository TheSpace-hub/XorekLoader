package hub.thespace.xorekloader;

import org.bukkit.plugin.java.JavaPlugin;

public final class XorekLoader extends JavaPlugin {

    private final Executor executor = new Executor(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (executor.isEnabled())
            executor.execute();
    }

}
