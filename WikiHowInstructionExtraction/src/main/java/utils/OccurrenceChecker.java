package utils;

import model.CuttingVerb;
import nlp.PoSTagger;

import java.util.regex.Pattern;

public class OccurrenceChecker {
    public static boolean checkOccurrence(String toCheck) {
        return checkOccurrence(toCheck, GlobalSettings.searchVerb);
    }

    public static boolean checkOccurrence(String toCheck, CuttingVerb verbToCompareTo) {
        if(toCheck == null || toCheck.isEmpty() || verbToCompareTo == null) {
            return false;
        }

        boolean presentOrParticiple = checkOccurrenceAndVerb(verbToCompareTo.present, toCheck) || checkOccurrenceAndVerb(verbToCompareTo.participle, toCheck);
        if(GlobalSettings.EXCLUDE_PAST_TENSE || verbToCompareTo.doesPresentEqualPast()) {
            return presentOrParticiple;
        }

        return presentOrParticiple || checkOccurrenceAndVerb(verbToCompareTo.past, toCheck);
    }

    private static boolean checkOccurrenceAndVerb(String word, String sentence) {
        if(doesWordOccurInSentence(word, sentence)) {
            return PoSTagger.checkIfWordIsUsedAsVerb(word, sentence);
        } else {
            return false;
        }
    }

    public static boolean doesWordOccurInSentence(String word, String sentence) {
        String regexFilter = String.format("^(?=.*\\b%s\\b).*$", word.toLowerCase());
        Pattern regexPattern = Pattern.compile(regexFilter, Pattern.MULTILINE);
        return regexPattern.matcher(sentence.toLowerCase()).find();
    }

    public static int getVerbLocationInSentenceChars(String sent) {
        int verbLocation = sent.indexOf(GlobalSettings.searchVerb.present);
        if(verbLocation == -1) {
            verbLocation = sent.indexOf(GlobalSettings.searchVerb.past);
            if(verbLocation == -1) {
                verbLocation = sent.indexOf(GlobalSettings.searchVerb.participle);
                if(verbLocation == -1) {
                    verbLocation = 0;
                }
            }
        }
        return verbLocation;
    }
}