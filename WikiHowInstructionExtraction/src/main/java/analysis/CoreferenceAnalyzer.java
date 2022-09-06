package analysis;

import model.DeconstructedStepSentence;
import java.util.*;

import static nlp.CoreferenceResolver.coreferenceWords;

public class CoreferenceAnalyzer implements ISentenceAnalyzer {
    @Override
    public void analyzeAndPrintResults(ArrayList<DeconstructedStepSentence> sentences) {
        Hashtable<String, Integer> counter = new Hashtable<>();
        for(DeconstructedStepSentence sent : sentences) {
            String key = sent.getBeforePrep();
            if(Arrays.stream(coreferenceWords).anyMatch(key::equalsIgnoreCase)) {
                if(counter.containsKey(key)) {
                    counter.put(key, counter.get(key) + 1);
                } else {
                    counter.put(key, 1);
                }
            }
        }

        for(Map.Entry<String, Integer> entry : counter.entrySet()) {
            System.out.println("\"" + entry.getKey() + "\": #" + entry.getValue());
        }
    }
}