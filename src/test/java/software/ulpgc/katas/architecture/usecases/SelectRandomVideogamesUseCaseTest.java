package software.ulpgc.katas.architecture.usecases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import software.ulpgc.katas.architecture.domain.Videogame;
import software.ulpgc.katas.architecture.ports.Input;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SelectRandomVideogamesUseCaseTest {
    private static final List<Videogame> CATALOG = List.of(
            new Videogame("Doom", 1993),
            new Videogame("Portal", 2007),
            new Videogame("Pokémon Red", 1996)
    );

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void selectsTheRequestedNumberOfDistinctVideogames(int count) {
        var useCase = new SelectRandomVideogamesUseCase(CATALOG, videogames -> {
            assertEquals(count, videogames.size());
            assertEquals(count, Set.copyOf(videogames).size());
            assertTrue(CATALOG.containsAll(videogames));
            return "serialized selection";
        });

        assertEquals("serialized selection", useCase.execute(input(count)).result());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4})
    void rejectsCountsOutsideTheCatalogSize(int count) {
        var useCase = new SelectRandomVideogamesUseCase(CATALOG, videogames -> "serialized selection");

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
