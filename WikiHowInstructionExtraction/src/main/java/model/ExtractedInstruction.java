package model;

import org.json.simple.JSONArray;
import java.util.ArrayList;

public class ExtractedInstruction {
    private String instruction;
    private String howTo;
    private String method;
    private String description;
    private ArrayList<String> categories;

    public ExtractedInstruction(String instruction, String howTo, String method, String description) {
        this.instruction = instruction;
        this.howTo = howTo;
        this.method = method;
        this.description = description;
        categories = new ArrayList<>();
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addCategories(JSONArray newCats) {
        categories.addAll(newCats);
    }

    public ArrayList<String>  getCategories() {
        return categories;
    }
}
