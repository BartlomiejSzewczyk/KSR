package Gui;

import Data.*;
import Logic.Classificators.FeatureClassificator;
import Logic.Classificators.IClassificator;
import Logic.Classificators.SimilarityClassificator;
import Logic.Extraction.DataStemmer;
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
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WindowMain extends Application {

    //region params
    private Group root = new Group();
    private Font myFont = new Font("Serif", 20);
    private Label chooseDataLabel = new Label();
    private ComboBox chooseDataComboBox;
    private Label chooseExtractionLabel = new Label();
    private ComboBox chooseExtractionComboBox;
    private Label chooseMeasureLabel = new Label();
    private ComboBox chooseMeasureComboBox;
    private Label chooseMetricLabel = new Label();
    private ComboBox chooseMEtricComboBox;
    private Label chooseFeaturesLabel = new Label();
    private ComboBox chooseFeaturesComboBox;
    private Label chooseKLabel = new Label();
    private TextField chooseKTextField = new TextField();
    private Label chooseNLabel = new Label();
    private TextField chooseNTextField = new TextField();
    private Label choosePercentDataLabel = new Label();
    private TextField choosePercentDataTextField = new TextField();
    private Label falseResultLabel = new Label();
    private Label trueResultLabel = new Label();
    private TextField falseResultTextField = new TextField();
    private TextField trueResultTextField = new TextField();
    private Button calcuteButtonKNN = new Button();
    private Button calcuteButtonMeasure = new Button();
    private Button saveSettingsButton = new Button();
    private Button readSettingsButton = new Button();
    private CheckBox checkBox1 = new CheckBox();
    private CheckBox checkBox2 = new CheckBox();
    private CheckBox checkBox3 = new CheckBox();
    private CheckBox checkBox4 = new CheckBox();
    private CheckBox checkBox5 = new CheckBox();
    private CheckBox checkBox6 = new CheckBox();
    private CheckBox checkBox7 = new CheckBox();
    private CheckBox checkBox8 = new CheckBox();
    private CheckBox checkBox11 = new CheckBox();
    private CheckBox checkBox22 = new CheckBox();
    private TextField settingsPath = new TextField();
    private final JFileChooser fileChooser = new JFileChooser();
    private TitledPane chooseFeaturesTitledPane;
    private String category;
    private String extraction;
    private String metric;
    private Integer k;
    private Integer n;
    private Integer percent;
    private List<Integer> listFeatures = new ArrayList<>();
    private List<String> listNames = new ArrayList<>();
    private ConfigurationManager manager;
    private List<DataNode> coldStart;
    private String settingFilePath = "";
    private SaveData saveData = new SaveData("data.txt");
    private boolean data = true;
    //endregion

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        manager = new ConfigurationManager();
        chooseData();
        chooseExtraction();
        chooseMetric();
        chooseK();
        choosePercentData();
        chooseMeasure();
        chooseN();
        chooseFeatures();
        result();
        calcuteKNN();
        calcuteMeasure();
        saveSettings();
        readSettings();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);


        root.getChildren().addAll();
        Scene scene = new Scene (root, 800, 600);
        primaryStage.setTitle("Komputerowe systemy rozpoznawania");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void readSettings() {
        settingsPath.setLayoutX(20);
        settingsPath.setLayoutY(560);
        settingsPath.setPrefWidth(500);
        root.getChildren().add(settingsPath);
        readSettingsButton.setText("Wczytaj ustawienia");
        readSettingsButton.setLayoutX(600);
        readSettingsButton.setLayoutY(560);
        root.getChildren().add(readSettingsButton);
        readSettingsButton.setOnAction(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            String filePath = "";
            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();
                settingsPath.setText(filePath);
            }
        });
    }

    private void saveSettings() {
        saveSettingsButton.setText("Zapisz ustawienia  ");
        saveSettingsButton.setLayoutX(600);
        saveSettingsButton.setLayoutY(530);
        root.getChildren().add(saveSettingsButton);
        saveSettingsButton.setOnAction(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            String filePath = "";
            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();
                manager.saveConfigurationToFile(filePath);
            }
        });
    }

    private void calcuteKNN() {
        calcuteButtonKNN.setLayoutX(20);
        calcuteButtonKNN.setLayoutY(470);
        calcuteButtonKNN.setText("Klasyfikuj metodą k-NN");
        calcuteButtonKNN.setOnAction(e -> {
            getParamsKNN();
        });
        root.getChildren().add(calcuteButtonKNN);
    }

    private void getParamsKNN() {
        try{
            category = chooseDataComboBox.getValue().toString();
            extraction = chooseExtractionComboBox.getValue().toString();
            metric = chooseMEtricComboBox.getValue().toString();
            k = Integer.parseInt(chooseKTextField.getText());
            percent = Integer.parseInt(choosePercentDataTextField.getText());
            listFeatures.clear();
            if(checkBox1.isSelected()){
                listFeatures.add(1);
            }
            else if(checkBox2.isSelected()){
                listFeatures.add(2);
            }
            else if(checkBox3.isSelected()){
                listFeatures.add(3);
            }
            else if(checkBox4.isSelected()){
                listFeatures.add(4);
            }
            else if(checkBox5.isSelected()){
                listFeatures.add(5);
            }
            else if(checkBox6.isSelected()){
                listFeatures.add(6);
            }
            else if(checkBox7.isSelected()){
                listFeatures.add(7);
            }
            else if(checkBox8.isSelected()){
                listFeatures.add(8);
            }
            if(listFeatures.size() == 0){
                throw new Exception("size equals 0");
            }
        }catch(Exception e){
            return;
        }
        classifyKNN();
    }

    private void classifyKNN() {
        listNames.clear();
        if(category.equals("TOPICS")){
            listNames.add("gas");listNames.add("ship");listNames.add("gold");
            listNames.add("cotton");listNames.add("alum");listNames.add("silver");
        }else if(category.equals("PLACES")){
            listNames.add("usa");listNames.add("uk");listNames.add("west-germany");
            listNames.add("canada");listNames.add("japan");listNames.add("france");
        }else if(category.equals("SPORT")){
            listNames.add("hockey");listNames.add("basketball");
        }
        ExtractionManager extractionManager = null;
        HighestFrequencyWord highestFrequencyWord = new HighestFrequencyWord();
        TFIDF tfidf = new TFIDF();
        List<IFeature> chosenFeatures = new ArrayList<>();
        if(category.equals("TOPICS") || category.equals("PLACES")){
            try {
                extractionManager = new ExtractionManager(percent, new XmlSerializator(), category);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            settingFilePath = settingsPath.getText();
            if(settingFilePath.equals(""))
            {
                Collections.shuffle(extractionManager.getLearningData());
               // coldStart = extractionManager.getLearningData();
            }
            else
            {
                manager.readConfigurationFromFile(settingFilePath);
                extractionManager.setLearningData(manager.configuration.getColdStart());
                extractionManager.deleteStopwords(extractionManager.getLearningData());
                DataStemmer stemmer = new DataStemmer();
                stemmer.stemmizeData(extractionManager.getLearningData());
                //coldStart = manager.configuration.getColdStart();
            }
            if(extraction.equals("TF")){
                for(int i = 0; i < listNames.size(); ++i){
                    highestFrequencyWord.ChooseWords(extractionManager, 6, listNames.get(i));
                }
            }
            else if(extraction.equals("TFIDF")){
                for(int i = 0; i < listNames.size(); ++i){
                    extractionManager.createLearningDataWords(listNames.get(i));
                    tfidf.ChooseMainWordsForCountries(extractionManager, listNames.get(i), 6);
                }
            }
            if(checkBox1.isSelected()){
                chosenFeatures.add(new TextLengthCounter());
            }
            if(checkBox2.isSelected()){
                chosenFeatures.add(new UpperCaseWordCounter(extractionManager.getKeyWords()));
            }
            if(checkBox3.isSelected()){
                chosenFeatures.add(new Keywords25(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords252(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords253(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords254(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords255(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords256(extractionManager.getKeyWords()));
            }
            if(checkBox4.isSelected()){
                chosenFeatures.add(new Keywords75(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords752(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords753(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords754(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords755(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords756(extractionManager.getKeyWords()));
            }
            if(checkBox5.isSelected()){
                chosenFeatures.add(new MostCommonKeyword(extractionManager.getKeyWords()));
                chosenFeatures.add(new MostCommonKeyword2(extractionManager.getKeyWords()));
                chosenFeatures.add(new MostCommonKeyword4(extractionManager.getKeyWords()));
                chosenFeatures.add(new MostCommonKeyword5(extractionManager.getKeyWords()));
                chosenFeatures.add(new MostCommonKeyword6(extractionManager.getKeyWords()));
                chosenFeatures.add(new MostCommonKeywords3(extractionManager.getKeyWords()));
            }
            if(checkBox6.isSelected()){
                chosenFeatures.add(new ShortWordCounter(extractionManager.getKeyWords()));
            }
            if(checkBox7.isSelected()){
                chosenFeatures.add(new LongWordCounter());
            }
            if(checkBox8.isSelected()){
                chosenFeatures.add(new NumberOfKeywords1(extractionManager.getKeyWords()));
                chosenFeatures.add(new NumberOfKeywords2(extractionManager.getKeyWords()));
                chosenFeatures.add(new NumberOfKeywords3(extractionManager.getKeyWords()));
                chosenFeatures.add(new NumberOfKeywords4(extractionManager.getKeyWords()));
                chosenFeatures.add(new NumberOfKeywords5(extractionManager.getKeyWords()));
                chosenFeatures.add(new NumberOfKeywords6(extractionManager.getKeyWords()));
            }
        }
        else if(category.equals("SPORT")){
            try {
                extractionManager = new ExtractionManager(percent, new OwnDataSerializator(), category);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            settingFilePath = settingsPath.getText();
            if(settingFilePath.equals(""))
            {
                Collections.shuffle(extractionManager.getLearningData());
                // coldStart = extractionManager.getLearningData();
            }
            else
            {
                manager.readConfigurationFromFile(settingFilePath);
                extractionManager.setLearningData(manager.configuration.getColdStart());
                extractionManager.deleteStopwords(extractionManager.getLearningData());
                DataStemmer stemmer = new DataStemmer();
                stemmer.stemmizeData(extractionManager.getLearningData());
                //coldStart = manager.configuration.getColdStart();
            }
            if(extraction.equals("TF")){
                for(int i = 0; i < listNames.size(); ++i){
                    highestFrequencyWord.ChooseWords(extractionManager, 6, listNames.get(i));
                }
            }
            else if(extraction.equals("TFIDF")){
                for(int i = 0; i < listNames.size(); ++i){
                    extractionManager.createLearningDataWords(listNames.get(i));
                    tfidf.ChooseMainWordsForCountries(extractionManager, listNames.get(i), 6);
                }
            }
            if(checkBox1.isSelected()){
                chosenFeatures.add(new TextLengthCounter());
            }
            if(checkBox2.isSelected()){
                chosenFeatures.add(new UpperCaseWordCounter(extractionManager.getKeyWords()));
            }
            if(checkBox3.isSelected()){
                chosenFeatures.add(new Keywords25(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords252(extractionManager.getKeyWords()));
            }
            if(checkBox4.isSelected()){
                chosenFeatures.add(new Keywords75(extractionManager.getKeyWords()));
                chosenFeatures.add(new Keywords752(extractionManager.getKeyWords()));
            }
            if(checkBox5.isSelected()){
                chosenFeatures.add(new MostCommonKeyword(extractionManager.getKeyWords()));
                chosenFeatures.add(new MostCommonKeyword2(extractionManager.getKeyWords()));
            }
            if(checkBox6.isSelected()){
                chosenFeatures.add(new ShortWordCounter(extractionManager.getKeyWords()));
            }
            if(checkBox7.isSelected()){
                chosenFeatures.add(new LongWordCounter());
            }
            if(checkBox8.isSelected()){
                chosenFeatures.add(new NumberOfKeywords1(extractionManager.getKeyWords()));
                chosenFeatures.add(new NumberOfKeywords2(extractionManager.getKeyWords()));
            }
        }
        IMetric whatMetric = null;
        if(metric.equals("euklidesowa")){
            whatMetric = new EuclideanMetric();
        }
        else if(metric.equals("uliczna")){
            whatMetric = new ManhattanMetric();
        }
        else if(metric.equals("Czebyszewa")){
            whatMetric = new ChebyshevMetric();
        }
        manager.configuration = Configuration.builder().coldStart(extractionManager.getLearningData()).build();
        IClassificator clas = new FeatureClassificator(extractionManager.getLearningData(), chosenFeatures, k, whatMetric);
        calculateSummary(extractionManager, clas);
    }

    private void calculateSummary(ExtractionManager extractionManager, IClassificator clas){
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
        }
        int good = 0;
        int bad = 0;
        for(Map.Entry e : howManyGood.entrySet()){
            good += (int)e.getValue();
        }
        for(Map.Entry e : howManyBad.entrySet()){
            bad += (int)e.getValue();
        }
        trueResultTextField.setText(String.valueOf(bad));
        falseResultTextField.setText(String.valueOf(good));
        if(data){
            try {
                String features = "";
                if(checkBox1.isSelected() && checkBox2.isSelected() && checkBox3.isSelected() && checkBox4.isSelected()
                && checkBox5.isSelected() && checkBox6.isSelected() && checkBox7.isSelected() && checkBox8.isSelected()){
                    features = "all";
                }
                else if(checkBox1.isSelected() && checkBox2.isSelected() && checkBox3.isSelected() && checkBox5.isSelected()){
                    features = "half";
                }
                saveData.save(metric, extraction, percent.toString(), category, k.toString(), features, trueResultTextField, falseResultTextField);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void calcuteMeasure() {
        calcuteButtonMeasure.setLayoutX(170);
        calcuteButtonMeasure.setLayoutY(470);
        calcuteButtonMeasure.setText("Klasyfikuj miarami podobieństwa");
        calcuteButtonMeasure.setOnAction(e -> {
            getParamsMeasure();
        });
        root.getChildren().add(calcuteButtonMeasure);
    }

    private void getParamsMeasure() {
        try{
            category = chooseDataComboBox.getValue().toString();
            n = Integer.parseInt(chooseNTextField.getText());
            k = Integer.parseInt(chooseKTextField.getText());
            percent = Integer.parseInt(choosePercentDataTextField.getText());
            listFeatures.clear();
            if(checkBox11.isSelected()){
                listFeatures.add(11);
            }
            else if(checkBox22.isSelected()){
                listFeatures.add(22);
            }
            if(listFeatures.size() == 0){
                throw new Exception("size equals 0");
            }
        }catch(Exception e){
            return;
        }
        classifyMeasure();
    }

    private void classifyMeasure() {
        ExtractionManager extractionManager = null;
        List<ISimilarityMeasure> chosenMeasures = new ArrayList<>();
        if(category.equals("TOPICS") || category.equals("PLACES")){
            try {
                extractionManager = new ExtractionManager(percent, new XmlSerializator(), category);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else if(category.equals("SPORT")){
            try {
                extractionManager = new ExtractionManager(percent, new OwnDataSerializator(), category);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(checkBox22.isSelected()){
            chosenMeasures.add(new NiewiadomskiNGramMeasure());
        }
        if(checkBox11.isSelected()){
            chosenMeasures.add(new NGramMeasure(n));
        }
        System.out.println(settingFilePath);
        settingFilePath = settingsPath.getText();
        if(settingFilePath.equals(""))
        {
            Collections.shuffle(extractionManager.getLearningData());
            coldStart = extractionManager.getLearningData();
        }
        else
        {
            manager.readConfigurationFromFile(settingFilePath);
            coldStart = manager.configuration.getColdStart();
            extractionManager.deleteStopwords(coldStart);
            DataStemmer stemmer = new DataStemmer();
            stemmer.stemmizeData(coldStart);
        }
        Configuration config = Configuration.builder().coldStart(coldStart).build();
        manager.configuration = config;

        IClassificator clas = new SimilarityClassificator(coldStart, chosenMeasures, k);
        calculateSummary(extractionManager,clas);
    }

    private void result() {
        Font myFont2 = new Font("Serif", 14);
        falseResultLabel.setText("Poprawne zaklasyfikowane: ");
        falseResultLabel.setLayoutX(20);
        falseResultLabel.setLayoutY(500);
        falseResultLabel.setFont(myFont);
        trueResultLabel.setText("Niepoprawnie zaklasyfikowane: ");
        trueResultLabel.setLayoutX(20);
        trueResultLabel.setLayoutY(530);
        trueResultLabel.setFont(myFont);
        falseResultTextField.setDisable(true);
        falseResultTextField.setLayoutX(320);
        falseResultTextField.setLayoutY(500);
        falseResultTextField.setFont(myFont2);
        trueResultTextField.setDisable(true);
        trueResultTextField.setLayoutX(320);
        trueResultTextField.setLayoutY(530);
        trueResultTextField.setFont(myFont2);
        root.getChildren().addAll(trueResultLabel, falseResultLabel, falseResultTextField, trueResultTextField);
    }

    private void chooseFeatures() {
        chooseFeaturesLabel.setText("Cechy do klasyfikacji: ");
        chooseFeaturesLabel.setLayoutX(20);
        chooseFeaturesLabel.setLayoutY(260);
        chooseFeaturesLabel.setFont(myFont);
        checkBox1.setText("(1)");
        checkBox1.setLayoutX(20);
        checkBox1.setLayoutY(290);
        checkBox2.setText("(2)");
        checkBox2.setLayoutX(60);
        checkBox2.setLayoutY(290);
        checkBox3.setText("(3)");
        checkBox3.setLayoutX(100);
        checkBox3.setLayoutY(290);
        checkBox4.setText("(4)");
        checkBox4.setLayoutX(20);
        checkBox4.setLayoutY(320);
        checkBox5.setText("(5)");
        checkBox5.setLayoutX(60);
        checkBox5.setLayoutY(320);
        checkBox6.setText("(6)");
        checkBox6.setLayoutX(100);
        checkBox6.setLayoutY(320);
        checkBox7.setText("(7)");
        checkBox7.setLayoutX(20);
        checkBox7.setLayoutY(350);
        checkBox8.setText("(8)");
        checkBox8.setLayoutX(60);
        checkBox8.setLayoutY(350);
        root.getChildren().addAll(checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, chooseFeaturesLabel);
    }

    private void chooseMeasure() {
        chooseMeasureLabel.setText("Wybierz miarę podobieństwa: ");
        chooseMeasureLabel.setLayoutX(480);
        chooseMeasureLabel.setLayoutY(80);
        chooseMeasureLabel.setFont(myFont);
        checkBox11.setText("(N-gramy)");
        checkBox11.setLayoutX(480);
        checkBox11.setLayoutY(110);
        checkBox22.setText("(N-gramy Niewiadomskiego)");
        checkBox22.setLayoutX(580);
        checkBox22.setLayoutY(110);
        root.getChildren().addAll(checkBox11, checkBox22, chooseMeasureLabel);

    }

    private void choosePercentData() {
        choosePercentDataLabel.setText("Podaj procent danych uczących: ");
        choosePercentDataLabel.setLayoutX(20);
        choosePercentDataLabel.setLayoutY(380);
        choosePercentDataLabel.setFont(myFont);
        choosePercentDataTextField.setLayoutY(410);
        choosePercentDataTextField.setLayoutX(20);
        root.getChildren().addAll(choosePercentDataTextField, choosePercentDataLabel);
    }

    private void chooseK() {
        chooseKLabel.setText("Podaj wartość k: ");
        chooseKLabel.setLayoutX(20);
        chooseKLabel.setLayoutY(200);
        chooseKLabel.setFont(myFont);
        chooseKTextField.setLayoutY(230);
        chooseKTextField.setLayoutX(20);
        root.getChildren().addAll(chooseKTextField, chooseKLabel);
    }

    private void chooseN() {
        chooseNLabel.setText("Podaj wartość n: ");
        chooseNLabel.setLayoutX(480);
        chooseNLabel.setLayoutY(140);
        chooseNLabel.setFont(myFont);
        chooseNTextField.setLayoutY(170);
        chooseNTextField.setLayoutX(480);
        root.getChildren().addAll(chooseNTextField, chooseNLabel);
    }

    private void chooseMetric() {
        chooseMetricLabel.setText("Wybierz metrykę: ");
        chooseMetricLabel.setLayoutX(20);
        chooseMetricLabel.setLayoutY(140);
        chooseMetricLabel.setFont(myFont);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "euklidesowa",
                        "Czebyszewa",
                        "uliczna"
                );
        chooseMEtricComboBox = new ComboBox(options);
        chooseMEtricComboBox.setLayoutY(170);
        chooseMEtricComboBox.setLayoutX(20);
        root.getChildren().addAll(chooseMEtricComboBox, chooseMetricLabel);
    }

    private void chooseExtraction() {
        chooseExtractionLabel.setText("Wybierz rodzaj ekstrakcji: ");
        chooseExtractionLabel.setLayoutX(20);
        chooseExtractionLabel.setLayoutY(80);
        chooseExtractionLabel.setFont(myFont);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "TF",
                        "TFIDF"
                );
        chooseExtractionComboBox = new ComboBox(options);
        chooseExtractionComboBox.setLayoutY(110);
        chooseExtractionComboBox.setLayoutX(20);
        root.getChildren().addAll(chooseExtractionComboBox, chooseExtractionLabel);
    }


    private void chooseData() {
        chooseDataLabel.setText("Wybierz kategorie danych: ");
        chooseDataLabel.setLayoutX(20);
        chooseDataLabel.setLayoutY(20);
        chooseDataLabel.setFont(myFont);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "PLACES",
                        "TOPICS",
                        "SPORT"
                );
        chooseDataComboBox = new ComboBox(options);
        chooseDataComboBox.setLayoutY(50);
        chooseDataComboBox.setLayoutX(20);
        root.getChildren().addAll(chooseDataComboBox, chooseDataLabel);
    }
}
