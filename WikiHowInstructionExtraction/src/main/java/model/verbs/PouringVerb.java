package model.verbs;

public enum PouringVerb implements ISearchableVerb {
    // "Pour" and its different synonyms
    BELCH("belch", "belched", "belching"),
    BLEED("bleed", "bled", "bleeding"),
    BLOW("blow", "blew", "blowing"),
    BOIL("boil", "boiled", "boiling"),
    BUBBLE("bubble", "bubbled", "bubbling"),
    CLEAN("clean", "cleaned", "cleaning"),
    CLEAR("clear", "cleared", "clearing"),
    DECANT("decant", "decanted", "decanting"),
    DRIBBLE("dribble", "dribbled", "dribbling"),
    DRIP("drip", "dripped", "dripping"),
    DRIZZLE("drizzle", "drizzled", "drizzling"),
    DROOL("drool", "drooled", "drooling"),
    DROP("drop", "dropped", "dropping"),
    EFFUSE("effuse", "effused", "effusing"),
    EMANATE("emanate", "emanated", "emanating"),
    EXUDE("exude", "exuded", "exuding"),
    FOAM("foam", "foamed", "foaming"),
    FOG("fog", "fogged", "fogging"),
    FREEZE("freeze", "froze", "freezing"),
    FRY("fry", "fried", "frying"),
    GRILL("grill", "grilled", "grilling"),
    GUSH("gush", "gushed", "gushing"),
    GUST("gust", "gusted", "gusting"),
    HAIL("hail", "hailed", "hailing"),
    HARDBOIL("hardboil", "hardboiled", "hardboiling"),
    HOWL("howl", "howled", "howling"),
    IRON("iron", "ironed", "ironing"),
    LEAK("leak", "leaked", "leaking"),
    LIGHTNING("lightning", "lightned", "lightning"),
    MIST("mist", "misted", "misting"),
    MIZZLE("mizzle", "mizzled", "mizzling"),
    OOZE("ooze", "oozed", "oozing"),
    OVERBAKE("overbake", "overbaked", "overbaking"),
    PELT("pelt", "pelted", "pelting"),
    POACH("poach", "poached", "poaching"),
    POUR("pour", "poured", "pouring"),
    PRECIPITATE("precipitate", "precipitated", "precipitating"),
    PUFF("puff", "puffed", "puffing"),
    PULLULATE("pullulate", "pullulated", "pullulating"),
    RADIATE("radiate", "radiated", "radiating"),
    RAIN("rain", "rained", "raining"),
    REGURGITATE("regurgitate", "regurgitated", "regurgitating"),
    ROAR("roar", "roared", "roaring"),
    ROAST("roast", "roasted", "roasting"),
    SCRAMBLE("scramble", "scrambled", "scrambling"),
    SEEP("seep", "seeped", "seeping"),
    SET("set", "set", "setting"),
    SHED("shed", "shed", "shedding"),
    SHEET("sheet", "sheeted", "sheeting"),
    SHOWER("shower", "showered", "showering"),
    SLEET("sleet", "sleeted", "sleeting"),
    SLOP("slop", "slopped", "slopping"),
    SLOSH("slosh", "sloshed", "sloshing"),
    SLUICE("sluice", "sluiced", "sluicing"),
    SNOW("snow", "snowed", "snowing"),
    SOFTBOIL("softboil", "softboiled", "softboiling"),
    SPEW("spew", "spewed", "spewing"),
    SPILL("spill", "spilt", "spilling"),
    SPIRT("spirt", "spirted", "spirting"),
    SPIT("spit", "spit", "spitting"),
    SPOT("spot", "spotted", "spotting"),
    SPOUT("spout", "spouted", "spouting"),
    SPRINKLE("sprinkle", "sprinkled", "sprinkling"),
    SPROUT("sprout", "sprouted", "sprouting"),
    SPURT("spurt", "spurted", "spurting"),
    SQUIRT("squirt", "squirted", "squirting"),
    STEAM("steam", "steamed", "steaming"),
    STORM("storm", "stormed", "storming"),
    STREAM("stream", "streamed", "streaming"),
    SWARM("swarm", "swarmed", "swarming"),
    SWEAT("sweat", "sweated", "sweating"),
    SWELTER("swelter", "sweltered", "sweltering"),
    TEEM("teem", "teemed", "teeming"),
    THAW("thaw", "thawed", "thawing"),
    THUNDER("thunder", "thundered", "thundering"),
    TOAST("toast", "toasted", "toasting"),
    TRANSFUSE("transfuse", "transfused", "transfusing"),
    WASH("wash", "washed", "washing");

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