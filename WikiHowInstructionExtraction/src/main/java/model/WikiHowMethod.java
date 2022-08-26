package model;

public class WikiHowMethod {
    private final int orderNo;
    private final String name;

    private final WikiHowArticle article;

    public WikiHowMethod(int no, String name, WikiHowArticle article) {
        this.orderNo = no;
        this.name = name;
        this.article = article;
    }

    public WikiHowArticle getArticle() {
        return article;
    }

    public String getName() {
        return name;
    }
}