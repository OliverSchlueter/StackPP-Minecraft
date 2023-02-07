package de.oliver.stackppmcplugin;

import de.oliver.stackpp.virtualMachine.Machine;
import de.oliver.stackppmcplugin.commands.StackppCMD;
import org.bukkit.plugin.java.JavaPlugin;

public final class StackppMcPlugin extends JavaPlugin {

    private static StackppMcPlugin plugin;

    private Machine machine;

    public StackppMcPlugin() {
        plugin = this;
        machine = new Machine();
    }

    @Override
    public void onEnable() {
        getCommand("StackPP").setExecutor(new StackppCMD());
    }

    @Override
    public void onDisable() {

    }

    public Machine getMachine() {
        return machine;
    }

    public static StackppMcPlugin getPlugin() {
        return plugin;
    }
}
