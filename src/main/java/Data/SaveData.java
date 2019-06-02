package Data;

import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SaveData {

    private String fileName;
    public SaveData(String fileName){
        this.fileName = fileName;
    }

    public void save(String metric, String extraction, String percent, String category, String k, String features, TextField trueResultTextField, TextField falseResultTextField) throws FileNotFoundException {
        File file = new File("src/main/java/Data/" + fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            Scanner scanner2 = new Scanner(scanner.nextLine());
            int cnt = 1;
            while(scanner2.hasNext()){
                String s = scanner2.next();
                if(cnt == 1){
                    if(s.equals(metric)){
                        ++cnt;
                    }
                    else{
                        break;
                    }
                }
                else if(cnt == 2){
                    if(s.equals(extraction)){
                        ++cnt;
                    }
                    else{
                        break;
                    }
                }
                else if(cnt == 3){
                    if(s.equals(percent)){
                        ++cnt;
                    }
                    else{
                        break;
                    }
                }
                else if(cnt == 4){
                    if(s.equals(category)){
                        ++cnt;
                    }
                    else{
                        break;
                    }
                }
                else if(cnt == 5){
                    if(s.equals(k)){
                        ++cnt;
                    }
                    else{
                        break;
                    }
                }
                else if(cnt == 6){
                    if(s.equals(features)){
                        ++cnt;
                    }
                    else{
                        break;
                    }
                }
                else if(cnt ==7){
                    falseResultTextField.setText(s);
                    trueResultTextField.setText(scanner2.next());
                }
                System.out.println(s);
            }
        }
    }
}
