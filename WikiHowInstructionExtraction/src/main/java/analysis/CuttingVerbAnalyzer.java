package analysis;

import model.CuttingVerb;
import model.WikiHowStep;
import utils.OccurrenceChecker;

import java.util.ArrayList;

public class CuttingVerbAnalyzer {
    public static void analyzeSynonyms(ArrayList<WikiHowStep> steps) {
        System.out.println("Starting the Cutting Synonym Analysis...");
        for (CuttingVerb verb : CuttingVerb.values()) {
            int descCount = 0;
            int methCount = 0;
            int headCount = 0;
            int titleCount = 0;
            int tiDescCount = 0;
            for(WikiHowStep step : steps) {
                if(OccurrenceChecker.checkOccurrence(step.getDescription(), verb)) {
                    descCount++;
                }
                if(OccurrenceChecker.checkOccurrence(step.getHeadline(), verb)) {
                    headCount++;
                }
                if(OccurrenceChecker.checkOccurrence(step.getMethod().getName(), verb)) {
                    methCount++;
                }
                if(OccurrenceChecker.checkOccurrence(step.getMethod().getArticle().getTitle(), verb)) {
                    titleCount++;
                }
                if(OccurrenceChecker.checkOccurrence(step.getMethod().getArticle().getDescription(), verb)) {
                    tiDescCount++;
                }
            }

            System.out.printf("%s; %d; %d; %d; %d; %d%n", verb.present, titleCount, tiDescCount, methCount, headCount, descCount);
        }
    }
}