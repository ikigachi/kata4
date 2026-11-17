package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.architecture.domain.Movie;

import java.util.List;

public interface MovieListSerializer {
    String serialize(List<Movie> movies);
}
