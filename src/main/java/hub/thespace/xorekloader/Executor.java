package hub.thespace.xorekloader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

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

    /**
     * Получение списка команд, которые необходимо выполнить.
     *
     * @return Список команд.
     */
    public List<String> getCommands() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("cmds")) {
            plugin.getLogger().severe("В файле конфигурации нет поля cmds! (Поле изменено на пустой список)");
            config.set("cmds", List.of());
            return List.of();
        }
        Object value = config.get("cmds");
        if (!(value instanceof List)) {
            plugin.getLogger().severe("В файле конфигурации поле cmds должно быть списком строк! (Поле изменено на пустой список)");
            config.set("cmds", List.of());
            return List.of();
        }
        return config.getStringList("cmds");
    }

}
