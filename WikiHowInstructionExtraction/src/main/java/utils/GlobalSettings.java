package utils;

import model.CuttingVerb;

public class GlobalSettings {
    /**
     * Visualize the results in a JTable instead of "just" printing using the console
     */
    public final static boolean VISUALIZE_RESULTS = false;
    /**
     * When this flag is set to true, a general overview over the results is used, consisting of these five parts of an article:
     * title, title description, method name, step headline, step description.
     * Otherwise, the different sentences from the step description are split up and analyzed.
     */
    public final static boolean OVERVIEW_EXTRACTION = false;
    /**
     * Only include articles where the PARENT category is equal to the {@link GlobalSettings#relevantCategoryParent} string
     */
    public final static boolean CATEGORY_EXCLUSION = true;
    /**
     * Only include articles where ANY category is equal to the {@link GlobalSettings#relevantCategoryAny} string
     */
    public final static boolean ONLY_CATEGORY_FILTER = false;
    /**
     * Use the {@link analysis.VerbOccurrencePrinter} to count the occurrences of the {@link GlobalSettings#searchVerb}
     * in different parts of the article. These parts are: title, title description, method name, step headline, step description
     */
    public final static boolean OCCURRENCE_PRINTING = false;
    /**
     * Use the {@link analysis.CategoryDistributionAnalyzer} to count the occurrences of the
     * {@link GlobalSettings#searchVerb} in different parent categories
     */
    public final static boolean CATEGORY_DISTRIBUTION = false;
    /**
     * Use the {@link analysis.ImageAndVideoCounter} to analyze the usage of the images and videos in the found articles.
     */
    public final static boolean IMAGE_VIDEO_COUNTER = false;
    /**
     * This filter is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * Only include sentences where the part BEFORE the preposition is equal to the {@link GlobalSettings#beforeFilterString}.
     */
    public final static boolean FILTER_BEFORE_PART = false;
    /**
     * This filter is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * Only include sentences where the part AFTER the preposition is equal to the {@link GlobalSettings#afterFilterString}.
     */
    public final static boolean FILTER_AFTER_PART = true;
    /**
     * This filter is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * Only include sentences that contain the {@link GlobalSettings#sentenceFilterString}.
     */
    public final static boolean FILTER_SENTENCE = false;
    /**
     * This flag is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * Exclude all sentences where the past tense of the {@link GlobalSettings#searchVerb} occurs, since it e.g.
     * can be mistaken for an adjective (e.g. diced).
     */
    public final static boolean EXCLUDE_PAST_TENSE = false;
    /**
     * This flag is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * Exclude all sentences with an empty preposition.
     */
    public final static boolean EXCLUDE_EMPTY_PREPOSITIONS = false;
    /**
     * This flag is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * Use the {@link analysis.SentencePartAnalysis} to count the occurrences of the {@link GlobalSettings#searchVerb} in different parts of the sentence.
     */
    public final static boolean SENTENCE_PARTS_ANALYSIS = false;
    /**
     * This flag is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * Use the {@link analysis.PrepositionDistributionAnalyzer} to count the occurrences of the different prepositions.
     */
    public final static boolean PREPOSITION_DISTRIBUTION = false;
    // true: only allow prepositions that are summarized in the prepositions array in the PoSTagger
    /**
     * This filter is only applied when sentences are analyzed ({@link GlobalSettings#OVERVIEW_EXTRACTION} == False).
     * When this flag is set to true, only prepositions collected in the {@link nlp.PoSTagger#prepositions} array are
     * counted as valid. Otherwise, everything tagged as a preposition by the {@link nlp.PoSTagger} is a valid preposition.
     */
    public final static boolean EXCLUDE_PREPOSITIONS = true;

    /**
     * The {@link CuttingVerb} that is currently searched for and analyzed
     */
    public final static CuttingVerb searchVerb = CuttingVerb.CUT;
    /**
     * The parent category used for exclusion through the {@link GlobalSettings#CATEGORY_EXCLUSION} flag
     */
    public final static String relevantCategoryParent = "Food and Entertaining";
    /**
     * The category used for inclusion through the {@link GlobalSettings#ONLY_CATEGORY_FILTER} flag
     */
    public final static String relevantCategoryAny = "Food Cutting Techniques";
    /**
     * The string used for filtering the sentence part BEFORE the preposition through the {@link GlobalSettings#FILTER_BEFORE_PART} flag
     */
    public final static String beforeFilterString = "";
    /**
     * The string used for filtering the sentence part AFTER the preposition through the {@link GlobalSettings#FILTER_AFTER_PART} flag
     */
    public final static String afterFilterString = "cubes";
    /**
     * The string used for filtering the sentence through the {@link GlobalSettings#FILTER_SENTENCE} flag
     */
    public final static String sentenceFilterString = "meat";
}