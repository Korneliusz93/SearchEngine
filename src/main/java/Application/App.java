package Application;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiclients.ApiClient;
import constants.LuceneConstants;
import dbconnectors.DriverSQLite;
import dbconnectors.WriterSQLite;
import filters.TextFileFilter;
import indexers.Indexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import scanners.Location;
import scanners.Scanner;
import searchers.Searcher;

public class App {

    //public static String indexDir = "/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/IndexDir/";
    public static String indexDir = "/Users/kornelrzeczycki/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/IndexDir/";
    //static String dataDir = "/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/";
    static String dataDir = "/Users/kornelrzeczycki/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/";
    ApiClient apiClient = new ApiClient();
    Indexer indexer;
    Searcher searcher;
    private static App instance; // Singleton instance

    /*public static void main(String[] args) throws SQLException {
        Tester tester;
        DriverSQLite.createTable("search_hits.db");
        DriverSQLite.createTable("search_hits.db");
        try {
            tester = new Tester();
            if (new File(indexDir).list().length == 1)
                tester.createIndex();
            tester.search("stone");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/

    // Method to get the singleton instance
    public static App getInstance() {
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null) {
                    instance = new App();
                }
            }
        }
        return instance;
    }
    public void createIndex() throws IOException {
        indexer = new Indexer(indexDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
        long endTime = System.currentTimeMillis();
        indexer.close();
        System.out.println(numIndexed+" File indexed, time taken: "
                +(endTime-startTime)+" ms");
    }

    public List<String> search(String searchQuery) throws IOException, ParseException, SQLException {
        searcher = new Searcher(indexDir);
        Scanner scanner = new Scanner();
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        Map<String,List<Location>> searchHits = new HashMap<>();

        long endTime = System.currentTimeMillis();

        if (hits.totalHits.value>0){
            System.out.println("Searched term \'" + searchQuery + "\' was found.");
        } else {
            System.out.println("Searched term \'" + searchQuery + "\' was not found.");
        }

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime));
        for(ScoreDoc scoreDoc : hits.scoreDocs) {

            Document doc = searcher.getDocument(scoreDoc);

            scanner.readFile(doc.get(LuceneConstants.FILE_PATH),searchQuery);
            System.out.println("\n------------------------------------------------------------");
            System.out.println("File: "
                    + doc.get(LuceneConstants.FILE_PATH));
            System.out.println("File name: "
                    + doc.get(LuceneConstants.FILE_NAME));
            System.out.println("------------------------------------------------------------");
            List <Location> locations = scanner.produceReport();

            String file_name = doc.get(LuceneConstants.FILE_NAME);
            SaveSearchLog(file_name,locations);

            searchHits.put(file_name,locations);

            scanner.CleanWordList();
            scanner.CleanWordLocationMap();

        }
        return SearchResults(searchHits);
    }

    public List<String> getDefinitions(String searchQuery){
        List<String> apiResponse = new ArrayList<>();
        try {
            apiResponse = apiClient.getResponse(searchQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return apiResponse;
    }

    public static void SaveSearchLog(String fileName, List<Location> list) throws SQLException {
        for (Location loc: list) {
            WriterSQLite.insert("search_hits.db",fileName,loc.getLineNum(), loc.getLog());
        }
    }

    public static List<String> SearchResults(Map<String,List<Location>> searchHits){
        String searchHit;
        List<String> searchResults = new ArrayList<>();
        HashMap<String, List<Long>> innerMap = new HashMap<>();
        Map<String, HashMap<String, List<Long>>> map = new HashMap<>();

        searchHits.keySet().forEach(
                x->{
                    searchHits.get(x).forEach(
                            y->{
                                List<Long> list = new ArrayList<Long>();
                                innerMap.putIfAbsent(y.getLog(),list);
                                innerMap.get(y.getLog()).add(y.getLineNum());
                            }
                    );
                    map.put(x, new HashMap<>(innerMap));
                    innerMap.clear();
                }
        );

        StringBuilder sB = new StringBuilder();
        map.keySet().forEach(
                x->{
                    sB.append(x+"\n");
                    map.get(x).keySet().forEach(
                            y -> {
                                sB.append(y+"\t");
                                map.get(x).get(y).forEach(
                                        z -> sB.append(String.valueOf(z) + ", ")
                                );
                                sB.append("\n");
                            }
                    );
                    searchResults.add(sB.toString());
                    sB.setLength(0);
                }
        );

        // Access the elements
        //List<Long> retrievedList = map.get("outerKey").get("innerKey");

        return searchResults;
    }
}
