package com.harleyoconnor.gamepieces.command;

import com.harleyoconnor.gamepieces.GamePieces;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class GamePiecesCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> command = dispatcher.register(
                Commands.literal(GamePieces.MOD_ID).then(SetChessPieceCommand.register(dispatcher))
        );

        dispatcher.register(Commands.literal("gp").redirect(command));
    }

}
