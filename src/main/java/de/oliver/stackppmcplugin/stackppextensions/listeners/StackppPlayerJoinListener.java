package de.oliver.stackppmcplugin.stackppextensions.listeners;

import de.oliver.stackpp.operations.Operation;
import de.oliver.stackpp.operations.impl.block.FunctionOperation;
import de.oliver.stackppmcplugin.stackppextensions.ExtensionRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class StackppPlayerJoinListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onStackppPlayerJoin(PlayerJoinEvent event){
        for (FunctionOperation function : ExtensionRegistry.getOnPlayerJoinSyscall().getFunctions()) {
            for (Operation operation : function.getOperations()) {
                operation.execute();
            }
        }
    }

}
