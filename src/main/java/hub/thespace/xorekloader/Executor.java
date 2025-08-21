package hub.thespace.xorekloader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Executor {
    private final Plugin plugin;

    public Executor(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Проверяет необходимость в работе плагина.
     *
     * @return Нужно ли запускать команды.
     */
    public boolean isEnabled() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("enabled")) {
            plugin.getLogger().severe("В файле конфигурации нет поля enabled! (Поле изменено на false)");
            config.set("enabled", false);
            return false;
        }
        Object value = config.get("enabled");
        if (!(value instanceof Boolean)) {
            plugin.getLogger().severe("В файле конфигурации поле enabled должно быть true или false! (Поле изменено на false)");
            config.set("enabled", false);
            return false;
        }
        return (boolean) value;
    }

}
