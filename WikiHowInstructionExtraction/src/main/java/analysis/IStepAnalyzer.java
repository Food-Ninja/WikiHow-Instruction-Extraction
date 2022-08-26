package analysis;

import model.WikiHowStep;

import java.util.ArrayList;

public interface IStepAnalyzer {
    void analyzeAndPrintResults(ArrayList<WikiHowStep> steps);
}