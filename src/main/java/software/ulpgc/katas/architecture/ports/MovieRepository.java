package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.domain.MovieEntry;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAll();
    MovieEntry add(Movie movie);
    MovieEntry replace(int id, Movie movie);
}
