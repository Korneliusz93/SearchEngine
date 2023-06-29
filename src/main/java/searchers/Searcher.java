package searchers;

import java.io.IOException;
import java.nio.file.Paths;

import analyzers.CustomAnalyzer;
import constants.LuceneConstants;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {

    IndexSearcher indexSearcher;
    IndexReader reader;
    QueryParser queryParser;
    Query query;

    public Searcher(String indexDirectoryPath)
            throws IOException {
        Directory indexDirectory =
                FSDirectory.open(Paths.get(indexDirectoryPath));
        reader = DirectoryReader.open(indexDirectory);

        indexSearcher = new IndexSearcher(reader);
        queryParser = new QueryParser(
                LuceneConstants.CONTENTS,
                new CustomAnalyzer());
    }

    public TopDocs search( String searchQuery)
            throws IOException, ParseException {
        query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
    }

    public Document getDocument(ScoreDoc scoreDoc)
            throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

}
