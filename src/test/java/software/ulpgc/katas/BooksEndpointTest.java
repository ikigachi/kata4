package software.ulpgc.katas;

import com.google.gson.Gson;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import software.ulpgc.katas.architecture.domain.Book;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BooksEndpointTest {
    private static Javalin server;
    private static HttpClient client;

    @BeforeAll
    static void startServer() {
        server = Javalin.create(Main::init).start("127.0.0.1", 0);
        client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();
    }

    @AfterAll
    static void stopServer() {
        try {
            if (client != null) {
                client.close();
            }
        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100})
    void returnsTheRequestedNumberOfDistinctBooks(int count) throws IOException, InterruptedException {
        var response = get("?n=" + count);

        assertEquals(200, response.statusCode());
        assertTrue(response.headers().firstValue("Content-Type").orElse("").startsWith("application/json"));
        List<Book> books = List.of(new Gson().fromJson(response.body(), Book[].class));
        assertEquals(count, books.size());
        assertEquals(count, Set.copyOf(books).size());
    }

    @ParameterizedTest
    @CsvSource({
            "'', Provide an integer",
            "?n=, Provide an integer",
            "?n=abc, Provide an integer",
            "?n=1.5, Provide an integer",
            "?n=2147483648, Provide an integer",
            "?n=-1, Choose between 1 and 100 books",
            "?n=0, Choose between 1 and 100 books",
            "?n=101, Choose between 1 and 100 books"
    })
    void returnsBadRequestWithAnExplanation(String query, String message) throws IOException, InterruptedException {
        var response = get(query);

        assertEquals(400, response.statusCode());
        assertTrue(response.body().contains(message), response.body());
        assertEquals(200, get("?n=5").statusCode());
    }

    private static HttpResponse<String> get(String query) throws IOException, InterruptedException {
        var uri = URI.create("http://127.0.0.1:" + server.port() + "/books" + query);
        var request = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(3)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
