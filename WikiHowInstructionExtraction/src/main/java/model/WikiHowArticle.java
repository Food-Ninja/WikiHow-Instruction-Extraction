package model;

import org.json.simple.JSONArray;
import java.util.ArrayList;

public class WikiHowArticle {
    private final String title;
    private final String description;

    private final String video;
    private final ArrayList<String> categories = new ArrayList<>();

    public WikiHowArticle(String title, String description, String video) {
        this.title = title;
        this.description = description;
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public String getVideo() { return video; }

    public String getDescription() {
        return description;
    }

    public void addCategories(JSONArray newCats) {
        categories.addAll(newCats);
    }

    public ArrayList<String>  getCategories() {
        return categories;
    }
}
