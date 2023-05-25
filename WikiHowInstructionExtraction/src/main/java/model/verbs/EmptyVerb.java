package model.verbs;

/**
 * This enum contains only a single possible value which returns the wildcard character ('.').
 * Since this character is used in a regex-based search, no filtering will be employed and all possible articles are
 * returned.
 */
public enum EmptyVerb implements ISearchableVerb {
    NONE;

    @Override
    public boolean doesPresentEqualPast() {
        return false;
    }

    @Override
    public String getPresentForm() {
        return ".";
    }

    @Override
    public String getPastForm() {
        return ".";
    }

    @Override
    public String getParticipleForm() {
        return ".";
    }
}