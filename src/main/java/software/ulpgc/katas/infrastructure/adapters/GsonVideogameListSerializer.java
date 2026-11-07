package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.domain.Videogame;
import software.ulpgc.katas.architecture.ports.VideogameListSerializer;

import java.util.List;

public class GsonVideogameListSerializer implements VideogameListSerializer {
    @Override
    public String serialize(List<Videogame> videogames) {
        return new Gson().toJson(videogames);
    }
}