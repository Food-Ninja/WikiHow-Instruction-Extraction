package model;

public enum CuttingVerb {
    // "Cut" and its different synonyms taken from Thesaurus, VerbNet and WordNet.
    // We only include verbs relevant for the cooking domain.
    // The past and participle forms are taken from https://pasttenses.com
    CUT("cut", "cut", "cutting"),
    CARVE("carve", "carved", "carving"),
    SLASH("slash", "slashed", "slashing"),
    SLICE("slice", "sliced", "slicing"),
    DICE("dice", "diced", "dicing"),
    HASH("hash", "hashed", "hashing"),
    MINCE("mince", "minced", "mincing"),
    QUARTER("quarter", "quartered", "quartering"),
    SAW("saw", "sawed", "sawing"),
    SLIT("slit", "slit", "slitting"),
    SLIVER("sliver", "slivered", "slivering"),
    SNIP("snip", "snipped", "snipping"),
    HALVE("halve", "halved", "halving"),
    CUBE("cube", "cubed", "cubing"),
    SEVER("sever", "severed", "severing"),
    CROSSCUT("crosscut", "crosscut", "crosscutting"),
    INCISE("incise", "incise", "incising"),
    JAG("jag", "jag", "jagging"),
    JULIENNE("julienne", "julienned", "julienning"),
    TRENCH("trench", "trenched", "trenching"),
    TRISECT("trisect", "trisected", "trisecting");

    public final String present;

    public final String past;

    public final String participle;

    CuttingVerb(String verb, String past, String participle) {
        this.present = verb;
        this.past = past;
        this.participle = participle;
    }

    public boolean doesPresentEqualPast() {
        return present.equals(past);
    }
}