package scanners;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {

    private String word;
    private List<String> wordList = new ArrayList<>();
    private Map<Long, List<String>> wordLocationMap = new HashMap<>();
    FileReader fileReader;

    public void readFile(String path, String query) throws FileNotFoundException {
        this.word = query;
        FileReader fileReader = new FileReader(path);
        this.fileReader = fileReader;
        try {
            storeLines(query);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CleanWordList(){
        wordList.clear();
    }

    public void CleanWordLocationMap(){
        wordLocationMap.clear();
    }

    private void storeLines(String query) throws IOException {
        try (BufferedReader br = new BufferedReader(this.fileReader)) {
            String line;
            long lineIndex = 1;

            Pattern pattern = Pattern.compile("(\\b\\S+"+query+"(?<="+query+")\\S+?\\b)|"
                                                        +"("+query+"(?<="+query+")\\S+?\\b)|(\\b\\S"+query+")"
                                                        +"|(\\b"+query+"\\b)", Pattern.CASE_INSENSITIVE);
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()){
                    this.wordList.add(matcher.group());
                }
                if (this.wordList.size()>0){
                    List<String> tempList = new ArrayList<>(wordList);
                    this.wordLocationMap.put(lineIndex,tempList);
                    CleanWordList();
                }
                lineIndex++;
            }
        }
    }

    public List<Location> produceReport(){

        List<Location> locationList = new ArrayList<Location>();
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println("|Line number:\t\t|\t\tWord:");
        for( Map.Entry<Long, List<String>> set :
                this.wordLocationMap.entrySet() ){
            System.out.printf(String.valueOf("|\t\t\t"+set.getKey())+"\t\t|\t\t\t");
            for (String word: set.getValue()){
                System.out.printf(word + ", ");
                stringBuilder.append(word + ", ");
            }
            System.out.println("");
            locationList.add(new Location(set.getKey(), stringBuilder.toString()));
            stringBuilder.setLength(0);
        }
        return locationList;
    }

    public void produceReport(String query, String paragraph) throws IOException {
        try (BufferedReader br = new BufferedReader(this.fileReader)) {
            String line;
            long lineIndex;
            while ((line = br.readLine()) != null) {
                // process the line.
            }
        }
    }

}
