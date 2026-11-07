package software.ulpgc.katas.architecture.domain;

public record Videogame(String title, int year) {
    public Videogame {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("A videogame must have a title.");
        }
        if (year < 1) {
            throw new IllegalArgumentException("A videogame must have a positive release year.");
        }
    }
}
