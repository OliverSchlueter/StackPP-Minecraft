package de.oliver.stackppmcplugin.commands;

import de.oliver.stackpp.Lexer;
import de.oliver.stackpp.Parser;
import de.oliver.stackpp.Program;
import de.oliver.stackppmcplugin.StackppMcPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

public class StackppCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player p)){
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Only players can execute this command</red>"));
            return false;
        }

        ItemStack book = p.getInventory().getItemInMainHand();

        if(!(book.getItemMeta() instanceof BookMeta bookMeta)){
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please hold a book in your hand</red>"));
            return false;
        }

        // add newlines
        String content = String.join("\n", bookMeta.getPages());
        content = content.replace("\n", "\r\n");

        // lex and parse program
        Lexer lexer = new Lexer(content);
        Parser parser = new Parser(lexer.lex());
        Program program = parser.parse();

        // change to custom output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // run program
        StackppMcPlugin.getPlugin().getMachine().runProgram(program);

        String output = out.toString();

        for (String s : output.split("\r\n")) {
            p.sendMessage(MiniMessage.miniMessage().deserialize("<green>" + s + "</green>"));
        }

        // set standard output back
        System.setOut(System.out);

        return false;
    }

}
