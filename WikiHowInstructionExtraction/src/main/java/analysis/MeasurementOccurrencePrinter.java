package analysis;

import model.DeconstructedStepSentence;

import java.util.ArrayList;

public class MeasurementOccurrencePrinter implements ISentenceAnalyzer {
    @Override
    public void analyzeAndPrintResults(ArrayList<DeconstructedStepSentence> sentences) {
        int counter = 0;
        for(DeconstructedStepSentence usage : sentences) {
            String obj = usage.getBeforePrep();
            if(obj.matches("(.*)\\d(.*)")) {
                counter++;
            }
        }
        System.out.println("Objects that contain a decimal measurement: " + counter);
    }
}