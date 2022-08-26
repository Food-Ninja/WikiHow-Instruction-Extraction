// import controller.Analyzer;
import controller.JSONFileReader;
import model.DeconstructedStepSentence;
import model.WikiHowStep;
import utils.GlobalSettings;
import utils.PoSTagger;
import utils.SentenceExtractor;
import view.ResultVisualizer;

import java.util.ArrayList;

public class ExtractionStarter {
    public static void main(String[] args) {
        //Analyzer analyzer = new Analyzer();
        JSONFileReader reader = new JSONFileReader();
        ResultVisualizer visualizer = new ResultVisualizer();
        PoSTagger.initialize();

        ArrayList<WikiHowStep> steps = reader.extractStepsFromFiles();
        System.out.println(steps.size() + " steps containing \"" + GlobalSettings.searchTerm + "\"");
        ArrayList<DeconstructedStepSentence> sentences = new ArrayList<>();
        if(!GlobalSettings.OVERVIEW_EXTRACTION) {
            sentences = SentenceExtractor.deconstructStepsIntoSentenceParts(steps);
            System.out.println(sentences.size() + " sentences containing \"" + GlobalSettings.searchTerm + "\"" +
                    (GlobalSettings.FILTER_TARGET ? " and \"" + GlobalSettings.targetFilterTerm + "\"" : "") +
                    (GlobalSettings.FILTER_SENTENCE ? " and \"" + GlobalSettings.sentenceFilterTerm + "\"" : "") +
                    (GlobalSettings.FILTER_LOCATION ? " and \"" + GlobalSettings.locationFilterTerm + "\"" : ""));
        }
        visualizer.visualizeResults(steps, sentences);
    }
}