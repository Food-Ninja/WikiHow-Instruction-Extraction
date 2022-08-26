package utils;

import model.DeconstructedStepSentence;
import model.WikiHowStep;

import java.util.ArrayList;
import java.util.Arrays;

public class SentenceExtractor {

    public final static String[] prepositions = new String[]{"over", "under", "in", "into", "inside", "on", "onto",
            "down", "through", "out", "off", "up"};

    public final static String[] targetObjectBeginners = new String[]{"the", "a", "your", "them", "it", "enough"};

    public static ArrayList<DeconstructedStepSentence> deconstructStepsIntoSentenceParts(ArrayList<WikiHowStep> steps) {
        ArrayList<DeconstructedStepSentence> sentences = new ArrayList<>();
        for(WikiHowStep step : steps) {
            sentences.addAll(splitSentencesInParts(splitUpStepDescription(step.getDescription()), step));
        }
        return sentences;
    }

    /**
     * Takes the step description string and returns a list containing all sentences from the
     * description where the searched verb occurs as single strings inside the list.
     * @param desc
     * @return
     */
    private static ArrayList<String> splitUpStepDescription(String desc) {
        desc = desc.toLowerCase();
        // remove commas from the original sentence
        desc = desc.replace(",", "");
        // split the description at each period (.)
        String[] sentences = desc.split("\\.");
        ArrayList<String> gluedSent = new ArrayList<>();
        try {
            for(int i = 0; i < sentences.length; i++) {
                // ignore empty strings that occur due to false use of multiple periods (..)
                if(sentences[i].isEmpty()) {
                    continue;
                }

                // since double measurements (like 2.5l) also use a period, we look for
                // substrings that begin with a number (the number after the decimal point)
                // and stitch them together with the last sentences already handled
                if(i > 0 && sentences[i].substring(0, 1).matches("\\d")) {
                    int loc = i - 1;
                    while(loc >= gluedSent.size()) {
                        loc--;
                    }
                    gluedSent.remove(loc);
                    gluedSent.add((sentences[loc] + "." + sentences[i]).trim());
                } else {
                    gluedSent.add(sentences[i].trim());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // return all sentences that contain the action verb
        gluedSent.removeAll(gluedSent.stream().filter(s -> !OccurrenceChecker.checkOccurrence(s)).toList());
        return gluedSent;
    }

    private static ArrayList<DeconstructedStepSentence> splitSentencesInParts(ArrayList<String> sentences, WikiHowStep currStep) {
        ArrayList<DeconstructedStepSentence> results = new ArrayList<>();

        for(int sentCount = 0; sentCount < sentences.size(); sentCount++) {
            String sent = sentences.get(sentCount);
            String verb = "";
            int verbLocation = Arrays.stream(sent.split(" ")).toList().indexOf(GlobalSettings.searchTerm);
            if(verbLocation == -1) {
                if(!GlobalSettings.EXCLUDE_PAST_TENSE) {
                    verbLocation = Arrays.stream(sent.split(" ")).toList().indexOf(GlobalSettings.searchTermPast);
                }
                if(verbLocation == -1) {
                    verbLocation = Arrays.stream(sent.split(" ")).toList()
                            .indexOf(GlobalSettings.searchTermParticiple);
                    if(verbLocation == -1) {
                        verbLocation = 0;
                    } else {
                        verb = GlobalSettings.searchTermParticiple;
                    }
                } else {
                    if(!GlobalSettings.EXCLUDE_PAST_TENSE) {
                        verb = GlobalSettings.searchTermPast;
                    }
                }
            } else {
                verb = GlobalSettings.searchTerm;
            }

            String removedVerb = sent;
            if(!GlobalSettings.EXCLUDE_PAST_TENSE) {
                removedVerb = removedVerb.replace(GlobalSettings.searchTermPast, "");
            }
            removedVerb = removedVerb.replace(GlobalSettings.searchTermParticiple, "");
            removedVerb = removedVerb.replace(GlobalSettings.searchTerm, "");

            String[] words = removedVerb.split(" ");
            String prep = "";
            int prepPos = -1;
            StringBuilder loc = new StringBuilder();
            StringBuilder obj = new StringBuilder();
            if(GlobalSettings.USE_STANFORD_NLP_API) {
                prep = PoSTagger.getFirstPrepositionAfterWord(verbLocation, removedVerb);
                if(!prep.isEmpty()) {
                    for(int i = verbLocation; i < words.length; i++) {
                        if(words[i].equals(prep)) {
                            prepPos = i;
                        }
                    }
                }
            } else {
                for(int i = verbLocation + 1; i < words.length; i++) {
                    if(Arrays.stream(prepositions).anyMatch(words[i]::equalsIgnoreCase)) {
                        prepPos = i;
                        prep = words[prepPos];
                        break;
                    }
                }
            }
            if(prepPos >= 0) {
                for(int j = verbLocation; j < prepPos; j++) {
                    obj.append(words[j]).append(" ");
                }
                for(int j = prepPos + 1; j < words.length; j++) {
                    loc.append(words[j]).append(" ");
                }
            }

            if(prep.isEmpty()) {
                obj = new StringBuilder();
                for(int i = verbLocation + 1; i < words.length; i++) {
                    if(Arrays.stream(targetObjectBeginners).anyMatch(words[i]::equalsIgnoreCase) || words[i].matches("\\d")) {
                        for(int j = i; j < words.length; j++) {
                            obj.append(words[j]).append(" ");
                        }

                        break;
                    }
                }
            }

            if(GlobalSettings.FILTER_TARGET &&
                    !OccurrenceChecker.doesWordOccurInSentence(GlobalSettings.targetFilterTerm, obj.toString())) {
                //obj.toString().trim().isEmpty()) {
                continue;
            }
            if(GlobalSettings.FILTER_LOCATION &&
                    !OccurrenceChecker.doesWordOccurInSentence(GlobalSettings.locationFilterTerm, loc.toString())) {
                //!loc.toString().trim().isEmpty()) {
                continue;
            }
            if(GlobalSettings.EXCLUDE_EMPTY_PREPOSITIONS &&
                    prep.trim().isEmpty()) {
                continue;
            }
            results.add(new DeconstructedStepSentence(sentCount, verb, prep, obj.toString().trim(), loc.toString().trim(), sent, currStep));
        }

        return results;
    }
}