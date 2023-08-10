package com.harleyoconnor.gamepieces.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ChessPiecePositionArgument extends Vec3Argument {

    public ChessPiecePositionArgument(boolean centerCorrect) {
        super(centerCorrect);
    }

    public static ChessPiecePositionArgument chessPiecePosition() {
        return new ChessPiecePositionArgument(true);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (!(context.getSource() instanceof SharedSuggestionProvider)) {
            return Suggestions.empty();
        }
        Collection<SharedSuggestionProvider.TextCoordinates> relevantCoordinates = ((SharedSuggestionProvider) context.getSource()).getRelevantCoordinates();
        List<SharedSuggestionProvider.TextCoordinates> coordinates = new LinkedList<>();
        for (SharedSuggestionProvider.TextCoordinates coordinate : relevantCoordinates) {
            processCoordinates(coordinate, coordinates);
        }

        return SharedSuggestionProvider.suggest(coordinates.stream().map(coords -> coords.x + " " + coords.y + " " + coords.z).collect(Collectors.toList()), builder);
    }

    private void processCoordinates(SharedSuggestionProvider.TextCoordinates coordinate, List<SharedSuggestionProvider.TextCoordinates> coordinates) {
        try {
            int x = Integer.parseInt(coordinate.x);
            int z = Integer.parseInt(coordinate.z);
            coordinates.add(new SharedSuggestionProvider.TextCoordinates(Double.toString(x + 0.25D), coordinate.y, Double.toString(z + 0.25D)));
            coordinates.add(new SharedSuggestionProvider.TextCoordinates(Double.toString(x + 0.25D), coordinate.y, Double.toString(z + 0.75D)));
            coordinates.add(new SharedSuggestionProvider.TextCoordinates(Double.toString(x + 0.75D), coordinate.y, Double.toString(z + 0.25D)));
            coordinates.add(new SharedSuggestionProvider.TextCoordinates(Double.toString(x + 0.75D), coordinate.y, Double.toString(z + 0.75D)));
        } catch (NumberFormatException e) {
            coordinates.add(coordinate);
        }
    }

}
