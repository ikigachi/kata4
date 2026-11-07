package software.ulpgc.katas.architecture.usecases;

import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.domain.Book;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.BookListSerializer;
import software.ulpgc.katas.architecture.ports.Output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SelectRandomBooksUseCase implements UseCase {
    private final List<Book> catalog;
    private final BookListSerializer serializer;

    public SelectRandomBooksUseCase(List<Book> catalog, BookListSerializer serializer) {
        this.catalog = List.copyOf(catalog);
        if (this.catalog.isEmpty() || Set.copyOf(this.catalog).size() != this.catalog.size()) {
            throw new IllegalArgumentException("The catalog must contain at least one book and no duplicates.");
        }
        this.serializer = Objects.requireNonNull(serializer);
    }

    @Override
    public Output execute(Input input) {
        List<Book> books = select(input.get("n").asInteger());
        return () -> serializer.serialize(books);
    }

    private List<Book> select(int count) {
        if (count < 1 || count > catalog.size()) {
            throw new IllegalArgumentException("Choose between 1 and " + catalog.size() + " books.");
        }
        List<Book> shuffled = new ArrayList<>(catalog);
        Collections.shuffle(shuffled);
        return List.copyOf(shuffled.subList(0, count));
    }
}
