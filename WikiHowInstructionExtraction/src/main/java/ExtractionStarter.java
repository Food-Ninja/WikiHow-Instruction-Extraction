// import analysis.Analyzer;

import analysis.*;
import io.JSONFileReader;
import io.ResultVisualizer;
import model.DeconstructedStepSentence;
import model.WikiHowStep;
import nlp.CoreferenceResolver;
import utils.GlobalSettings;
import nlp.PoSTagger;
import nlp.SentencePartsExtractor;

import java.util.ArrayList;

public class ExtractionStarter {

    private static ArrayList<IStepAnalyzer> stepAnalyzers = new ArrayList<>();
    private static ArrayList<ISentenceAnalyzer> sentenceAnalyzers = new ArrayList<>();

    public static void main(String[] args) {
        JSONFileReader reader = new JSONFileReader();
        ResultVisualizer visualizer = new ResultVisualizer();
        PoSTagger.initialize();
        addAnalyzers();

        ArrayList<WikiHowStep> steps = reader.extractStepsFromFiles();
        System.out.println(steps.size() + " steps containing \"" + GlobalSettings.searchTerm + "\"\n");
        callStepAnalyzers(steps);

        ArrayList<DeconstructedStepSentence> sentences = new ArrayList<>();
        if(!GlobalSettings.OVERVIEW_EXTRACTION) {
            sentences = SentencePartsExtractor.deconstructStepsIntoSentenceParts(steps);
            CoreferenceResolver.resolveCoreferences(sentences);
            callSentenceAnalyzers(sentences);
            System.out.println(sentences.size() + " sentences containing \"" + GlobalSettings.searchTerm + "\"" +
                    (GlobalSettings.FILTER_TARGET ? " and \"" + GlobalSettings.targetFilterTerm + "\"" : "") +
                    (GlobalSettings.FILTER_SENTENCE ? " and \"" + GlobalSettings.sentenceFilterTerm + "\"" : "") +
                    (GlobalSettings.FILTER_LOCATION ? " and \"" + GlobalSettings.locationFilterTerm + "\"" : ""));
        }
        visualizer.visualizeResults(steps, sentences);
    }

    private static void addAnalyzers() {
        if(GlobalSettings.OCCURRENCE_PRINTING) {
            stepAnalyzers.add(new VerbOccurrencePrinter());
        }

        if(GlobalSettings.CATEGORY_DISTRIBUTION) {
            stepAnalyzers.add(new CategoryDistributionAnalyzer());
        }

        if(GlobalSettings.IMAGE_VIDEO_COUNTER) {
            stepAnalyzers.add(new ImageAndVideoCounter());
        }

        if(!GlobalSettings.OVERVIEW_EXTRACTION && GlobalSettings.SENTENCE_PARTS_ANALYSIS) {
            sentenceAnalyzers.add(new SentencePartAnalysis());
        }

        if(!GlobalSettings.OVERVIEW_EXTRACTION && GlobalSettings.PREPOSITION_DISTRIBUTION) {
            sentenceAnalyzers.add(new PrepositionDistributionAnalyzer());
        }

        if(!GlobalSettings.OVERVIEW_EXTRACTION) {
            sentenceAnalyzers.add(new MeasurementOccurrencePrinter());
        }

        sentenceAnalyzers.add(new CoreferenceAnalyzer());
    }

    private static void callStepAnalyzers(ArrayList<WikiHowStep> steps) {
        for(IStepAnalyzer analyzer : stepAnalyzers) {
            System.out.print("# " + analyzer.getClass().getSimpleName() + " results:\n");
            analyzer.analyzeAndPrintResults(steps);
            System.out.print("\n");
        }
    }

    private static void callSentenceAnalyzers(ArrayList<DeconstructedStepSentence> sentences) {
        for(ISentenceAnalyzer analyzer : sentenceAnalyzers) {
            System.out.print("# " + analyzer.getClass().getSimpleName() + " results:\n");
            analyzer.analyzeAndPrintResults(sentences);
            System.out.print("\n");
        }
    }
}