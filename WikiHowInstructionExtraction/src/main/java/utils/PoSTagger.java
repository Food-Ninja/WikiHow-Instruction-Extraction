package utils;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PoSTagger {

    private static String POS_PREPOSITION_TAG = "IN";

    private static MaxentTagger tagger;

    public static void initialize() {
        tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger");
    }

    public static String getFirstPrepositionAfterWord(int wordLoc, String sentence) {
        String tag = tagger.tagString(sentence);
        String[] tagsPerWord = tag.split("\\s+");
        for(int i = wordLoc; i < tagsPerWord.length; i++) {
            String[] wordTagPair = tagsPerWord[i].split("_");
            if(wordTagPair[1].equals(POS_PREPOSITION_TAG)) {
                return wordTagPair[0];
            }
        }

        return "";
    }
}
