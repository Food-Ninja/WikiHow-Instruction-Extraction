package utils;

public class GlobalSettings {
    // true: the results are printed as a JTable
    public final static boolean VISUALIZE_RESULTS = true;
    // true: all articles from the parent category != relevantCategoryParent are excluded from the results
    public final static boolean CATEGORY_EXCLUSION = true;
    // true: search only for articles where any category == relevantCategoryAny
    public final static boolean ONLY_CATEGORY_FILTER = false;
    // true: the occurrences of the searchTerm in the different parts of the article are counted & printed
    public final static boolean OCCURRENCE_PRINTING = false;
    // true: counts & prints the distribution of the searchTerm in the parent categories
    public final static boolean CATEGORY_DISTRIBUTION = false;
    // true: the general overview consisting of title, categories, method name, step headline and
    // step description is visualized
    public final static boolean OVERVIEW_EXTRACTION = false;
    // true: all target objects that do not equal the targetFilterTerm are excluded from the results
    public final static boolean FILTER_TARGET = false;
    // true: all target locations that do not equal the locationFilterTerm are excluded from the results
    public final static boolean FILTER_LOCATION = false;
    // true: exclude all detailed results where the sentence does not contain the word sentenceFilterTerm
    public final static boolean FILTER_SENTENCE = false;
    // true: exclude all detailed results where the preposition is empty
    public final static boolean EXCLUDE_EMPTY_PREPOSITIONS = false;
    // true: count & print the usage of the different sentence parts in the detailed sentence results
    public final static boolean SENTENCE_PARTS_ANALYSIS = false;
    // true: count & print the distribution of the prepositions in the detailed sentence results
    public final static boolean PREPOSITION_DISTRIBUTION = true;
    // true: exclude the past tense of the search term for whatever reason (e.g. can be mistaken for adjective)
    public final static boolean EXCLUDE_PAST_TENSE = true;
    // true: count occurrences of images and videos in steps
    public final static boolean IMAGE_VIDEO_COUNTER = false;
    // true: use functionality provided by the Stanford CoreNLP API
    public final static boolean USE_STANFORD_NLP_API = true;

    public final static String searchTerm = "dice";
    public final static String searchTermPast = "diced";
    public final static String searchTermParticiple = "dicing";
    public final static String targetFilterTerm = "potatoes";
    public final static String locationFilterTerm = "";
    public final static String sentenceFilterTerm = "off";
    public final static String relevantCategoryParent = "Food and Entertaining";
    public final static String relevantCategoryAny = "Food Cutting Techniques";
}
