package analysis;

import model.WikiHowStep;
import model.verbs.PouringVerb;
import utils.OccurrenceChecker;

import java.util.ArrayList;

public class PouringVerbAnalyzer {
    public static void analyzeHyponyms(ArrayList<WikiHowStep> steps) {
        System.out.println("Starting the Pouring Hyponym Analysis...");
        for (PouringVerb verb : PouringVerb.values()) {
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

            System.out.printf("%s; %d; %d; %d; %d; %d%n", verb.getPresentForm(), titleCount, tiDescCount, methCount, headCount, descCount);
        }
    }
}