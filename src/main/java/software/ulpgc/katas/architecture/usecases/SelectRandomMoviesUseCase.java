package software.ulpgc.katas.architecture.usecases;

import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.MovieListSerializer;
import software.ulpgc.katas.architecture.ports.Output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SelectRandomMoviesUseCase implements UseCase {
    private final List<Movie> catalog;
    private final MovieListSerializer serializer;

    public SelectRandomMoviesUseCase(List<Movie> catalog, MovieListSerializer serializer) {
        this.catalog = List.copyOf(catalog);
        if (this.catalog.isEmpty() || Set.copyOf(this.catalog).size() != this.catalog.size()) {
            throw new IllegalArgumentException("The catalog must contain at least one movie and no duplicates.");
        }
        this.serializer = Objects.requireNonNull(serializer);
    }

    @Override
    public Output execute(Input input) {
        List<Movie> movies = select(input.get("n").asInteger());
        return () -> serializer.serialize(movies);
    }

    private List<Movie> select(int count) {
        if (count < 1 || count > catalog.size()) {
            throw new IllegalArgumentException("Choose between 1 and " + catalog.size() + " movies.");
        }
        List<Movie> shuffled = new ArrayList<>(catalog);
        Collections.shuffle(shuffled);
        return List.copyOf(shuffled.subList(0, count));
    }
}
