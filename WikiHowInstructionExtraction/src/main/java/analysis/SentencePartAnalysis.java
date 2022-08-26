package analysis;

import model.DeconstructedStepSentence;
import utils.GlobalSettings;

import java.util.ArrayList;

public class SentencePartAnalysis implements ISentenceAnalyzer {
    @Override
    public void analyzeAndPrintResults(ArrayList<DeconstructedStepSentence> sentences) {
        int verbCounter = 0;
        int verbAllPartsCounter = 0;
        int beforePrepMissingCounter = 0;
        int afterPrepMissingCounter = 0;
        int prepositionMissingCounter = 0;
        int onlyBeforePrepCounter = 0;
        int onlyAfterPrepCounter = 0;
        int onlyPrepositionCounter = 0;
        int noPartsCounter = 0;

        int verbPastCounter = 0;
        int verbParticipleCounter = 0;

        for(DeconstructedStepSentence sent : sentences) {
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
            boolean hasPartAfterPrep = !sent.getAfterPrep().isEmpty();
            boolean hasPartBeforePrep = !sent.getBeforePrep().isEmpty();
            boolean hasPreposition = !sent.getPreposition().isEmpty();
            if(hasPartBeforePrep && hasPreposition && hasPartAfterPrep) {
                verbAllPartsCounter++;
            }
            if(!hasPartBeforePrep && hasPreposition && hasPartAfterPrep) {
                beforePrepMissingCounter++;
            }
            if(hasPartBeforePrep && hasPreposition && !hasPartAfterPrep) {
                afterPrepMissingCounter++;
            }
            if(hasPartBeforePrep && !hasPreposition && hasPartAfterPrep) {
                prepositionMissingCounter++;
            }
            if(hasPartBeforePrep && !hasPreposition && !hasPartAfterPrep) {
                onlyBeforePrepCounter++;
            }
            if(!hasPartBeforePrep && hasPreposition && !hasPartAfterPrep) {
                onlyPrepositionCounter++;
            }
            if(!hasPartBeforePrep && !hasPreposition && hasPartAfterPrep) {
                onlyAfterPrepCounter++;
            }
            if(!hasPartBeforePrep && !hasPreposition && !hasPartAfterPrep) {
                noPartsCounter++;
            }
        }

        System.out.println("Usage of " + GlobalSettings.searchTerm + ": " + verbCounter);
        System.out.println("Usage of " + GlobalSettings.searchTermPast + ": " + verbPastCounter);
        System.out.println("Usage of " + GlobalSettings.searchTermParticiple + ": " + verbParticipleCounter + "\n");

        System.out.println("Before, Preposition & After specified: " + verbAllPartsCounter);
        System.out.println("Preposition & After specified: " + beforePrepMissingCounter);
        System.out.println("Before & After specified: " + prepositionMissingCounter);
        System.out.println("Before & Preposition specified: " + afterPrepMissingCounter);
        System.out.println("Only Before specified: " + onlyBeforePrepCounter);
        System.out.println("Only Preposition specified: " + onlyPrepositionCounter);
        System.out.println("Only After specified: " + onlyAfterPrepCounter);
        System.out.println("No parts specified: " + noPartsCounter);
    }
}