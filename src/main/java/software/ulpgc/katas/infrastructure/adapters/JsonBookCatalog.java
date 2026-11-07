package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.domain.Book;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonBookCatalog {
    public List<Book> load() {
        var resource = JsonBookCatalog.class.getResourceAsStream("/book.json");
        if (resource == null) {
            throw new IllegalStateException("Could not find book.json.");
        }

        try (var reader = new InputStreamReader(resource, StandardCharsets.UTF_8)) {
            Book[] books = new Gson().fromJson(reader, Book[].class);
            return List.of(books);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read book.json.", e);
        }
    }
}