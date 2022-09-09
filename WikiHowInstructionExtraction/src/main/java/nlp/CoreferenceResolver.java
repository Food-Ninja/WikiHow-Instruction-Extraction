package nlp;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import model.DeconstructedStepSentence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class CoreferenceResolver {
    public final static String[] coreferenceWords = new String[]{"them", "it"};

    private static StanfordCoreNLP pipeline;

    public static void resolveCoreferences(ArrayList<DeconstructedStepSentence> sentences) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref");
        pipeline = new StanfordCoreNLP(props);

        for(DeconstructedStepSentence sent : sentences) {
            for(String cor : coreferenceWords) {
                if(sent.checkIfBeforeTextContainsGivenTerm(cor)) {
                    String resolution = resolveCoreference(sent);
                    System.out.println(String.format("Before: %s - Resolution: %s - Sentence: %s", sent.getBeforePrep(), resolution, sent.getCompleteSentence()));
                    if(checkResolution(resolution)) {
                        sent.replaceInBeforeSent(cor, resolution);
                    }
                }
            }
        }
    }

    private static String resolveCoreference(DeconstructedStepSentence sent) {
        Annotation document = new Annotation(sent.getCompleteSentence());
        pipeline.annotate(document);

        for (CorefChain chain : document.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
            if(chain.getMentionsInTextualOrder().size() <= 1) {
                continue;
            }

            for (CorefChain.CorefMention mention : chain.getMentionsInTextualOrder()) {
                if(Arrays.stream(coreferenceWords).anyMatch(mention.mentionSpan::equalsIgnoreCase)) {
                    if(mention.mentionSpan.equals("you")) {
                        continue;
                    }
                    return chain.getRepresentativeMention().mentionSpan;
                }
            }
        }
        return sent.getBeforePrep();
    }

    private static boolean checkResolution(String resolution) {
        return PoSTagger.checkForNoun(resolution);
    }
}