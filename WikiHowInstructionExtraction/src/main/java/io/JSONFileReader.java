package io;

import analysis.CorpusDataPrinter;
import analysis.ImageAndVideoCounter;
import analysis.VerbOccurrencePrinter;
import model.*;
import nlp.SentencePreprocessor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.GlobalSettings;

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
        String video = (String) file.get("video");
        WikiHowArticle article = new WikiHowArticle(articleTitle, articleDescription, video);
        article.addCategories(categories);
        boolean relevantCategory = !categories.isEmpty() && categories.get(0).equals(GlobalSettings.relevantCategoryParent);
        if(GlobalSettings.IMAGE_VIDEO_COUNTER) {
            ImageAndVideoCounter.countIfArticleHasVideo(article);
        }
        if(GlobalSettings.OCCURRENCE_PRINTING) {
            VerbOccurrencePrinter.analyzeSearchTermOccurrenceInArticle(article);
        }
        if(GlobalSettings.GET_CORPUS_META) {
            CorpusDataPrinter.countArticle(relevantCategory);
        }

        JSONArray methods = (JSONArray) file.get("methods");
        if(GlobalSettings.GET_CORPUS_META) {
            CorpusDataPrinter.countMethods(methods.size(), relevantCategory);
        }
        for(int m = 0; m < methods.size(); m++) {
            JSONObject methodObj = (JSONObject) methods.get(m);
            String methodName = (String) methodObj.get("name");
            WikiHowMethod method = new WikiHowMethod(m, methodName, article);
            if(GlobalSettings.OCCURRENCE_PRINTING) {
                VerbOccurrencePrinter.analyzeSearchTermOccurrenceInMethod(method);
            }

            JSONArray steps = (JSONArray) methodObj.get("steps");
            if(GlobalSettings.GET_CORPUS_META) {
                CorpusDataPrinter.countSteps(steps.size(), relevantCategory);
            }
            for(int s = 0; s < steps.size(); s++) {
                JSONObject stepObj = (JSONObject) steps.get(s);
                String headline = (String) stepObj.get("headline");
                String desc = (String) stepObj.get("description");
                String imgFile = (String) stepObj.get("img");
                WikiHowStep step = new WikiHowStep(s, headline, desc, imgFile, method);
                if(GlobalSettings.OCCURRENCE_PRINTING) {
                    VerbOccurrencePrinter.analyzeSearchTermOccurrenceInStep(step);
                }
                if(GlobalSettings.GET_CORPUS_META) {
                    String cleaned_desc = SentencePreprocessor.preprocessWholeDescription(desc);
                    CorpusDataPrinter.countStepSentences(SentencePreprocessor.splitDescriptionIntoSentences(cleaned_desc).size(), relevantCategory);
                }
                stepsInFile.add(step);
            }
        }

        return stepsInFile;
    }

    private boolean checkForFileSkip(JSONObject file) {
        JSONArray categories = (JSONArray) file.get("category_hierarchy");
        if(GlobalSettings.CATEGORY_EXCLUSION) {
            return categories.isEmpty() || !categories.get(0).equals(GlobalSettings.relevantCategoryParent);
        }
        if(GlobalSettings.ONLY_CATEGORY_FILTER) {
            return categories.isEmpty() || !categories.contains(GlobalSettings.relevantCategoryAny);
        }
        return false;
    }
}