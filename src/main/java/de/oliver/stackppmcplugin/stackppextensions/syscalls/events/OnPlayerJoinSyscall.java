package de.oliver.stackppmcplugin.stackppextensions.syscalls.events;

import de.oliver.stackpp.Program;
import de.oliver.stackpp.operations.impl.block.FunctionOperation;
import de.oliver.stackpp.utils.ExceptionHelper;
import de.oliver.stackpp.virtualMachine.Machine;
import de.oliver.stackpp.virtualMachine.syscalls.Syscall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OnPlayerJoinSyscall extends Syscall {

    public Set<FunctionOperation> functions;

    public OnPlayerJoinSyscall(int id, Machine machine) {
        super(id, machine);
        functions = new HashSet<>();
    }

    /**
     *  'a' - ptr to function name
     */
    @Override
    public void execute(Program program) {
        int funcNamePtr = machine.getIntegerRegister("a").getValue();

        String funcName = "";

        while(true){
            char c = (char) machine.getMemory().getAt(funcNamePtr);

            if(c == 0){
                break;
            }

            funcName += c;

            funcNamePtr++;
        }

        FunctionOperation function = program.getFunctions().get(funcName);

        if(function == null){
            ExceptionHelper.throwException(-1, "Could not find function: '" + funcName + "'");
            return;
        }

        functions.remove(function);
        functions.add(function);
    }

    public Set<FunctionOperation> getFunctions() {
        return functions;
    }
}
