package analysis;

import model.WikiHowStep;
import model.verbs.ISearchableVerb;
import utils.OccurrenceChecker;

import java.util.ArrayList;

public class VerbHyponymCountAnalyzer {
    public static void analyzeHyponyms(ArrayList<WikiHowStep> steps, ISearchableVerb[] verbs) {
        System.out.printf("Starting the %s Hyponym Analysis...\n", verbs.getClass().getSimpleName());
        for (ISearchableVerb verb : verbs) {
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
            int sum = titleCount + tiDescCount + methCount + headCount + descCount;
            System.out.printf("%s, %d, %d, %d, %d, %d, %d%n", verb.getPresentForm(), titleCount, tiDescCount, methCount, headCount, descCount, sum);
        }
    }
}