package guru.qa;

public enum Items {
    PILLOW("подушка"),
    BLANKET("одеяло");

    private String title;

    Items(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
