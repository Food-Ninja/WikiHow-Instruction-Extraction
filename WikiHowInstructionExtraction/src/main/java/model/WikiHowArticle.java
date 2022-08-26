package model;

import org.json.simple.JSONArray;
import java.util.ArrayList;

public class WikiHowArticle {
    private final String title;
    private final String description;
    private final ArrayList<String> categories = new ArrayList<>();

    public WikiHowArticle(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void addCategories(JSONArray newCats) {
        categories.addAll(newCats);
    }

    public ArrayList<String>  getCategories() {
        return categories;
    }
}
