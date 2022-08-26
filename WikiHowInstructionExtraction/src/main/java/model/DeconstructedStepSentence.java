package model;

public class DeconstructedStepSentence {
    private final int orderNo;
    private final String verb;
    private final String preposition;
    private final String beforePrep;
    private final String afterPrep;
    private final String completeSentence;
    private final WikiHowStep step;

    public DeconstructedStepSentence(int orderNo, String verb, String preposition, String beforePrep, String afterPrep, String sent, WikiHowStep step) {
        this.orderNo = orderNo;
        this.verb = verb;
        this.preposition = preposition;
        this.beforePrep = beforePrep;
        this.afterPrep = afterPrep;
        this.completeSentence = sent;
        this.step = step;
    }

    public String getVerb() {
        return verb;
    }

    public String getPreposition() {
        return preposition;
    }

    public String getBeforePrep() {
        return beforePrep;
    }

    public String getAfterPrep() {
        return afterPrep;
    }

    public String getCompleteSentence() {
        return completeSentence;
    }
}