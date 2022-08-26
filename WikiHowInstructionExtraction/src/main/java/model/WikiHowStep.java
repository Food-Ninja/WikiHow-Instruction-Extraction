package model;

import java.util.ArrayList;

public class WikiHowStep {
    private final int orderNo;
    private final String image;
    private final String headline;
    private final String description;
    private final WikiHowMethod method;

    public WikiHowStep(int no, String headline, String desc, String img, WikiHowMethod method) {
        this.orderNo = no;
        this.headline = headline;
        this.description = desc;
        this.image = img;
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public String getHeadline() {
        return headline;
    }

    public ArrayList<String> getCategories() {
        return method.getArticle().getCategories();
    }

    public WikiHowMethod getMethod() {
        return method;
    }
}