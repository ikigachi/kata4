package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.domain.Videogame;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonVideogameCatalog {
    public List<Videogame> load() {
        var resource = JsonVideogameCatalog.class.getResourceAsStream("/videogame.json");
        if (resource == null) {
            throw new IllegalStateException("Could not find videogame.json.");
        }

        try (var reader = new InputStreamReader(resource, StandardCharsets.UTF_8)) {
            Videogame[] videogames = new Gson().fromJson(reader, Videogame[].class);
            return List.of(videogames);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read videogame.json.", e);
        }
    }
}