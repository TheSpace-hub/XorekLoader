package hub.thespace.xorekloader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class Executor implements Runnable {
    private final Plugin plugin;
    private List<String> commands;
    private int cooldown;

    public Executor(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Получить данные из конфига и запустить выполнение.
     */
    public void execute() {
        commands = getCommands();
        cooldown = getCooldown();
        Bukkit.getScheduler().runTaskLater(plugin, this, 1L);
    }

    @Override
    public void run() {
        if (commands.isEmpty())
            return;
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commands.get(commands.size() - 1));
        commands.remove(commands.size() - 1);
        Bukkit.getScheduler().runTaskLater(plugin, this, cooldown);
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
     * Получение задержки между командами.
     *
     * @return Задержка в тиках.
     */
    private int getCooldown() {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains("cooldown")) {
            plugin.getLogger().severe("В файле конфигурации нет поля cooldown! (Поле изменено на 10)");
            config.set("cooldown", 10);
            return 10;
        }
        Object value = config.get("cooldown");
        if (!(value instanceof Integer)) {
            plugin.getLogger().severe("В файле конфигурации поле cooldown должно быть целым числом! (Поле изменено на 10)");
            config.set("cooldown", 10);
            return 10;
        }
        return (int) value;
    }

    /**
     * Получение списка команд, которые необходимо выполнить.
     *
     * @return Список команд.
     */
    private List<String> getCommands() {
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
