package com.harleyoconnor.gamepieces.command;

import com.harleyoconnor.gamepieces.block.*;
import com.harleyoconnor.gamepieces.setup.Registry;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.command.EnumArgument;

public class SetCheckersPieceCommand implements Command<CommandSourceStack> {

    private static final SetCheckersPieceCommand INSTANCE = new SetCheckersPieceCommand();

    private static final EnumArgument<CheckersPiece.Type> TYPE_ARGUMENT = EnumArgument.enumArgument(CheckersPiece.Type.class);
    private static final EnumArgument<PieceColor> COLOR_ARGUMENT = EnumArgument.enumArgument(PieceColor.class);

    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.gamepieces.setcheckerspiece.failed"));

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("setcheckerspiece")
                .requires(sender -> sender.hasPermission(2))
                .then(Commands.argument("pos", ChessPiecePositionArgument.chessPiecePosition())
                        .then(Commands.argument("piece", TYPE_ARGUMENT)
                                .then(Commands.argument("col", COLOR_ARGUMENT).executes(INSTANCE))));
    }

    private SetCheckersPieceCommand() {
    }


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Vec3 pos = Vec3Argument.getVec3(context, "pos");
        BlockPos blockPos = new BlockPos(pos);
        ServerLevel level = context.getSource().getLevel();
        BlockState oldState = level.getBlockState(blockPos);
        BlockState newState;
        if (oldState.getBlock() instanceof PiecesBlock.Chess) {
            newState = oldState;
        } else {
            newState = Registry.CHECKERS_BLOCK.get().defaultBlockState();
            level.setBlock(blockPos, newState, 0);
        }
        BlockEntity blockEntity = context.getSource().getLevel().getBlockEntity(blockPos);
        if (blockEntity instanceof PiecesBlockEntity.Checkers) {
            setPiece(context, Math.abs(pos.x - ((int) pos.x)) - 0.5, Math.abs(pos.z - ((int) pos.z)) - 0.5, ((PiecesBlockEntity.Checkers) blockEntity).getPieces());
            context.getSource().getLevel().sendBlockUpdated(blockPos, oldState, newState, 2);
            context.getSource().sendSuccess(new TranslatableComponent("commands.gamepieces.setcheckerspiece.success", pos.x, pos.y, pos.z), true);
            return SINGLE_SUCCESS;
        } else {
            throw ERROR_FAILED.create();
        }
    }

    private void setPiece(CommandContext<CommandSourceStack> context, double subX, double subZ, PieceData<CheckersPiece> pieces) {
        int square = ((int) (((subX / -0.25) + 1) / 2) & 1) | ((int) ((subZ / 0.25) + 1) & 2);
        pieces.setPiece(square, new CheckersPiece(
                context.getArgument("piece", CheckersPiece.Type.class),
                context.getArgument("col", PieceColor.class)
        ));
    }

}
