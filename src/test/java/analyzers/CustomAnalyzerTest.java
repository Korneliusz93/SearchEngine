package analyzers;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CustomAnalyzerTest {

    //Path file1 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/A Case of Identity.rtf");
    Path file1 = Path.of("/Users/kornelrzeczycki/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/A Case of Identity.rtf");
    Path file2 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/The Five Orange Pips.rtf");
    Path file3 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/The Adventure of the Blue Carbuncle.rtf");
    Path file4 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/THE ADVENTURE OF THE ENGINEER’S THUMB.rtf");
    Path file5 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/A Scandal in Bohemia.rtf");
    Path file6 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/The Red-Headed League.rtf");
    Path file7 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/The Boscombe Valley Mystery.rtf");
    Path file8 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/THE ADVENTURE OF THE SPECKLED BAND.rtf");
    Path file9 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/THE ADVENTURE OF THE COPPER BEECHES.rtf");
    Path file10 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/THE ADVENTURE OF THE BERYL CORONET.rtf");
    Path file11 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/THE ADVENTURE OF THE NOBLE BACHELOR.rtf");
    Path file12 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/The Man with the Twisted Lip.rtf");
    public String SAMPLE_TEXT;
    public String FIELD_NAME = "sampleField";

    {
        try {
            SAMPLE_TEXT = Files.readString(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void CustomAnalyzerTest_StopWordsEliminated() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new CustomAnalyzer());
        List<String> stopWordsNotDetected =
                result.stream()
                        .filter(element -> CustomAnalyzer.stopWordsList.contains(element))
                        .collect(Collectors.toList());
        for(String word: result){
            System.out.println(word);
        }
        assertEquals(0,stopWordsNotDetected.size());
    }

    @Test
    void CustomAnalyzerTest_WordFound_Roundtrip1() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new CustomAnalyzer());
        for(String word: result){
            System.out.println(word);
        }
        assertTrue(result.contains("Wine"));
    }

    public List<String> analyze(String text, Analyzer analyzer) throws IOException {
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream(FIELD_NAME, text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while(tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        return result;
    }
}