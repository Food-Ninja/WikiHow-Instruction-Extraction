package controller;

import model.WikiHowArticle;
import model.WikiHowMethod;
import model.WikiHowStep;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.GlobalSettings;
import utils.OccurrenceChecker;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class JSONFileReader {

    public ArrayList<WikiHowStep> extractStepsFromFiles() {
        String dataDirectory = "./data/";
        File dir = new File(dataDirectory);
        JSONParser parser = new JSONParser();
        ArrayList<WikiHowStep> steps = new ArrayList<>();

        int progressCounter = 0;
        int tenPercent = Objects.requireNonNull(dir.listFiles()).length / 10;
        int percentCounter = 1;

        for(File json : Objects.requireNonNull(dir.listFiles())) {
            try {
                Object obj = parser.parse(new FileReader(json));
                ArrayList<WikiHowStep> extractedFromCurrentFile = extractStepsFromGivenFile((JSONObject) obj);

                if(progressCounter >= tenPercent) {
                    System.out.println(percentCounter + "0% completed");
                    progressCounter = 0;
                    percentCounter++;
                } else {
                    progressCounter++;
                }

                if (extractedFromCurrentFile.isEmpty()) {
                    continue;
                }
                steps.addAll(extractedFromCurrentFile);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(percentCounter + "0% completed\n");
        return steps;
    }

    private ArrayList<WikiHowStep> extractStepsFromGivenFile(JSONObject file) {
        if(checkForFileSkip(file)) {
            return new ArrayList<>();
        }

        ArrayList<WikiHowStep> stepsInFile = new ArrayList<>();
        JSONArray categories = (JSONArray) file.get("category_hierarchy");
        String articleTitle = (String) file.get("title");
        String articleDescription = (String) file.get("title_description");
        WikiHowArticle article = new WikiHowArticle(articleTitle, articleDescription);
        article.addCategories(categories);

        JSONArray methods = (JSONArray) file.get("methods");
        for(int m = 0; m < methods.size(); m++) {
            JSONObject methodObj = (JSONObject) methods.get(m);
            String methodName = (String) methodObj.get("name");
            WikiHowMethod method = new WikiHowMethod(m, methodName, article);

            JSONArray steps = (JSONArray) methodObj.get("steps");
            for(int s = 0; s < steps.size(); s++) {
                JSONObject stepObj = (JSONObject) steps.get(s);
                String headline = (String) stepObj.get("headline");
                String desc = (String) stepObj.get("description");
                String imgFile = (String) stepObj.get("img");
                if (!GlobalSettings.ONLY_CATEGORY_FILTER && !OccurrenceChecker.checkOccurrence(desc)) {
                    continue;
                }
                WikiHowStep step = new WikiHowStep(s, headline, desc, imgFile, method);
                stepsInFile.add(step);
            }
        }

        return stepsInFile;
    }

    private boolean checkForFileSkip(JSONObject file) {
        JSONArray categories = (JSONArray) file.get("category_hierarchy");
        if(GlobalSettings.CATEGORY_EXCLUSION) {
            if(categories.isEmpty() || !categories.get(0).equals(GlobalSettings.relevantCategoryParent)) {
                return true;
            }
        }
        if(GlobalSettings.ONLY_CATEGORY_FILTER) {
            return categories.isEmpty() || !categories.contains(GlobalSettings.relevantCategoryAny);
        }
        return false;
    }
}