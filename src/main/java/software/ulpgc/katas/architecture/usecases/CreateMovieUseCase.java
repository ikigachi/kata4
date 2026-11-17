package software.ulpgc.katas.architecture.usecases;

import software.ulpgc.katas.architecture.UseCase;
import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.MovieEntrySerializer;
import software.ulpgc.katas.architecture.ports.MovieRepository;
import software.ulpgc.katas.architecture.ports.Output;

public class CreateMovieUseCase implements UseCase {
    private final MovieRepository repository;
    private final MovieEntrySerializer serializer;

    public CreateMovieUseCase(MovieRepository repository, MovieEntrySerializer serializer) {
        this.repository = repository;
        this.serializer = serializer;
    }

    @Override
    public Output execute(Input input) {
        var movie = new Movie(input.get("title").asString(), input.get("year").asInteger());
        var created = repository.add(movie);
        return () -> serializer.serialize(created);
    }
}
