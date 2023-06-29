import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class LuceneAnalyzersIntegrationTest {

    Path file1 = Path.of("/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/dataDir/A Case of Identity.rtf");
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

    /* Predefined stop words list

            "a", "all", "am", "an", "and", "any", "are", "as", "at", "be", "but", "by",
            "can", "could", "did", "do", "does", "etc", "for", "from", "goes", "got", "had", "has",
            "have", "he", "her", "him", "his", "how", "if", "in", "is", "it", "let", "me", "more",
            "much", "must", "my", "nor", "not", "now", "of", "off", "on", "or", "our", "own", "see", "set",
            "shall", "she", "should", "so", "some", "than", "that", "the", "them", "then", "there",
            "these", "this", "those", "though", "to", "too", "us", "was", "way", "we", "what", "when",
            "where", "which", "who", "why", "will", "would", "yes", "yet", "you"

     */

    List <String> stopWordsList =
            Stream.of("a", "all", "am", "an", "and", "any", "are", "as", "at", "be", "but", "by",
                    "can", "could", "did", "do", "does", "etc", "for", "from", "goes", "got", "had", "has",
                    "have", "he", "her", "him", "his", "how", "if", "in", "is", "it", "let", "me", "more",
                    "much", "must", "my", "nor", "not", "now", "of", "off", "on", "or", "our", "own", "see", "set",
                    "shall", "she", "should", "so", "some", "than", "that", "the", "them", "then", "there",
                    "these", "this", "those", "though", "to", "too", "us", "was", "way", "we", "what", "when",
                    "where", "which", "who", "why", "will", "would", "yes", "yet", "you")
                    .collect(Collectors.toList());


    {
        try {
            SAMPLE_TEXT = Files.readString(file1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void StandardAnalyzerTest() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new StandardAnalyzer());
        List<String> stopWordsNotDetected =
                result.stream()
                        .filter(element -> stopWordsList.contains(element))
                        .collect(Collectors.toList());
        assertEquals(0,stopWordsNotDetected.size());
    }

    /*@Test
    void StopAnalyzerTest() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new StopAnalyzer());
        List<String> stopWordsNotDetected =
                result.stream()
                        .filter(element -> stopWordsList.contains(element))
                        .collect(Collectors.toList());
        assertEquals(0,stopWordsNotDetected.size());
    }*/

    @Test
    void SimpleAnalyzerTest() throws IOException{
        List<String> result = analyze(SAMPLE_TEXT, new SimpleAnalyzer());
        List<String> stopWordsNotDetected =
                result.stream()
                        .filter(element -> stopWordsList.contains(element))
                        .collect(Collectors.toList());
        assertEquals(0,stopWordsNotDetected.size());
    }

    @Test
    void WhitespaceAnalyzerTest() throws IOException{
        List<String> result = analyze(SAMPLE_TEXT, new WhitespaceAnalyzer());
        List<String> stopWordsNotDetected =
                result.stream()
                        .filter(element -> stopWordsList.contains(element))
                        .collect(Collectors.toList());
        assertEquals(0,stopWordsNotDetected.size());
    }

    @Test
    void KeywordAnalyzerTest() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new KeywordAnalyzer());
        List<String> stopWordsNotDetected =
                result.stream()
                        .filter(element -> stopWordsList.contains(element))
                        .collect(Collectors.toList());
        System.out.println(result);
        assertEquals(0,stopWordsNotDetected.size());
    }

    @Test
    void EnglishAnalyzerTest() throws IOException {
        List<String> result = analyze(SAMPLE_TEXT, new EnglishAnalyzer());
        List<String> stopWordsNotDetected =
                result.stream()
                        .filter(element -> stopWordsList.contains(element))
                        .collect(Collectors.toList());
        assertEquals(0,stopWordsNotDetected.size());
    }

    public List<String> analyze(String text, Analyzer analyzer) throws IOException{
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
