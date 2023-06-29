package apiclients;

import com.google.gson.internal.LinkedTreeMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ApiClient {

    private static String ApiKey = "/Users/kornelrzeczycki/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/SearchEngine/src/main/resources/LinguaRobotKey.txt";

    private OkHttpClient client;

    public ApiClient() {
        client = new OkHttpClient();
    }

    private String getKey() throws IOException {
        Path filepath = Path.of(ApiKey);
        return Files.readString(filepath);
    }

    public List<String> getResponse(String q) throws Exception {
        List<String> definitions = new ArrayList<>();
        Request request = new Request.Builder()
                .url("https://lingua-robot.p.rapidapi.com/language/v1/entries/en/"+q)
                .get()
                .addHeader("X-RapidAPI-Key", getKey())
                .addHeader("X-RapidAPI-Host", "lingua-robot.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();


        if (response.isSuccessful()) {
            Gson gson = new Gson();
            Map<String, Object> json  = gson.fromJson(response.body().string(), Map.class);

            ArrayList<LinkedTreeMap<String,Object>> outerList = (ArrayList<LinkedTreeMap<String, Object>>) json.get("entries");

            for ( Map<String,Object> outerMap: outerList) {
                ArrayList<LinkedTreeMap<String,Object>> innerList = (ArrayList<LinkedTreeMap<String, Object>>) outerMap.get("lexemes");
                for (LinkedTreeMap<String, Object> map: innerList) {
                    ArrayList<LinkedTreeMap<String,String>> nestedList = (ArrayList<LinkedTreeMap<String,String>>) map.get("senses");
                    for(LinkedTreeMap<String,String> treeMap: nestedList){
                            String definition = treeMap.get("definition");
                            if(treeMap.containsKey("context")){
                                treeMap.keySet()
                                        .stream()
                                        .forEach(
                                                x -> {
                                                        treeMap.entrySet().stream()
                                                                .forEach(
                                                                        y -> {
                                                                            if(y.toString().startsWith("context=")){
                                                                                String ltm = y.toString().replace("context=","");
                                                                                if (!definitions.contains(definition+ " " + ltm))
                                                                                    definitions.add(definition+ " " + ltm);
                                                                            }
                                                                        }
                                                                );
                                                    }
                                        );
                            } else {
                                if (!definitions.contains(definition))
                                    definitions.add(definition);
                            }
                    }
                }
                continue;
            }
            return definitions;

        } else {
            throw new Exception("Error getting result");
        }
    }

}
