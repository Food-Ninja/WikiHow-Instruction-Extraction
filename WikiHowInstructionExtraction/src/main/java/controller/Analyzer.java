package controller;

import model.ExtractedInstruction;
import model.VerbUsageSentence;
import org.json.simple.JSONObject;
import utils.GlobalSettings;
import utils.OccurrenceChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Analyzer {
    private int titleCounter = 0;
    private int titleDescCounter = 0;
    private int methodCounter = 0;
    private int stepHeadlineCounter = 0;
    private int stepDescCounter = 0;

    private int videoCounter = 0;
    private int imageCounter = 0;

    public void analyzeOverviewResults(ArrayList<ExtractedInstruction> results) {
        if(GlobalSettings.OCCURRENCE_PRINTING) {
            printVerbOccurrenceResults();
        }

        if(GlobalSettings.IMAGE_VIDEO_COUNTER) {
            printImageAndVideoResults();
        }

        if(GlobalSettings.CATEGORY_DISTRIBUTION) {
            analyzeCategoryDistribution(results);
        }
    }

    public void analyzeDetailedResults(ArrayList<VerbUsageSentence> results) {
        if(GlobalSettings.SENTENCE_PARTS_ANALYSIS) {
            countAndPrintSentenceStructure(results);
        }

        if(GlobalSettings.IMAGE_VIDEO_COUNTER) {
            printImageAndVideoResults();
        }

        if(GlobalSettings.PREPOSITION_DISTRIBUTION) {
            analyzePrepositionDistribution(results);
        }

        countAndPrintMeasurementOccurrences(results);
    }

    private void countAndPrintMeasurementOccurrences(ArrayList<VerbUsageSentence> results) {
        int counter = 0;
        for(VerbUsageSentence usage : results) {
            String obj = usage.getTargetObject();
            if(obj.matches("(.*)\\d(.*)")) {
                counter++;
            }
        }
        System.out.println("Objects that contain a decimal measurement: " + counter);
    }

    private void analyzePrepositionDistribution(ArrayList<VerbUsageSentence> results) {
        HashMap<String, Integer> dist = new HashMap<String, Integer>();
        for(VerbUsageSentence sent : results) {
            String prep = sent.getPreposition().isEmpty() ? "No Preposition" : sent.getPreposition();
            if(dist.containsKey(prep)) {
                dist.put(prep, dist.get(prep) + 1);
            } else {
                dist.put(prep, 1);
            }
        }
        for (Map.Entry<String, Integer> next : dist.entrySet()) {
            System.out.println("Preposition: \"" + next.getKey() + "\" - Entries: " + next.getValue());
        }
        System.out.println("\n");
    }

    private void countAndPrintSentenceStructure(ArrayList<VerbUsageSentence> results) {
        int verbCounter = 0;
        int verbAllPartsCounter = 0;
        int targetMissingCounter = 0;
        int locationMissingCounter = 0;
        int prepositionMissingCounter = 0;
        int onlyTargetCounter = 0;
        int onlyLocationCounter = 0;
        int onlyPrepositionCounter = 0;
        int noPartsCounter = 0;

        int verbPastCounter = 0;
        int verbParticipleCounter = 0;

        for(VerbUsageSentence sent : results) {
            switch (sent.getVerb()) {
                case GlobalSettings.searchTerm -> {
                    verbCounter++;
                }
                case GlobalSettings.searchTermPast -> {
                    verbPastCounter++;
                }
                case GlobalSettings.searchTermParticiple -> {
                    verbParticipleCounter++;
                }
            }
            boolean hasLocation = !sent.getTargetLocation().isEmpty();
            boolean hasTarget = !sent.getTargetObject().isEmpty();
            boolean hasPreposition = !sent.getPreposition().isEmpty();
            if(hasTarget && hasPreposition && hasLocation) {
                verbAllPartsCounter++;
            }
            if(!hasTarget && hasPreposition && hasLocation) {
                targetMissingCounter++;
            }
            if(hasTarget && hasPreposition && !hasLocation) {
                locationMissingCounter++;
            }
            if(hasTarget && !hasPreposition && hasLocation) {
                prepositionMissingCounter++;
            }
            if(hasTarget && !hasPreposition && !hasLocation) {
                onlyTargetCounter++;
            }
            if(!hasTarget && hasPreposition && !hasLocation) {
                onlyPrepositionCounter++;
            }
            if(!hasTarget && !hasPreposition && hasLocation) {
                onlyLocationCounter++;
            }
            if(!hasTarget && !hasPreposition && !hasLocation) {
                noPartsCounter++;
            }
        }

        System.out.println("Usage of " + GlobalSettings.searchTerm + ": " + verbCounter);
        System.out.println("Usage of " + GlobalSettings.searchTermPast + ": " + verbPastCounter);
        System.out.println("Usage of " + GlobalSettings.searchTermParticiple + ": " + verbParticipleCounter + "\n");

        System.out.println("Target, Preposition & Location specified: " + verbAllPartsCounter);
        System.out.println("Preposition & Location specified: " + targetMissingCounter);
        System.out.println("Target & Location specified: " + prepositionMissingCounter);
        System.out.println("Target & Preposition specified: " + locationMissingCounter);
        System.out.println("Only Target specified: " + onlyTargetCounter);
        System.out.println("Only Preposition specified: " + onlyPrepositionCounter);
        System.out.println("Only Location specified: " + onlyLocationCounter);
        System.out.println("No parts specified: " + noPartsCounter + "\n");
    }

    public void analyzeVerbOccurrenceInJSONTitleAndDescription(JSONObject file) {
        if(OccurrenceChecker.checkOccurrence((String)file.get("title"))) {
            titleCounter++;
        }

        if(OccurrenceChecker.checkOccurrence(((String)file.get("title_description")))) {
            titleDescCounter++;
        }
    }

    public void analyzeVerbOccurrenceInJSONMethod(JSONObject method) {
        if (OccurrenceChecker.checkOccurrence((String) method.get("name"))) {
            methodCounter++;
        }
    }

    public void analyzeVerbOccurrenceInJSONStep(JSONObject step) {
        if (OccurrenceChecker.checkOccurrence((String) step.get("headline"))) {
            stepHeadlineCounter++;
        }
        if (OccurrenceChecker.checkOccurrence((String) step.get("description"))) {
            stepDescCounter++;
        }
    }

    public void countIfStepHasWorkingImage(JSONObject step) {
        String imgFile = (String) step.get("img");
        if(imgFile == null || imgFile.isEmpty()) {
            return;
        }

        File file = new File(imgFile);
        String mimetype = null;
        try {
            mimetype = Files.probeContentType(file.toPath());
            if(mimetype == null) {
                return;
            }
            if (mimetype.split("/")[0].equals("image")) {
                imageCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countIfArticleHasVideo(JSONObject article) {
        String videoLink = (String) article.get("video");
        if(videoLink != null && !videoLink.isEmpty()) {
            videoCounter++;
        }
    }

    private void printImageAndVideoResults() {
        System.out.println("Images: " + imageCounter);
        System.out.println("Videos: " + videoCounter + "\n");
    }

    private void printVerbOccurrenceResults() {
        System.out.println("Results in Title: " + titleCounter);
        System.out.println("Results in Title Description: " + titleDescCounter);
        System.out.println("Results in Methods: " + methodCounter);
        System.out.println("Results in Step Headlines: " + stepHeadlineCounter);
        System.out.println("Results in Step Description: " + stepDescCounter + "\n");
    }

    private void analyzeCategoryDistribution(ArrayList<ExtractedInstruction> results) {
        HashMap<String, Integer> catDist = new HashMap<String, Integer>();
        for(ExtractedInstruction inst : results) {
            String parentCat = inst.getCategories().isEmpty() ? "No Category" : inst.getCategories().get(0);
            if(catDist.containsKey(parentCat)) {
                catDist.put(parentCat, catDist.get(parentCat) + 1);
            } else {
                catDist.put(parentCat, 1);
            }
        }
        Iterator<Map.Entry<String, Integer>> keys = catDist.entrySet().iterator();
        while (keys.hasNext()) {
            Map.Entry<String, Integer> next = keys.next();
            System.out.println("Category: " + next.getKey() + " Entries: " + next.getValue());
        }
        System.out.println("\n");
    }
}