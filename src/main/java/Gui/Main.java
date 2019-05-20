package Gui;

import Data.DataNode;
import Data.LabelsTypes;
import Data.OwnDataSerializator;
import Data.XmlSerializator;
import Logic.Classificators.FeatureClassificator;
import Logic.Classificators.IClassificator;
import Logic.Classificators.SimilarityClassificator;
import Logic.Extraction.ExtractionManager;
import Logic.Extraction.HighestFrequencyWord;
import Logic.Extraction.TFIDF;
import Logic.Features.*;
import Logic.Metrics.ChebyshevMetric;
import Logic.Metrics.EuclideanMetric;
import Logic.Metrics.IMetric;
import Logic.Metrics.ManhattanMetric;
import Logic.SimilarityMeasures.ISimilarityMeasure;
import Logic.SimilarityMeasures.NGramMeasure;
import Logic.SimilarityMeasures.NiewiadomskiNGramMeasure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ExtractionManager extractionManager = null;
        try {
            extractionManager = new ExtractionManager(40, new OwnDataSerializator(), "SPORT");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        System.out.println(extractionManager.getLearningData().size());
//        System.out.println(extractionManager.getTestingData().size());
////
//        System.out.println(extractionManager.getLearningData().get(0).stemmedWords);
//        System.out.println(extractionManager.getLearningData().get(0).label);

//
        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
        highestFrequencyWord.ChooseWords(extractionManager, 6, "basketball");
        highestFrequencyWord.ChooseWords(extractionManager, 6, "hockey");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "gold");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "cotton");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "alum");
//        highestFrequencyWord.ChooseWords(extractionManager, 6, "silver");

//
//        TFIDF tfidf = new TFIDF();
//        extractionManager.createLearningDataWords("basketball");
//        tfidf.ChooseMainWordsForCountries(extractionManager, "basketball", 6);
//        extractionManager.createLearningDataWords("hockey");
//        tfidf.ChooseMainWordsForCountries(extractionManager, "hockey", 6);
//        extractionManager.createLearningDataWords("gold");
//        tfidf.ChooseMainWordsForCountries(extractionManager, "gold", 6);
//        extractionManager.createLearningDataWords("cotton");
//        tfidf.ChooseMainWordsForCountries(extractionManager, "cotton", 6);
//        extractionManager.createLearningDataWords("alum");
//        tfidf.ChooseMainWordsForCountries(extractionManager, "alum", 6);
//        extractionManager.createLearningDataWords("silver");
//        tfidf.ChooseMainWordsForCountries(extractionManager, "silver", 6);


        List<IFeature> chosenFeatures = Arrays.asList(
//                new FirstVowelWordCounter(),
//                new LastVowelWordCounter(),
//                new MediumWordCounter(),

                new NumberOfKeywords1(extractionManager.getKeyWords()),
                new NumberOfKeywords2(extractionManager.getKeyWords()),

//                new NumberOfKeywords3(extractionManager.getKeyWords()),
//                new NumberOfKeywords4(extractionManager.getKeyWords()),
//                new NumberOfKeywords5(extractionManager.getKeyWords()),
//                new NumberOfKeywords6(extractionManager.getKeyWords()),

                new LongWordCounter(),
                new ShortWordCounter(extractionManager.getKeyWords()),
                new TextLengthCounter(),//byly
                new Keywords25(extractionManager.getKeyWords()),//byly
                new Keywords252(extractionManager.getKeyWords()),//byly
//                new Keywords253(extractionManager.getKeyWords()),//byly
//                new Keywords254(extractionManager.getKeyWords()),//byly
//                new Keywords255(extractionManager.getKeyWords()),//byly
//                new Keywords256(extractionManager.getKeyWords()),//byly

                new Keywords75(extractionManager.getKeyWords()),
                new Keywords752(extractionManager.getKeyWords()),

//                new Keywords753(extractionManager.getKeyWords()),
//                new Keywords754(extractionManager.getKeyWords()),
//                new Keywords755(extractionManager.getKeyWords()),
//                new Keywords756(extractionManager.getKeyWords()),
                new MostCommonKeyword(extractionManager.getKeyWords()),//byly
                new MostCommonKeyword2(extractionManager.getKeyWords()),//byly
//                new MostCommonKeyword4(extractionManager.getKeyWords()),//byly
//                new MostCommonKeyword5(extractionManager.getKeyWords()),//byly
//                new MostCommonKeyword6(extractionManager.getKeyWords()),//byly
//                new MostCommonKeywords3(extractionManager.getKeyWords()),//byly
                new UpperCaseWordCounter(extractionManager.getKeyWords())//byly

//                new NiewiadomskiNGramMeasure()
        );

        int dobrze = 0;
        int zle = 0;
        IMetric metric = new EuclideanMetric();
        int kk = 0;
        for(int j = 0; j < 5; ++j){
            if(j == 0){
                kk = 1;
            }
            if(j == 1){
                kk = 3;
            }
            if(j == 2){
                kk = 6;
            }
            if(j == 3){
                kk = 10;
            }
            if(j == 4){
                kk = 20;
            }
            for(int i = 0; i < 1; ++i){
                if(i == 0){
                    metric = new EuclideanMetric();
                }
                else if(i ==1){
                    metric = new ChebyshevMetric();

                }else if(i ==2){
                    metric = new ManhattanMetric();

                }
                IClassificator clas = new FeatureClassificator(extractionManager.getLearningData(), chosenFeatures, kk, metric);

                Map<String, Integer> howManyGood = new HashMap<>();
                for(String s : LabelsTypes.chosen)
                {
                    howManyGood.put(s, 0);
                }

                Map<String, Integer> howManyBad = new HashMap<>();
                for(String s : LabelsTypes.chosen)
                {
                    howManyBad.put(s, 0);
                }
                int k = 1;
                for(DataNode node : extractionManager.getTestingData())
                {
                    String shouldBe = node.label;
                    String classified = clas.classify(node);
                    if(shouldBe.equals(classified))
                    {
                        howManyGood.put(shouldBe, howManyGood.get(shouldBe)+1);
                    }
                    else
                    {
                        howManyBad.put(shouldBe, howManyBad.get(shouldBe)+1);
                    }
//            System.out.println(shouldBe + "    " + classified);
                }
//                System.out.println("good: ");
                int a = 0;
                int b = 0;
                for(Map.Entry e : howManyGood.entrySet()){
//                    System.out.println(e.getKey() + "    " + e.getValue());
                    a += (int)e.getValue();
                    dobrze += (int)e.getValue();
                }
//                System.out.println("bad: ");
                for(Map.Entry e : howManyBad.entrySet()){
//                    System.out.println(e.getKey() + "    " + e.getValue());
                    b += (int)e.getValue();
                    zle += (int)e.getValue();
                }
                System.out.println("Procent: " + (double)a/(double)(a+b));
                System.out.println("Dobrze: " + dobrze);
                System.out.println("Zle: " + zle);
                System.out.println();
                dobrze = 0;
                zle = 0;
            }
        }
//        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
//        extractionManager.createLearningDataWords("canada");
//        highestFrequencyWord.ChooseWords(extractionManager, 20, "canada");
    }
}