package nlp;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import utils.GlobalSettings;

import java.util.Arrays;

public class PoSTagger {

    private static final String PREPOSITION_TAG = "IN";

    private static final String NOUN_TAG = "NN";

    private static MaxentTagger tagger;

    private final static String[] prepositions = new String[]{"over", "under", "in", "into", "inside", "on", "onto",
            "down", "through", "out", "off", "up"};

    public static void initialize() {
        tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger");
    }

    public static int getPositionOfFirstPreposition(String sentence) {
        String tag = tagger.tagString(sentence);
        String[] tagsPerWord = tag.split("\\s+");
        for(int i = 1; i < tagsPerWord.length; i++) {
            String[] wordTagPair = tagsPerWord[i].split("_");
            if(wordTagPair[1].equals(PREPOSITION_TAG)) {
                if(GlobalSettings.EXCLUDE_PREPOSITIONS) {
                    if(Arrays.stream(prepositions).anyMatch(wordTagPair[0]::equalsIgnoreCase)) {
                        return i;
                    }
                } else {
                    return i;
                }
            }
        }

        return -1;
    }

    public static boolean checkForNoun(String toCheck) {
        String tag = tagger.tagString(toCheck);
        String[] tagsPerWord = tag.split("\\s+");
        for (String s : tagsPerWord) {
            String[] wordTagPair = s.split("_");
            if (wordTagPair[1].equals(NOUN_TAG)) {
                return true;
            }
        }
        return false;
    }
}