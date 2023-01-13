package model.verbs;

public enum PouringVerb implements ISearchableVerb {
    // "Pour" and its different synonyms
    POUR("pour", "poured", "pouring");

    private final String present;

    private final String past;

    private final String participle;

    PouringVerb(String verb, String past, String participle) {
        this.present = verb;
        this.past = past;
        this.participle = participle;
    }

    @Override
    public boolean doesPresentEqualPast() {
        return present.equals(past);
    }

    @Override
    public String getPresentForm() {
        return present;
    }

    @Override
    public String getPastForm() {
        return past;
    }

    @Override
    public String getParticipleForm() {
        return participle;
    }
}