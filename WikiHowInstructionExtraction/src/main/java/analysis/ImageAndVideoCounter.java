package analysis;

import model.WikiHowArticle;
import model.WikiHowStep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class ImageAndVideoCounter implements IStepAnalyzer{
    private static int videoCounter = 0;
    private int imageCounter = 0;

    @Override
    public void analyzeAndPrintResults(ArrayList<WikiHowStep> steps) {
        countStepsWithImage(steps);
        System.out.println("Images: " + imageCounter);
        System.out.println("Videos: " + videoCounter);
    }

    public static void countIfArticleHasVideo(WikiHowArticle article) {
        String videoLink = article.getVideo();
        if(videoLink != null && !videoLink.isEmpty()) {
            videoCounter++;
        }
    }

    private void countStepsWithImage(ArrayList<WikiHowStep> steps) {
        for(WikiHowStep step : steps) {
            String imgLink = step.getImage();
            if(imgLink == null || imgLink.isEmpty()) {
                continue;
            }

            File file = new File(imgLink);
            String mimetype = null;
            try {
                mimetype = Files.probeContentType(file.toPath());
                if(mimetype == null) {
                    return;
                }
                if (mimetype.split("/")[0].equals("image")) {
                    imageCounter++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}