package utils;

import nlp.PoSTagger;

import java.util.regex.Pattern;

public class OccurrenceChecker {
    public static boolean checkOccurrence(String toCheck) {
        if(toCheck == null || toCheck.isEmpty()) {
            return false;
        }

        if(!GlobalSettings.EXCLUDE_PAST_TENSE && !GlobalSettings.searchVerb.doesPresentEqualPast() && doesWordOccurInSentence(GlobalSettings.searchVerb.past, toCheck)) {
            return PoSTagger.checkIfPastTenseIsVerb(toCheck);
        }

        return doesWordOccurInSentence(GlobalSettings.searchVerb.present, toCheck) || doesWordOccurInSentence(GlobalSettings.searchVerb.participle, toCheck);
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