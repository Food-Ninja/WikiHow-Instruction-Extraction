package utils;

import model.verbs.ISearchableVerb;
import nlp.PoSTagger;

import java.util.regex.Pattern;

public class OccurrenceChecker {
    public static boolean checkOccurrence(String toCheck) {
        return checkOccurrence(toCheck, GlobalSettings.searchVerb);
    }

    public static boolean checkOccurrence(String toCheck, ISearchableVerb verbToCompareTo) {
        if(toCheck == null || toCheck.isEmpty() || verbToCompareTo == null) {
            return false;
        }

        toCheck = toCheck.toLowerCase();
        boolean presentOrParticiple = checkOccurrenceAndVerb(verbToCompareTo.getPresentForm(), toCheck) || checkOccurrenceAndVerb(verbToCompareTo.getParticipleForm(), toCheck);
        if(GlobalSettings.EXCLUDE_PAST_TENSE || verbToCompareTo.doesPresentEqualPast()) {
            return presentOrParticiple;
        }

        return presentOrParticiple || checkOccurrenceAndVerb(verbToCompareTo.getPastForm(), toCheck);
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

    public static int getVerbLocationInSentenceChars(String sent, ISearchableVerb verb) {
        int verbLocation = sent.indexOf(verb.getPresentForm());
        if(verbLocation == -1) {
            verbLocation = sent.indexOf(verb.getPastForm());
            if(verbLocation == -1) {
                verbLocation = sent.indexOf(verb.getParticipleForm());
                if(verbLocation == -1) {
                    verbLocation = 0;
                }
            }
        }
        return verbLocation;
    }
}