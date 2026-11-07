package software.ulpgc.katas.architecture.usecases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import software.ulpgc.katas.architecture.domain.Book;
import software.ulpgc.katas.architecture.ports.Input;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SelectRandomBooksUseCaseTest {
    private static final List<Book> CATALOG = List.of(
            new Book("Frankenstein", 1818),
            new Book("Pride and Prejudice", 1813),
            new Book("Les Misérables", 1862)
    );

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void selectsTheRequestedNumberOfDistinctBooks(int count) {
        var useCase = new SelectRandomBooksUseCase(CATALOG, books -> {
            assertEquals(count, books.size());
            assertEquals(count, Set.copyOf(books).size());
            assertTrue(CATALOG.containsAll(books));
            return "serialized selection";
        });

        assertEquals("serialized selection", useCase.execute(input(count)).result());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4})
    void rejectsCountsOutsideTheCatalogSize(int count) {
        var useCase = new SelectRandomBooksUseCase(CATALOG, books -> "serialized selection");

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
