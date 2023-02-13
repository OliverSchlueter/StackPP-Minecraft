package de.oliver.stackppmcplugin.commands;

import de.oliver.stackpp.Lexer;
import de.oliver.stackpp.Parser;
import de.oliver.stackpp.Program;
import de.oliver.stackppmcplugin.StackppMcPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class StackppCMD implements CommandExecutor, TabExecutor {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length == 1){
            return Arrays.asList("book", "file");
        }

        if(args.length == 2 && args[0].equalsIgnoreCase("file")){
            return Arrays.asList("plugins/stackpp-mc-plugin/your_script.spp");
        }

        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player p)){
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Only players can execute this command</red>"));
            return false;
        }

        if(args.length < 1){
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>/stackpp <book|file> [file path]</red>"));
            return false;
        }

        String content = "";

        switch (args[0].toLowerCase()){
            case "book" -> {
                ItemStack book = p.getInventory().getItemInMainHand();

                if(!(book.getItemMeta() instanceof BookMeta bookMeta)){
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please hold a book in your hand</red>"));
                    return false;
                }

                content = String.join("\n", bookMeta.getPages());
                content = content.replace("\n", "\r\n");
            }

            case "file" -> {
                if(args.length < 2){
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Please specify a file path</red>"));
                    return false;
                }

                String path = args[1];
                File file = new File(path);

                if(!file.exists()){
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>This file does not exist</red>"));
                    return false;
                }

                try {
                    content = Files.readString(Path.of(file.getAbsolutePath()));
                } catch (IOException e) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Something went wrong while reading the file</red>"));
                    throw new RuntimeException(e);
                }

            }

            default -> {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Required: 'book' or 'file', got: '" + args[0] + "'</red>"));
                return false;
            }
        }


        // lex and parse program
        Lexer lexer = new Lexer(content);
        Parser parser = new Parser(lexer.lex());
        Program program = parser.parse();

        // change to custom output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));

        // run program
        StackppMcPlugin.getPlugin().getMachine().runProgram(program);

        String output = out.toString();

        for (String s : output.split("\r\n")) {
            p.sendMessage(MiniMessage.miniMessage().deserialize("<green>" + s + "</green>"));
        }

        // set standard output back
        System.setOut(System.out);
        System.setErr(System.err);

        return false;
    }

}
