package de.oliver.stackppmcplugin.stackppextensions;

import de.oliver.stackpp.virtualMachine.Machine;
import de.oliver.stackppmcplugin.stackppextensions.syscalls.SendMessageToPlayerSyscall;

public class ExtensionRegistry {

    private static SendMessageToPlayerSyscall sendMessageToPlayerSyscall;

    public static void registerSyscalls(Machine machine){
        // add custom syscalls
        sendMessageToPlayerSyscall = new SendMessageToPlayerSyscall(100, machine);
        machine.getSyscalls().put(100, sendMessageToPlayerSyscall);

        // remove graphics syscalls
        machine.getSyscalls().remove(6);
        machine.getSyscalls().remove(7);
        machine.getSyscalls().remove(8);
        machine.getSyscalls().remove(9);

        // remove read line syscall
        machine.getSyscalls().remove(11);
    }

    public static SendMessageToPlayerSyscall getSendMessageToPlayerSyscall() {
        return sendMessageToPlayerSyscall;
    }
}
