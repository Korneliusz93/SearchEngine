package analyzers;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.CapitalizationFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.PagedBytes;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomAnalyzer extends Analyzer {

    public static String stopWordsDir = "/Users/kornelrzeczycki/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/StopWords/";
    //public static String stopWordsDir = "/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/StopWords/";

    static List<String> stopWordsList =
            Stream.of("a", "all", "am", "an", "and", "any", "are", "as", "at", "be", "but", "by",
                            "can", "could", "did", "do", "does", "etc", "for", "from", "goes", "got", "had", "has",
                            "have", "he", "her","here", "him", "his", "how", "if", "in", "is", "it", "let", "me", "more",
                            "much", "must", "my", "nor", "not", "now", "of", "off", "on", "or","other", "our", "own", "see", "set",
                            "shall", "she", "should", "so", "some", "than", "that", "the", "them", "then", "there",
                            "these", "this", "those", "though", "to", "too", "us", "up", "was", "way", "we", "what", "when",
                            "where", "which", "who", "why", "will", "would", "yes", "yet", "you","your","out", "over", "upon", "with", "no", "yes")
                    .collect(Collectors.toList());
    //CharArraySet stopWords = new CharArraySet(stopWordsList.size(),true);
    CharArraySet stopWords;
    Reader reader;

    {
        try {
            reader = new FileReader("/Users/kornelrzeczycki/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/StopWords/StopWordsList.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {

        final StandardTokenizer src = new StandardTokenizer();
        TokenStream result = new ClassicFilter(src);
        result = new LowerCaseFilter(result);
        try {
            result = new StopFilter(result, WordlistLoader.getWordSet(reader));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result = new PorterStemFilter(result);
        result = new CapitalizationFilter(result);
        return new TokenStreamComponents(src, result);
    }
}
