package Gui;

import Logic.Extraction.ExtractionManager;

public class Main {

    public static void main(String[] args) {
        ExtractionManager extractionManager = new ExtractionManager(60);
        System.out.println(extractionManager.getLearningData().size());
        System.out.println(extractionManager.getTestingData().size());

//        Map.Entry<List<String>, String> entry = extractionManager.getLearningData().entrySet().iterator().next();
//        System.out.println(entry.getKey());
//        System.out.println(entry.getValue());
    }
}
