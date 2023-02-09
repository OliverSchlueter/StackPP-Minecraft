package de.oliver.stackppmcplugin.stackppextensions.syscalls;

import de.oliver.stackpp.Program;
import de.oliver.stackpp.utils.ExceptionHelper;
import de.oliver.stackpp.virtualMachine.Machine;
import de.oliver.stackpp.virtualMachine.syscalls.Syscall;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendMessageToPlayerSyscall extends Syscall {

    public SendMessageToPlayerSyscall(int id, Machine machine) {
        super(id, machine);
    }

    /**
     *  'a' - pointer to player name (null-terminated)
     *  'b' - pointer to message (null-terminated)
     */
    @Override
    public void execute(Program program) {
        int playerNamePtr = machine.getIntegerRegister("a").getValue();
        String playerName = "";

        while(true){
            char c = (char) machine.getMemory().getAt(playerNamePtr);

            if(c == 0){
                break;
            }

            playerName += c;

            playerNamePtr++;
        }

        int messagePtr = machine.getIntegerRegister("b").getValue();
        String message = "";

        while(true){
            char c = (char) machine.getMemory().getAt(messagePtr);

            if(c == 0){
                break;
            }

            message += c;

            messagePtr++;
        }

        Player player = Bukkit.getPlayer(playerName);

        if(player == null || !player.isOnline()){
            ExceptionHelper.throwException(-1, "Could not find player with name: '" + playerName + "'");
            return;
        }

        player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
}
