package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.domain.Book;
import software.ulpgc.katas.architecture.ports.BookListSerializer;

import java.util.List;

public class GsonBookListSerializer implements BookListSerializer {
    @Override
    public String serialize(List<Book> books) {
        return new Gson().toJson(books);
    }
}