package software.ulpgc.katas;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.Output;
import software.ulpgc.katas.architecture.usecases.SelectRandomBooksUseCase;
import software.ulpgc.katas.infrastructure.adapters.ContextInput;
import software.ulpgc.katas.infrastructure.adapters.GsonBookListSerializer;
import software.ulpgc.katas.infrastructure.adapters.JsonBookCatalog;

public class Main {
    static void main() {
        Javalin.create(Main::init).start(8080);
    }

    private static void init(JavalinConfig config) {
        var catalog = new JsonBookCatalog().load();
        UseCase useCase = new SelectRandomBooksUseCase(catalog, new GsonBookListSerializer());
        config.routes.get("/books", context -> books(context, useCase));
    }

    private static void books(Context context, UseCase useCase) {
        Input input = new ContextInput(context);
        Output output;
        try {
            output = useCase.execute(input);
        } catch (NumberFormatException e) {
            throw new BadRequestResponse("Provide an integer for the 'n' query parameter.");
        } catch (IllegalArgumentException e) {
            throw new BadRequestResponse(e.getMessage());
        }
        context.contentType("application/json").result(output.result());
    }

}
