package com.harleyoconnor.gamepieces.command;

import com.harleyoconnor.gamepieces.block.ChessBlock;
import com.harleyoconnor.gamepieces.block.ChessPiece;
import com.harleyoconnor.gamepieces.block.ChessPieceData;
import com.harleyoconnor.gamepieces.block.ChessTileEntity;
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

public class SetChessPieceCommand implements Command<CommandSource> {

    private static final SetChessPieceCommand INSTANCE = new SetChessPieceCommand();

    private static final EnumArgument<ChessPiece.Man> MAN_ARGUMENT = EnumArgument.enumArgument(ChessPiece.Man.class);
    private static final EnumArgument<ChessPiece.Color> COLOR_ARGUMENT = EnumArgument.enumArgument(ChessPiece.Color.class);

    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.gamepieces.setchessblock.failed"));

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("setchesspiece")
                .requires(sender -> sender.hasPermission(2))
                .then(Commands.argument("pos", ChessPiecePositionArgument.chessPiecePosition())
                        .then(Commands.argument("piece", MAN_ARGUMENT)
                                .then(Commands.argument("col", COLOR_ARGUMENT)
                                        .executes(INSTANCE))));
    }

    private SetChessPieceCommand() {
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Vector3d pos = Vec3Argument.getVec3(context, "pos");
        BlockPos blockPos = new BlockPos(pos);
        ServerWorld level = context.getSource().getLevel();
        BlockState oldState = level.getBlockState(blockPos);
        BlockState newState;
        if (oldState.getBlock() instanceof ChessBlock) {
            newState = oldState;
        } else {
            newState = Registry.CHESS_BLOCK.get().defaultBlockState();
            level.setBlock(blockPos, newState, 0);
        }
        TileEntity tileEntity = context.getSource().getLevel().getBlockEntity(blockPos);
        if (tileEntity instanceof ChessTileEntity) {
            setPiece(context, Math.abs(pos.x - ((int) pos.x)) - 0.5, Math.abs(pos.z - ((int) pos.z)) - 0.5, ((ChessTileEntity) tileEntity).getData());
            context.getSource().getLevel().sendBlockUpdated(blockPos, oldState, newState, 2);
            context.getSource().sendSuccess(new TranslationTextComponent("commands.gamepieces.setchessblock.success", pos.x, pos.y, pos.z), true);
            return SINGLE_SUCCESS;
        } else {
            throw ERROR_FAILED.create();
        }
    }

    private void setPiece(CommandContext<CommandSource> context, double subX, double subZ, ChessPieceData pieces) {
        int square = ((int) (((subX / -0.25) + 1) / 2) & 1) | ((int) ((subZ / 0.25) + 1) & 2);
        pieces.setPiece(square, new ChessPiece(
                context.getArgument("piece", ChessPiece.Man.class),
                context.getArgument("col", ChessPiece.Color.class)
        ));
    }

}
