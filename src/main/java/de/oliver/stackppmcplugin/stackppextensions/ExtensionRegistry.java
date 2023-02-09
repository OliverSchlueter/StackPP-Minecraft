package de.oliver.stackppmcplugin.stackppextensions;

import de.oliver.stackpp.virtualMachine.Machine;
import de.oliver.stackppmcplugin.stackppextensions.syscalls.SendMessageToPlayerSyscall;

public class ExtensionRegistry {

    private static SendMessageToPlayerSyscall sendMessageToPlayerSyscall;

    public static void registerSyscalls(Machine machine){
        sendMessageToPlayerSyscall = new SendMessageToPlayerSyscall(100, machine);
        machine.getSyscalls().put(100, sendMessageToPlayerSyscall);
    }

    public static SendMessageToPlayerSyscall getSendMessageToPlayerSyscall() {
        return sendMessageToPlayerSyscall;
    }
}
