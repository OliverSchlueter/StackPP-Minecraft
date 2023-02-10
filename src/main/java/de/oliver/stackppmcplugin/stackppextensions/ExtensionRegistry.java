package de.oliver.stackppmcplugin.stackppextensions;

import de.oliver.stackpp.virtualMachine.Machine;
import de.oliver.stackppmcplugin.StackppMcPlugin;
import de.oliver.stackppmcplugin.stackppextensions.listeners.StackppPlayerJoinListener;
import de.oliver.stackppmcplugin.stackppextensions.syscalls.SendMessageToPlayerSyscall;
import de.oliver.stackppmcplugin.stackppextensions.syscalls.events.OnPlayerJoinSyscall;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ExtensionRegistry {

    private static SendMessageToPlayerSyscall sendMessageToPlayerSyscall;
    private static OnPlayerJoinSyscall onPlayerJoinSyscall;

    public static void registerSyscalls(Machine machine){
        // add custom syscalls
        sendMessageToPlayerSyscall = new SendMessageToPlayerSyscall(100, machine);
        machine.getSyscalls().put(100, sendMessageToPlayerSyscall);

        onPlayerJoinSyscall = new OnPlayerJoinSyscall(150, machine);
        machine.getSyscalls().put(150, onPlayerJoinSyscall);

        // remove graphics syscalls
        machine.getSyscalls().remove(7);
        machine.getSyscalls().remove(8);
        machine.getSyscalls().remove(9);

        // remove read line syscall
        machine.getSyscalls().remove(11);

        // register listeners for callbacks
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new StackppPlayerJoinListener(), StackppMcPlugin.getPlugin());
    }

    public static SendMessageToPlayerSyscall getSendMessageToPlayerSyscall() {
        return sendMessageToPlayerSyscall;
    }

    public static OnPlayerJoinSyscall getOnPlayerJoinSyscall() {
        return onPlayerJoinSyscall;
    }
}
