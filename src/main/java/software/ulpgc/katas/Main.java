package software.ulpgc.katas;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.Output;
import software.ulpgc.katas.architecture.usecases.SelectRandomMoviesUseCase;
import software.ulpgc.katas.infrastructure.adapters.ContextInput;
import software.ulpgc.katas.infrastructure.adapters.GsonSeriesSerializer;
import software.ulpgc.katas.infrastructure.adapters.JsonMovieCatalog;

public class Main {
    static void main() {
        Javalin.create(Main::init).start(8080);
    }

    private static void init(JavalinConfig config) {
        var catalog = new JsonMovieCatalog().load();
        UseCase useCase = new SelectRandomMoviesUseCase(catalog, new GsonSeriesSerializer());
        config.routes.get("/movies", context -> movies(context, useCase));
    }

    private static void movies(Context context, UseCase useCase) {
        Input input = new ContextInput(context);
        Output output = useCase.execute(input);
        context.contentType("application/json").result(output.result());
    }

}
