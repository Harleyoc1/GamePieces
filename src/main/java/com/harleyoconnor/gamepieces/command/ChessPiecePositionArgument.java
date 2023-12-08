package com.harleyoconnor.gamepieces.command;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.SharedSuggestionProvider.TextCoordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;

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
        Collection<TextCoordinates> absoluteCoordinates = ((SharedSuggestionProvider)context.getSource()).getAbsoluteCoordinates();
        List<SharedSuggestionProvider.TextCoordinates> coordinates = new LinkedList<>();
        for (SharedSuggestionProvider.TextCoordinates coordinate : absoluteCoordinates) {
            processCoordinates(coordinate, coordinates);
        }
        return SharedSuggestionProvider.suggest(coordinates.stream().map(coords -> coords.x + " " + coords.y + " " + coords.z).collect(Collectors.toList()), builder);
    }

    private void processCoordinates(SharedSuggestionProvider.TextCoordinates coordinate, List<SharedSuggestionProvider.TextCoordinates> coordinates) {
        try {
        	double x = Math.floor(Double.parseDouble(coordinate.x) * 2);
        	double y = Math.floor(Double.parseDouble(coordinate.y));
        	double z = Math.floor(Double.parseDouble(coordinate.z) * 2);
        	x = (x / 2) + 0.25;
        	z = (z / 2) + 0.25;
            coordinates.add(new SharedSuggestionProvider.TextCoordinates(Double.toString(x), Double.toString(y), Double.toString(z)));
        } catch (NumberFormatException e) {
            coordinates.add(coordinate);
        }
    }
    
}
