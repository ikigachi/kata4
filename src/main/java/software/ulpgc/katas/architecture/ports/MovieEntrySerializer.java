package software.ulpgc.katas.architecture.ports;

import software.ulpgc.katas.architecture.domain.MovieEntry;

public interface MovieEntrySerializer {
    String serialize(MovieEntry entry);
}
