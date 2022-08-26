package utils;

import java.util.Arrays;

public class SupportingToolkit {
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
}