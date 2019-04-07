import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ExtractionManager extractionManager = new ExtractionManager(60);
        System.out.println(extractionManager.getLearningData().size());
        System.out.println(extractionManager.getTestingData().size());

//        CountOwnNames ownNames = new CountOwnNames();
//        ownNames.Count(learningMap);
    }
}
