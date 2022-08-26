package model;

public class VerbUsageSentence {
    private final String verb;
    private final String sentence;

    private final String targetObject;

    private final String preposition;

    private final String targetLocation;

    public VerbUsageSentence(String verb, String sentence, String targetObject, String preposition, String targetLocation) {
        this.verb = verb;
        this.sentence = sentence;
        this.targetObject = targetObject;
        this.preposition = preposition;
        this.targetLocation = targetLocation;
    }

    public String getVerb() {
        return verb;
    }

    public String getSentence() {
        return sentence;
    }

    public String getTargetObject() {
        return targetObject;
    }

    public String getPreposition() {
        return preposition;
    }

    public String getTargetLocation() {
        return targetLocation;
    }
}
