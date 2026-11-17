package software.ulpgc.katas.architecture.usecases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.ports.Input;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SelectRandomMoviesUseCaseTest {
    private static final List<Movie> CATALOG = List.of(
            new Movie("Alien", 1979),
            new Movie("The Matrix", 1999),
            new Movie("Amélie", 2001)
    );

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void selectsTheRequestedNumberOfDistinctMovies(int count) {
        var useCase = new SelectRandomMoviesUseCase(CATALOG, movies -> {
            assertEquals(count, movies.size());
            assertEquals(count, Set.copyOf(movies).size());
            assertTrue(CATALOG.containsAll(movies));
            return "serialized selection";
        });

        assertEquals("serialized selection", useCase.execute(input(count)).result());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4})
    void rejectsCountsOutsideTheCatalogSize(int count) {
        var useCase = new SelectRandomMoviesUseCase(CATALOG, movies -> "serialized selection");

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(input(count)));
    }

    private static Input input(int count) {
        return name -> {
            assertEquals("n", name);
            return new Input.Parameter() {
                @Override
                public String asString() {
                    return Integer.toString(count);
                }

                @Override
                public int asInteger() {
                    return count;
                }
            };
        };
    }
}
