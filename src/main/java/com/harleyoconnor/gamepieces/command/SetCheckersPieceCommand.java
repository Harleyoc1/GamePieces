package com.harleyoconnor.gamepieces.command;

import com.harleyoconnor.gamepieces.block.*;
import com.harleyoconnor.gamepieces.setup.Registry;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.server.command.EnumArgument;

public class SetCheckersPieceCommand implements Command<CommandSource> {

    private static final SetCheckersPieceCommand INSTANCE = new SetCheckersPieceCommand();

    private static final EnumArgument<CheckersPiece.Type> TYPE_ARGUMENT = EnumArgument.enumArgument(CheckersPiece.Type.class);
    private static final EnumArgument<PieceColor> COLOR_ARGUMENT = EnumArgument.enumArgument(PieceColor.class);

    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.gamepieces.setcheckerspiece.failed"));

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("setcheckerspiece")
                .requires(sender -> sender.hasPermission(2))
                .then(Commands.argument("pos", ChessPiecePositionArgument.chessPiecePosition())
                        .then(Commands.argument("piece", TYPE_ARGUMENT)
                                .then(Commands.argument("col", COLOR_ARGUMENT).executes(INSTANCE))));
    }

    private SetCheckersPieceCommand() {
    }


    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Vector3d pos = Vec3Argument.getVec3(context, "pos");
        BlockPos blockPos = new BlockPos(pos);
        ServerWorld level = context.getSource().getLevel();
        BlockState oldState = level.getBlockState(blockPos);
        BlockState newState;
        if (oldState.getBlock() instanceof PiecesBlock.Chess) {
            newState = oldState;
        } else {
            newState = Registry.CHECKERS_BLOCK.get().defaultBlockState();
            level.setBlock(blockPos, newState, 0);
        }
        TileEntity tileEntity = context.getSource().getLevel().getBlockEntity(blockPos);
        if (tileEntity instanceof PiecesTileEntity.Checkers) {
            setPiece(context, Math.abs(pos.x - ((int) pos.x)) - 0.5, Math.abs(pos.z - ((int) pos.z)) - 0.5, ((PiecesTileEntity.Checkers) tileEntity).getPieces());
            context.getSource().getLevel().sendBlockUpdated(blockPos, oldState, newState, 2);
            context.getSource().sendSuccess(new TranslationTextComponent("commands.gamepieces.setcheckerspiece.success", pos.x, pos.y, pos.z), true);
            return SINGLE_SUCCESS;
        } else {
            throw ERROR_FAILED.create();
        }
    }

    private void setPiece(CommandContext<CommandSource> context, double subX, double subZ, PieceData<CheckersPiece> pieces) {
        int square = ((int) (((subX / -0.25) + 1) / 2) & 1) | ((int) ((subZ / 0.25) + 1) & 2);
        pieces.setPiece(square, new CheckersPiece(
                context.getArgument("piece", CheckersPiece.Type.class),
                context.getArgument("col", PieceColor.class)
        ));
    }

}
