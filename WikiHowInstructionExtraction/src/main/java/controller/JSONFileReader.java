package controller;

import model.ExtractedInstruction;
import model.VerbUsageSentence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.GlobalSettings;
import utils.PoSTagger;
import utils.OccurrenceChecker;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class JSONFileReader {
    private final Analyzer analyzer;

    public final String[] prepositions = new String[]{"over", "under", "in", "into", "inside", "on", "onto",
            "down", "through", "out", "off", "up"};

    public final String[] targetObjectBeginners = new String[]{"the", "a", "your", "them", "it", "enough"};

    public JSONFileReader(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    private int articleCounter = 0;

    public ArrayList<ExtractedInstruction> extractInstructionsFromAllFiles() {
        File dir = new File("./data/");
        JSONParser parser = new JSONParser();
        ArrayList<ExtractedInstruction> fittingInstructions = new ArrayList<>();
        int progressCounter = 0;
        int tenPercent = Objects.requireNonNull(dir.listFiles()).length / 10;
        int percentCounter = 1;

        for(File json : Objects.requireNonNull(dir.listFiles())) {
            try {
                Object obj = parser.parse(new FileReader(json));
                ArrayList<ExtractedInstruction> extractedFromCurrentFile = extractInstructionFromSingleFile((JSONObject) obj);

                if(progressCounter >= tenPercent) {
                    System.out.println(percentCounter + "0% completed");
                    progressCounter = 0;
                    percentCounter++;
                } else {
                    progressCounter++;
                }

                if (extractedFromCurrentFile.isEmpty()) {
                    continue;
                }
                analyzer.countIfArticleHasVideo((JSONObject) obj);
                fittingInstructions.addAll(extractedFromCurrentFile);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(percentCounter + "0% completed\n");
        System.out.println("#Articles: " + articleCounter);
        return fittingInstructions;
    }

    public ArrayList<VerbUsageSentence> extractSentencesFromAllFiles() {
        File dir = new File("./data/");
        JSONParser parser = new JSONParser();
        ArrayList<VerbUsageSentence> sentences = new ArrayList<>();
        int progressCounter = 0;
        int tenPercent = Objects.requireNonNull(dir.listFiles()).length / 10;
        int percentCounter = 1;

        for(File json : Objects.requireNonNull(dir.listFiles())) {
            try {
                Object obj = parser.parse(new FileReader(json));
                ArrayList<VerbUsageSentence> extractedFromCurrentFile = extractSentenceFromSingleFile((JSONObject) obj);

                if(progressCounter >= tenPercent) {
                    System.out.println(percentCounter + "0% completed");
                    progressCounter = 0;
                    percentCounter++;
                } else {
                    progressCounter++;
                }
                if (extractedFromCurrentFile.isEmpty()) {
                    continue;
                }

                analyzer.countIfArticleHasVideo((JSONObject) obj);
                sentences.addAll(extractedFromCurrentFile);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(percentCounter + "0% completed\n");
        return sentences;
    }

    private ArrayList<ExtractedInstruction> extractInstructionFromSingleFile(JSONObject file) {
        JSONArray categories = (JSONArray) file.get("category_hierarchy");
        if(GlobalSettings.CATEGORY_EXCLUSION) {
            if(categories.isEmpty() || !categories.get(0).equals(GlobalSettings.relevantCategoryParent)) {
                return new ArrayList<>();
            }
        }
        if(GlobalSettings.ONLY_CATEGORY_FILTER) {
            if(categories.isEmpty() || !categories.contains(GlobalSettings.relevantCategoryAny)) {
                return new ArrayList<>();
            }
        }

        return extractInstructionOverviewFromSingleFile(file);
    }

    private ArrayList<ExtractedInstruction> extractInstructionOverviewFromSingleFile(JSONObject file) {
        ArrayList<ExtractedInstruction> allInstructions = new ArrayList<>();

        JSONArray categories = (JSONArray) file.get("category_hierarchy");
        String howTo = (String) file.get("title");
        analyzer.analyzeVerbOccurrenceInJSONTitleAndDescription(file);
        articleCounter++;

        JSONArray methods = (JSONArray) file.get("methods");
        for (Object method : methods) {
            JSONObject methodObj = (JSONObject) method;
            analyzer.analyzeVerbOccurrenceInJSONMethod(methodObj);

            String methodName = (String) methodObj.get("name");
            JSONArray steps = (JSONArray) methodObj.get("steps");
            for (Object step : steps) {
                JSONObject stepObj = (JSONObject) step;
                analyzer.analyzeVerbOccurrenceInJSONStep(stepObj);

                String instruction = (String) stepObj.get("headline");
                String desc = (String) stepObj.get("description");
                if (!GlobalSettings.ONLY_CATEGORY_FILTER && !OccurrenceChecker.checkOccurrence(desc)) {
                    continue;
                }
                analyzer.countIfStepHasWorkingImage(stepObj);

                ExtractedInstruction instr = new ExtractedInstruction(instruction, howTo, methodName, desc);
                instr.addCategories(categories);
                allInstructions.add(instr);
            }
        }

        return allInstructions;
    }

    private ArrayList<VerbUsageSentence> extractSentenceFromSingleFile(JSONObject file) {
        JSONArray categories = (JSONArray) file.get("category_hierarchy");
        if(GlobalSettings.CATEGORY_EXCLUSION) {
            if(categories.isEmpty() || !categories.get(0).equals(GlobalSettings.relevantCategoryParent)) {
                return new ArrayList<>();
            }
        }

        return extractDetailedInstructionFromSingleFile(file);
    }

    private ArrayList<VerbUsageSentence> extractDetailedInstructionFromSingleFile(JSONObject file) {
        ArrayList<VerbUsageSentence> sentences = new ArrayList<>();

        JSONArray methods = (JSONArray) file.get("methods");
        for (Object method : methods) {
            JSONObject methodObj = (JSONObject) method;
            JSONArray steps = (JSONArray) methodObj.get("steps");
            for (Object step : steps) {
                JSONObject stepObj = (JSONObject) step;
                String desc = (String) stepObj.get("description");
                if (!OccurrenceChecker.checkOccurrence(desc)) {
                    continue;
                }
                analyzer.countIfStepHasWorkingImage(stepObj);

                ArrayList<VerbUsageSentence> results = splitSentencesInParts(splitUpStepDescription(desc));
                if(GlobalSettings.FILTER_SENTENCE) {
                    sentences.addAll(results.stream().filter(s ->
                            OccurrenceChecker.doesWordOccurInSentence(GlobalSettings.sentenceFilterTerm,
                                    s.getSentence())).toList());
                } else {
                    sentences.addAll(results);
                }
            }
        }

        return sentences;
    }

    /**
     * Takes the step description string and returns a list containing all sentences from the
     * description where the searched verb occurs as single strings inside the list.
     * @param desc
     * @return
     */
    private ArrayList<String> splitUpStepDescription(String desc) {
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

    private ArrayList<VerbUsageSentence> splitSentencesInParts(ArrayList<String> sentences) {
        ArrayList<VerbUsageSentence> results = new ArrayList<>();

        for(String sent : sentences) {
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
            results.add(new VerbUsageSentence(verb, sent, obj.toString().trim(), prep, loc.toString().trim()));
        }

        return results;
    }
}