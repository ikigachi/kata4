package software.ulpgc.katas.architecture.domain;

public record Book(String title, int year) {
    public Book {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("A book must have a title.");
        }
        if (year < 1) {
            throw new IllegalArgumentException("A book must have a positive release year.");
        }
    }
}
