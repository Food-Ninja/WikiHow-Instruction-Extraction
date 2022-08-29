package utils;

import java.util.Arrays;

public class OccurrenceChecker {
    public static boolean checkOccurrence(String toCheck) {
        if(toCheck == null || toCheck.isEmpty()) {
            return false;
        }

        return doesWordOccurInSentence(GlobalSettings.searchTerm, toCheck) || doesWordOccurInSentence(GlobalSettings.searchTermParticiple, toCheck) ||
                (doesWordOccurInSentence(GlobalSettings.searchTermPast, toCheck) && !GlobalSettings.EXCLUDE_PAST_TENSE);
    }

    public static boolean doesWordOccurInSentence(String word, String sentence) {
        String[] words = sentence.toLowerCase().split(" ");
        return Arrays.asList(words).contains(word);
    }

    public static int getVerbLocationInSentenceChars(String sent) {
        int verbLocation = sent.indexOf(GlobalSettings.searchTerm);
        if(verbLocation == -1) {
            if(!GlobalSettings.EXCLUDE_PAST_TENSE) {
                verbLocation = sent.indexOf(GlobalSettings.searchTermPast);
            }
            if(verbLocation == -1) {
                verbLocation = sent.indexOf(GlobalSettings.searchTermParticiple);
                if(verbLocation == -1) {
                    verbLocation = 0;
                }
            }
        }
        return verbLocation;
    }
}