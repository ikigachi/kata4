package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.architecture.domain.Videogame;

import java.util.List;

public interface VideogameListSerializer {
    String serialize(List<Videogame> videogames);
}
