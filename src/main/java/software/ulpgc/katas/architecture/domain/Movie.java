package software.ulpgc.katas.architecture.domain;

public record Movie(String title, int year) {
    public Movie {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("A movie must have a title.");
        }
        if (year < 1) {
            throw new IllegalArgumentException("A movie must have a positive release year.");
        }
    }
}
