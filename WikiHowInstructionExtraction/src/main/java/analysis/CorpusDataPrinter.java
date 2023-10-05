package analysis;

import utils.GlobalSettings;

public class CorpusDataPrinter {
    private static int articleCounter = 0;
    private static int articleCategoryCounter = 0;
    private static int methodCounter = 0;
    private static int methodCategoryCounter = 0;
    private static int stepCounter = 0;
    private static int stepCategoryCounter = 0;
    private static int sentenceCounter = 0;
    private static int sentenceCategoryCounter = 0;

    public static void printResults() {
        System.out.printf("Articles: %d\n", articleCounter);
        System.out.printf("Articles in the %s category: %d\n", GlobalSettings.relevantCategoryParent, articleCategoryCounter);
        System.out.printf("Methods: %d\n", methodCounter);
        System.out.printf("Methods in the %s category: %d\n", GlobalSettings.relevantCategoryParent, methodCategoryCounter);
        System.out.printf("Steps: %d\n", stepCounter);
        System.out.printf("Steps in the %s category: %d\n", GlobalSettings.relevantCategoryParent, stepCategoryCounter);
        System.out.printf("Sentences in the step description: %d\n", sentenceCounter);
        System.out.printf("Sentences in the %s category: %d\n", GlobalSettings.relevantCategoryParent, sentenceCategoryCounter);
    }

    public static void countArticle(boolean isRightCategory) {
        articleCounter++;
        if (isRightCategory) {
            articleCategoryCounter++;
        }
    }

    public static void countMethods(int methods, boolean isRightCategory) {
        methodCounter += methods;
        if (isRightCategory) {
            methodCategoryCounter += methods;
        }
    }

    public static void countSteps(int steps, boolean isRightCategory) {
        stepCounter += steps;
        if (isRightCategory) {
            stepCategoryCounter += steps;
        }
    }

    public static void countStepSentences(int sentences, boolean isRightCategory) {
        sentenceCounter += sentences;
        if (isRightCategory) {
            sentenceCategoryCounter += sentences;
        }
    }
}