package io;

import model.WikiHowStep;
import model.verbs.ISearchableVerb;
import utils.GlobalSettings;
import utils.OccurrenceChecker;

import java.util.ArrayList;
import java.util.List;

public class StepFilter {

    public static ArrayList<WikiHowStep> filterGivenSteps(ArrayList<WikiHowStep> steps) {
        return filterGivenSteps(steps, GlobalSettings.searchVerb);
    }

    public static ArrayList<WikiHowStep> filterGivenSteps(ArrayList<WikiHowStep> steps, ISearchableVerb verb) {
        List<WikiHowStep> list = steps.stream().filter(s -> OccurrenceChecker.checkOccurrence(s.getDescription(), verb)).toList();
        return new ArrayList<>(list);
    }
}