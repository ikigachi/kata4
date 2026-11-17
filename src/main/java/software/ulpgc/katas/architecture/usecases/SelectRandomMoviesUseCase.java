package software.ulpgc.katas.architecture.usecases;

import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.MovieListSerializer;
import software.ulpgc.katas.architecture.ports.MovieRepository;
import software.ulpgc.katas.architecture.ports.Output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class SelectRandomMoviesUseCase implements UseCase {
    private final Supplier<List<Movie>> catalog;
    private final MovieListSerializer serializer;

    public SelectRandomMoviesUseCase(List<Movie> catalog, MovieListSerializer serializer) {
        List<Movie> snapshot = List.copyOf(catalog);
        if (snapshot.isEmpty() || Set.copyOf(snapshot).size() != snapshot.size()) {
            throw new IllegalArgumentException("The catalog must contain at least one movie and no duplicates.");
        }
        this.catalog = () -> snapshot;
        this.serializer = Objects.requireNonNull(serializer);
    }

    public SelectRandomMoviesUseCase(MovieRepository repository, MovieListSerializer serializer) {
        this.catalog = repository::findAll;
        this.serializer = Objects.requireNonNull(serializer);
    }

    @Override
    public Output execute(Input input) {
        List<Movie> movies = select(input.get("n").asInteger());
        return () -> serializer.serialize(movies);
    }

    private List<Movie> select(int count) {
        List<Movie> movies = catalog.get();
        if (count < 1 || count > movies.size()) {
            throw new IllegalArgumentException("Choose between 1 and " + movies.size() + " movies.");
        }
        List<Movie> shuffled = new ArrayList<>(movies);
        Collections.shuffle(shuffled);
        return List.copyOf(shuffled.subList(0, count));
    }
}
