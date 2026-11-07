package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.domain.Movie;
import software.ulpgc.katas.architecture.ports.MovieListSerializer;

import java.util.List;

public class GsonSeriesSerializer implements MovieListSerializer {
    @Override
    public String serialize(List<Movie> movies) {
        return new Gson().toJson(movies);
    }
}