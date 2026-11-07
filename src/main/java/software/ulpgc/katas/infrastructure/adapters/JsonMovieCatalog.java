package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.domain.Movie;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonMovieCatalog {
    public List<Movie> load() {
        var resource = JsonMovieCatalog.class.getResourceAsStream("/movie.json");
        if (resource == null) {
            throw new IllegalStateException("Could not find movie.json.");
        }

        try (var reader = new InputStreamReader(resource, StandardCharsets.UTF_8)) {
            Movie[] movies = new Gson().fromJson(reader, Movie[].class);
            return List.of(movies);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read movie.json.", e);
        }
    }
}