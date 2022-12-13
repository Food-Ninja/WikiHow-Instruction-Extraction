package analysis;

import model.DeconstructedStepSentence;

import java.util.ArrayList;

public interface ISentenceAnalyzer {
    void analyzeAndPrintResults(ArrayList<DeconstructedStepSentence> sentences);
}