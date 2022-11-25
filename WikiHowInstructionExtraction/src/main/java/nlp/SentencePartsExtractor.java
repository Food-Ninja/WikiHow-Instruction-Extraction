package nlp;

import model.DeconstructedStepSentence;
import model.WikiHowStep;
import utils.GlobalSettings;
import utils.OccurrenceChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SentencePartsExtractor {

    public final static String[] targetObjectBeginners = new String[]{"the", "a", "your", "them", "it", "enough"};

    public static ArrayList<DeconstructedStepSentence> deconstructStepsIntoSentenceParts(ArrayList<WikiHowStep> steps) {
        ArrayList<DeconstructedStepSentence> sentences = new ArrayList<>();
        for(WikiHowStep step : steps) {
            sentences.addAll(splitSentencesInParts(SentencePreprocessor.preprocessDescription(step.getDescription()), step));
        }
        return sentences;
    }

    private static ArrayList<DeconstructedStepSentence> splitSentencesInParts(Map<String, String> sentences, WikiHowStep currStep) {
        ArrayList<DeconstructedStepSentence> results = new ArrayList<>();
        int sentCount = 0;

        for(var sentEntry : sentences.entrySet()) {
            String sent = sentEntry.getValue();
            String[] words = sent.split(" ");
            String verb = words[0];

            int prepPos = PoSTagger.getPositionOfFirstPreposition(sent);
            String prep = (prepPos < 0 || prepPos >= words.length) ? "" : words[prepPos];
            StringBuilder after = new StringBuilder();
            StringBuilder before = new StringBuilder();

            if(!prep.isEmpty()) {
                for(int j = 1; j < prepPos; j++) {
                    before.append(words[j]).append(" ");
                }
                for(int j = prepPos + 1; j < words.length; j++) {
                    after.append(words[j]).append(" ");
                }
            } else {
                for(int i = 1; i < words.length; i++) {
                    if(Arrays.stream(targetObjectBeginners).anyMatch(words[i]::equalsIgnoreCase) || words[i].matches("\\d")) {
                        for(int j = i; j < words.length; j++) {
                            before.append(words[j]).append(" ");
                        }

                        break;
                    }
                }
            }

            if(GlobalSettings.FILTER_TARGET &&
                    !OccurrenceChecker.doesWordOccurInSentence(GlobalSettings.targetFilterTerm, before.toString())) {
                //before.toString().trim().isEmpty()) {
                continue;
            }
            if(GlobalSettings.FILTER_LOCATION &&
                    !OccurrenceChecker.doesWordOccurInSentence(GlobalSettings.locationFilterTerm, after.toString())) {
                //!after.toString().trim().isEmpty()) {
                continue;
            }
            if(GlobalSettings.FILTER_SENTENCE &&
                    !OccurrenceChecker.doesWordOccurInSentence(GlobalSettings.sentenceFilterTerm, sentEntry.getKey())) {
                //!after.toString().trim().isEmpty()) {
                continue;
            }
            if(GlobalSettings.EXCLUDE_EMPTY_PREPOSITIONS &&
                    prep.trim().isEmpty()) {
                continue;
            }

            results.add(new DeconstructedStepSentence(sentCount++, verb, prep, before.toString().trim(), after.toString().trim(), sentEntry.getKey(), currStep));
        }

        return results;
    }
}