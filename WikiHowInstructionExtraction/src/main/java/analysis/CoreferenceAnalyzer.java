package analysis;

import model.DeconstructedStepSentence;
import java.util.*;

import static nlp.CoreferenceResolver.coreferenceWords;

public class CoreferenceAnalyzer implements ISentenceAnalyzer {
    private Hashtable<String, Integer> counter = new Hashtable<>();

    @Override
    public void analyzeAndPrintResults(ArrayList<DeconstructedStepSentence> sentences) {
        for(DeconstructedStepSentence sent : sentences) {
            for(String cor : coreferenceWords) {
                if(sent.checkIfBeforeTextContainsGivenTerm(cor)) {
                    increaseCounterForCoreferenceTerm(cor);
                }
            }
        }

        for(Map.Entry<String, Integer> entry : counter.entrySet()) {
            System.out.println("\"" + entry.getKey() + "\": #" + entry.getValue());
        }
    }

    private void increaseCounterForCoreferenceTerm(String term) {
        if(counter.containsKey(term)) {
            counter.put(term, counter.get(term) + 1);
        } else {
            counter.put(term, 1);
        }
    }
}