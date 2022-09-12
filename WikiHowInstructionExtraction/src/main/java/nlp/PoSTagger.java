package nlp;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import utils.GlobalSettings;

import java.util.Arrays;

public class PoSTagger {
    private static final String PREPOSITION_TAG = "IN";
    private static final String PARTICLE_TAG = "RP";

    // Tag Resource:
    // https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
    private static final String[] VERB_TAGS = new String[]{ "VB", "VBG", "VBD", "VBN", "VBP", "VBZ" };
    private static final String[] NOUN_TAGS = new String[]{ "NN", "NNS", "NNP", "NNPS" };
    private static MaxentTagger tagger;

    private final static String[] prepositions = new String[]{"over", "under", "in", "into", "inside", "on", "onto",
            "down", "through", "out", "off", "up"};

    public static void initialize() {
        tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger");
    }

    public static int getPositionOfFirstPreposition(String sentence) {
        sentence = preprocessSentence(sentence);
        String tag = tagger.tagString(sentence);
        String[] tagsPerWord = tag.split("\\s+");
        for(int i = 1; i < tagsPerWord.length; i++) {
            String[] wordTagPair = tagsPerWord[i].split("_");
            if(wordTagPair[1].equals(PREPOSITION_TAG) || wordTagPair[1].equals(PARTICLE_TAG)) {
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

    public static boolean checkIfPastTenseIsVerb(String toCheck) {
        String tagged = tagger.tagString(toCheck);
        String[] tagsPerWord = tagged.split("\\s+");
        for (String s : tagsPerWord) {
            String[] wordTagPair = s.split("_");
            String word = wordTagPair[0];
            String tag = wordTagPair[1];

            if(word.equals(GlobalSettings.searchTermPast)) {
                if (Arrays.stream(VERB_TAGS).anyMatch(tag::equalsIgnoreCase)) {
                    return true;
                }
            }
        }
        return  false;
    }

    public static boolean checkForNoun(String toCheck) {
        String tag = tagger.tagString(toCheck);
        String[] tagsPerWord = tag.split("\\s+");
        for (String s : tagsPerWord) {
            String[] wordTagPair = s.split("_");
            if (Arrays.stream(NOUN_TAGS).anyMatch(wordTagPair[1]::equalsIgnoreCase)) {
                return true;
            }
        }
        return false;
    }

    private static String preprocessSentence(String sent) {
        sent = sent.replaceAll("-", "");
        return sent;
    }
}