package utils;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.util.Arrays;

public class PoSTagger {

    private static final String POS_PREPOSITION_TAG = "IN";

    private static MaxentTagger tagger;

    private final static String[] prepositions = new String[]{"over", "under", "in", "into", "inside", "on", "onto",
            "down", "through", "out", "off", "up"};

    public static void initialize() {
        tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger");
    }

    public static String getFirstPrepositionAfterWord(int wordLoc, String sentence) {
        String tag = tagger.tagString(sentence);
        String[] tagsPerWord = tag.split("\\s+");
        for(int i = wordLoc; i < tagsPerWord.length; i++) {
            String[] wordTagPair = tagsPerWord[i].split("_");
            if(wordTagPair[1].equals(POS_PREPOSITION_TAG)) {
                if(GlobalSettings.EXCLUDE_PREPOSITIONS) {
                    if(Arrays.stream(prepositions).anyMatch(wordTagPair[0]::equalsIgnoreCase)) {
                        return wordTagPair[0];
                    }
                } else {
                    return wordTagPair[0];
                }
            }
        }

        return "";
    }
}
