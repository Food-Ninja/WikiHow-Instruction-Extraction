package utils;

import controller.Analyzer;
import controller.JSONFileReader;
import model.ExtractedInstruction;
import model.VerbUsageSentence;
import view.ResultVisualizer;

import java.util.ArrayList;

public class ExtractionStarter {
    public static void main(String[] args) {
        Analyzer analyzer = new Analyzer();
        JSONFileReader reader = new JSONFileReader(analyzer);
        ResultVisualizer visualizer = new ResultVisualizer();
        PoSTagger.initialize();

        if(GlobalSettings.OVERVIEW_EXTRACTION) {
            ArrayList<ExtractedInstruction> results = reader.extractInstructionsFromAllFiles();
            analyzer.analyzeOverviewResults(results);
            System.out.println(results.size() + " results for \"" + GlobalSettings.searchTerm + "\"");
            visualizer.visualizeResults(results, null);
        } else {
            ArrayList<VerbUsageSentence> results = reader.extractSentencesFromAllFiles();
            analyzer.analyzeDetailedResults(results);
            System.out.println(results.size() + " results for \"" + GlobalSettings.searchTerm + "\"" +
                    (GlobalSettings.FILTER_TARGET ? " and \"" + GlobalSettings.targetFilterTerm + "\"" : "") +
                    (GlobalSettings.FILTER_SENTENCE ? " and \"" + GlobalSettings.sentenceFilterTerm + "\"" : "") +
                    (GlobalSettings.FILTER_LOCATION ? " and \"" + GlobalSettings.locationFilterTerm + "\"" : ""));
            visualizer.visualizeResults(null, results);
        }
    }
}
