package analysis;

import model.WikiHowArticle;
import model.WikiHowMethod;
import model.WikiHowStep;
import utils.OccurrenceChecker;

import java.util.ArrayList;

public class VerbOccurrencePrinter implements IStepAnalyzer {
    private static int titleCounter = 0;
    private static int titleDescCounter = 0;
    private static int methodCounter = 0;
    private static int stepHeadlineCounter = 0;
    private static int stepDescCounter = 0;

    @Override
    public void analyzeAndPrintResults(ArrayList<WikiHowStep> steps) {
        System.out.println("Results in Title: " + titleCounter);
        System.out.println("Results in Title Description: " + titleDescCounter);
        System.out.println("Results in Methods: " + methodCounter);
        System.out.println("Results in Step Headlines: " + stepHeadlineCounter);
        System.out.println("Results in Step Description: " + stepDescCounter);
    }

    public static void analyzeSearchTermOccurrenceInArticle(WikiHowArticle article) {
        if(OccurrenceChecker.checkOccurrence(article.getTitle())) {
            titleCounter++;
        }

        if(OccurrenceChecker.checkOccurrence(article.getDescription())) {
            titleDescCounter++;
        }
    }

    public static void analyzeSearchTermOccurrenceInMethod(WikiHowMethod method) {
        if (OccurrenceChecker.checkOccurrence(method.getName())) {
            methodCounter++;
        }
    }

    public static void analyzeSearchTermOccurrenceInStep(WikiHowStep step) {
        if (OccurrenceChecker.checkOccurrence(step.getHeadline())) {
            stepHeadlineCounter++;
        }
        if (OccurrenceChecker.checkOccurrence(step.getDescription())) {
            stepDescCounter++;
        }
    }
}