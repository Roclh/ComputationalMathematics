package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class History {
    String filePath = System.getenv("APPDATA") + "/ComputationalMathematics/history";
    String fileName;
    int iterator = 0;
    int maxSize;
    String[] functions;

    private void init(){
        File dir = new File(System.getenv("APPDATA") + "/ComputationalMathematics");
        if(!dir.exists()) {
            if (dir.mkdir()) System.out.println("Folder has been created");
        }
        dir = new File(System.getenv("APPDATA") + "/ComputationalMathematics/history");
        if(!dir.exists()){
            if(dir.mkdir()) System.out.println("Folder has been created");
        }
    }

    public History(String fileName, int maxSize){
        init();
        this.maxSize = maxSize;
        this.functions = new String[maxSize];
        try{
            File file = new File(filePath, fileName);
            if(file.exists()){
                try{
                    Scanner scanner = new Scanner(file);
                    while(scanner.hasNextLine()&& iterator<maxSize){
                        functions[iterator] = scanner.nextLine();
                        iterator++;
                    }
                    scanner.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            this.fileName = fileName;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public History(String fileName){
        init();
        this.maxSize = 6;
        this.functions = new String[maxSize];
        try{
            File file = new File(filePath, fileName);
            if(file.exists()){
                try{
                    Scanner scanner = new Scanner(file);
                    while(scanner.hasNextLine() && iterator<maxSize){
                        functions[iterator] = scanner.nextLine().trim().replaceAll("\r\n","");
                        iterator++;
                    }
                    scanner.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            this.fileName = fileName;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public boolean save(){
        File file = new File(filePath, fileName);

        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for(int i =0; i<iterator; i++){
                fileWriter.write(functions[i]+"\r\n");
            }
            fileWriter.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> getFunctions(){
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i =0; i<this.iterator; i++){
            arrayList.add(this.functions[i]);
        }
        return arrayList;
    }

    public boolean addToHistory(String function){
        if(function.isEmpty()||checkExistence(function)){
            return false;
        }
        if(iterator >= maxSize-1){
            reduceHistory();
            functions[iterator] = function.trim();
        }else{
            functions[iterator] = function.trim();
            iterator++;
        }
        return true;
    }


    /**
     * Function  check current array of functions in history if the function already exists
     * @param function The string, containing new function
     * @return true if already exists, false if not
     */
    private boolean checkExistence(String function){
        for(int i =0; i<iterator;i++){
            if(functions[i].equals(function)) return true;
        }
        return false;
    }

    private void reduceHistory(){
        for(int i =1; i<maxSize; i++){
            functions[i-1]= functions[i];
        }
    }

}
