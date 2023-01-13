package model.verbs;

public interface ISearchableVerb {
    String getPresentForm();

    String getPastForm();

    String getParticipleForm();

    boolean doesPresentEqualPast();
}