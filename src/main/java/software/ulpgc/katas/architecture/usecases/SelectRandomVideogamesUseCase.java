package software.ulpgc.katas.architecture.usecases;

import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.domain.Videogame;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.VideogameListSerializer;
import software.ulpgc.katas.architecture.ports.Output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SelectRandomVideogamesUseCase implements UseCase {
    private final List<Videogame> catalog;
    private final VideogameListSerializer serializer;

    public SelectRandomVideogamesUseCase(List<Videogame> catalog, VideogameListSerializer serializer) {
        this.catalog = List.copyOf(catalog);
        if (this.catalog.isEmpty() || Set.copyOf(this.catalog).size() != this.catalog.size()) {
            throw new IllegalArgumentException("The catalog must contain at least one videogame and no duplicates.");
        }
        this.serializer = Objects.requireNonNull(serializer);
    }

    @Override
    public Output execute(Input input) {
        List<Videogame> videogames = select(input.get("n").asInteger());
        return () -> serializer.serialize(videogames);
    }

    private List<Videogame> select(int count) {
        if (count < 1 || count > catalog.size()) {
            throw new IllegalArgumentException("Choose between 1 and " + catalog.size() + " videogames.");
        }
        List<Videogame> shuffled = new ArrayList<>(catalog);
        Collections.shuffle(shuffled);
        return List.copyOf(shuffled.subList(0, count));
    }
}
