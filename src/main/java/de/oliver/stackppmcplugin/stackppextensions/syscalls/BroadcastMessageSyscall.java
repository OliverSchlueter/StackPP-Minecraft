package de.oliver.stackppmcplugin.stackppextensions.syscalls;

import de.oliver.stackpp.Program;
import de.oliver.stackpp.virtualMachine.Machine;
import de.oliver.stackpp.virtualMachine.syscalls.Syscall;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

public class BroadcastMessageSyscall extends Syscall {

    public BroadcastMessageSyscall(int id, Machine machine) {
        super(id, machine);
    }

    /**
     *  'a' - ptr to message
     */
    @Override
    public void execute(Program program) {
        int messagePtr = machine.getIntegerRegister("a").getValue();
        String message = "";

        while(true){
            char c = (char) machine.getMemory().getAt(messagePtr);

            if(c == 0){
                break;
            }

            message += c;

            messagePtr++;
        }

        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(message));
    }
}
