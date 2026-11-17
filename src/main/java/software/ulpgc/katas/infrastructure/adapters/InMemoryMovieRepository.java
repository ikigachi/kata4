package software.ulpgc.katas.infrastructure.adapters;

import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.domain.MovieEntry;
import software.ulpgc.katas.architecture.ports.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class InMemoryMovieRepository implements MovieRepository {
    private final List<Movie> movies;

    public InMemoryMovieRepository(List<Movie> catalog) {
        var snapshot = List.copyOf(catalog);
        if (snapshot.isEmpty() || Set.copyOf(snapshot).size() != snapshot.size()) {
            throw new IllegalArgumentException("The catalog must contain distinct movies.");
        }
        movies = new ArrayList<>(snapshot);
    }

    @Override
    public synchronized List<Movie> findAll() {
        return List.copyOf(movies);
    }

    @Override
    public synchronized MovieEntry add(Movie movie) {
        Objects.requireNonNull(movie);
        if (movies.contains(movie)) {
            throw new IllegalArgumentException("The movie is already in the catalog.");
        }
        movies.add(movie);
        return new MovieEntry(movies.size(), movie);
    }

    @Override
    public synchronized MovieEntry replace(int id, Movie movie) {
        Objects.requireNonNull(movie);
        if (id < 1) {
            throw new IllegalArgumentException("The movie ID must be a positive integer.");
        }
        if (id > movies.size()) {
            throw new NoSuchElementException("Movie " + id + " was not found.");
        }
        int existingIndex = movies.indexOf(movie);
        if (existingIndex >= 0 && existingIndex != id - 1) {
            throw new IllegalArgumentException("The movie is already in the catalog.");
        }
        movies.set(id - 1, movie);
        return new MovieEntry(id, movie);
    }
}
