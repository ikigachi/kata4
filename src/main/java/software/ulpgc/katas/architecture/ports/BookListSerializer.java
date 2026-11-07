package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.architecture.domain.Book;

import java.util.List;

public interface BookListSerializer {
    String serialize(List<Book> books);
}
