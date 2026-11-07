package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import software.ulpgc.katas.architecture.domain.Videogame;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VideogameJsonTest {
    @Test
    void loadsOneHundredDistinctVideogamesFromTheBundledCatalog() {
        List<Videogame> videogames = new JsonVideogameCatalog().load();

        assertEquals(100, videogames.size());
        assertEquals(100, Set.copyOf(videogames).size());
        assertTrue(videogames.contains(new Videogame("Pokémon Red", 1996)));
    }

    @Test
    void serializesTheVideogameListWithTitlesAndReleaseYears() {
        List<Videogame> videogames = List.of(new Videogame("Doom", 1993), new Videogame("Pokémon Red", 1996));

        String json = new GsonVideogameListSerializer().serialize(videogames);

        assertEquals(JsonParser.parseString("""
                [
                  {"title": "Doom", "year": 1993},
                  {"title": "Pokémon Red", "year": 1996}
                ]
                """), JsonParser.parseString(json));
    }
}
