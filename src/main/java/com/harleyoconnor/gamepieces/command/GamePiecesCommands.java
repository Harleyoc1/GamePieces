package com.harleyoconnor.gamepieces.command;

import com.harleyoconnor.gamepieces.GamePieces;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class GamePiecesCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> command = dispatcher.register(
                Commands.literal(GamePieces.MOD_ID)
                        .then(SetChessPieceCommand.register(dispatcher))
                        .then(SetCheckersPieceCommand.register(dispatcher))
        );

        dispatcher.register(Commands.literal("gp").redirect(command));
    }

}
