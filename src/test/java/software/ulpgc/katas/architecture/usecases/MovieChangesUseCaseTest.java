package software.ulpgc.katas.architecture.usecases;

import org.junit.jupiter.api.Test;
import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.infrastructure.adapters.InMemoryMovieRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieChangesUseCaseTest {
    private final InMemoryMovieRepository repository =
            new InMemoryMovieRepository(List.of(new Movie("Alien", 1979)));

    @Test
    void createsAndReplacesThroughInputAndOutput() {
        var create = new CreateMovieUseCase(repository, entry -> Integer.toString(entry.id()));
        assertEquals("2", create.execute(input("2", "Arrival", "2016")).result());

        var replace = new ReplaceMovieUseCase(repository, entry -> entry.movie().title());
        assertEquals("Dune", replace.execute(input("2", "Dune", "2021")).result());
        assertEquals(new Movie("Dune", 2021), repository.findAll().get(1));
    }

    @Test
    void invalidCreationPreservesTheCatalog() {
        var before = repository.findAll();
        var create = new CreateMovieUseCase(repository, entry -> entry.movie().title());

        assertThrows(IllegalArgumentException.class, () -> create.execute(input("1", "", "2020")));
        assertThrows(IllegalArgumentException.class, () -> create.execute(input("1", "Arrival", "0")));
        assertEquals(before, repository.findAll());
    }

    @Test
    void missingReplacementPreservesTheCatalog() {
        var before = repository.findAll();
        var replace = new ReplaceMovieUseCase(repository, entry -> entry.movie().title());

        assertThrows(NoSuchElementException.class, () -> replace.execute(input("999", "Arrival", "2016")));
        assertEquals(before, repository.findAll());
    }

    private static Input input(String id, String title, String year) {
        var values = Map.of("id", id, "title", title, "year", year);
        return name -> new Input.Parameter() {
            @Override
            public String asString() {
                return values.get(name);
            }

            @Override
            public int asInteger() {
                return Integer.parseInt(asString());
            }
        };
    }
}
