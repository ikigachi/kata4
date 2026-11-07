package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import software.ulpgc.katas.architecture.domain.Book;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookJsonTest {
    @Test
    void loadsOneHundredDistinctBooksFromTheBundledCatalog() {
        List<Book> books = new JsonBookCatalog().load();

        assertEquals(100, books.size());
        assertEquals(100, Set.copyOf(books).size());
        assertTrue(books.contains(new Book("Les Misérables", 1862)));
    }

    @Test
    void serializesTheBookListWithTitlesAndReleaseYears() {
        List<Book> books = List.of(new Book("Frankenstein", 1818), new Book("Les Misérables", 1862));

        String json = new GsonBookListSerializer().serialize(books);

        assertEquals(JsonParser.parseString("""
                [
                  {"title": "Frankenstein", "year": 1818},
                  {"title": "Les Misérables", "year": 1862}
                ]
                """), JsonParser.parseString(json));
    }
}
