package software.ulpgc.katas.infrastructure.adapters;

import org.junit.jupiter.api.Test;
import software.ulpgc.katas.architecture.domain.Movie;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryMovieRepositoryTest {
    private final Movie first = new Movie("Alien", 1979);
    private final Movie second = new Movie("The Matrix", 1999);
    private final InMemoryMovieRepository repository = new InMemoryMovieRepository(List.of(first));

    @Test
    void assignsAStableIdAndReplacesTheCreatedMovie() {
        var created = repository.add(second);
        assertEquals(2, created.id());
        assertEquals(second, created.movie());

        var replacement = new Movie("Arrival", 2016);
        assertEquals(2, repository.replace(created.id(), replacement).id());
        assertEquals(List.of(first, replacement), repository.findAll());
    }

    @Test
    void rejectedChangesPreserveTheCatalog() {
        repository.add(second);
        var before = repository.findAll();

        assertThrows(IllegalArgumentException.class, () -> repository.add(first));
        assertThrows(IllegalArgumentException.class, () -> repository.replace(2, first));
        assertThrows(NoSuchElementException.class, () -> repository.replace(3, first));
        assertEquals(before, repository.findAll());
    }

    @Test
    void exposesAnImmutableSnapshot() {
        var snapshot = repository.findAll();
        repository.add(second);

        assertEquals(List.of(first), snapshot);
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(second));
    }
}
