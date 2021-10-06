package nodes;


import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;

public class FileInputButton extends Button {
    private final FileChooser fileChooser = new FileChooser();

    public FileInputButton(String text, TextArea textArea) {
        this.setText(text);
        this.setId("dark-blue");
        this.setOnMouseClicked((event)->{
            textArea.clear();
            File file = fileChooser.showOpenDialog(textArea.getScene().getWindow());
            if(file!=null){
                try {
                    String line;
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    while((line =br.readLine())!=null){
                        textArea.appendText(line+"\r\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }




}
