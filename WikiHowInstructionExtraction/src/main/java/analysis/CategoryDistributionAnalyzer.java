package analysis;

import model.WikiHowStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CategoryDistributionAnalyzer implements IStepAnalyzer {
    @Override
    public void analyzeAndPrintResults(ArrayList<WikiHowStep> steps) {
        HashMap<String, Integer> catDist = new HashMap<String, Integer>();
        for(WikiHowStep inst : steps) {
            String parentCat = inst.getCategories().isEmpty() ? "No Category" : inst.getCategories().get(0);
            if(catDist.containsKey(parentCat)) {
                catDist.put(parentCat, catDist.get(parentCat) + 1);
            } else {
                catDist.put(parentCat, 1);
            }
        }
        Iterator<Map.Entry<String, Integer>> keys = catDist.entrySet().iterator();
        while (keys.hasNext()) {
            Map.Entry<String, Integer> next = keys.next();
            System.out.println("Category: " + next.getKey() + " Entries: " + next.getValue());
        }
    }
}