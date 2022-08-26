package analysis;

import model.DeconstructedStepSentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrepositionDistributionAnalyzer implements ISentenceAnalyzer {
    @Override
    public void analyzeAndPrintResults(ArrayList<DeconstructedStepSentence> sentences) {
        HashMap<String, Integer> dist = new HashMap<String, Integer>();
        for(DeconstructedStepSentence sent : sentences) {
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
    }
}