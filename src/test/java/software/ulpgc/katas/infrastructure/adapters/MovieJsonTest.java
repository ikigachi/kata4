package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import software.ulpgc.katas.architecture.domain.Movie;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieJsonTest {
    @Test
    void loadsOneHundredDistinctMoviesFromTheBundledCatalog() {
        List<Movie> movies = new JsonMovieCatalog().load();

        assertEquals(100, movies.size());
        assertEquals(100, Set.copyOf(movies).size());
        assertTrue(movies.contains(new Movie("Amélie", 2001)));
    }

    @Test
    void serializesTheMovieListWithTitlesAndReleaseYears() {
        List<Movie> movies = List.of(new Movie("Alien", 1979), new Movie("Amélie", 2001));

        String json = new GsonSeriesSerializer().serialize(movies);

        assertEquals(JsonParser.parseString("""
                [
                  {"title": "Alien", "year": 1979},
                  {"title": "Amélie", "year": 2001}
                ]
                """), JsonParser.parseString(json));
    }
}
