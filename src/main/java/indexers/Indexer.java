package indexers;

import java.io.*;

import analyzers.CustomAnalyzer;
import constants.LuceneConstants;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

    private IndexWriter writer;
    private IndexWriterConfig config;

    public Indexer(String indexDirectoryPath) throws IOException {
        //this directory will contain the indexes
        Directory indexDirectory =
                FSDirectory.open(new File(indexDirectoryPath).toPath());

        //create the indexer config
        config = new IndexWriterConfig(new CustomAnalyzer());
        //create the indexer
        writer = new IndexWriter(indexDirectory ,config);
    }

    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();

        Reader fileReader = new FileReader(file);
        //index file contents
        TextField contentField = new TextField(LuceneConstants.CONTENTS, fileReader);
        //index file name
        TextField fileNameField = new TextField(LuceneConstants.FILE_NAME,
                file.getName(),Field.Store.YES);
        //index file path
        TextField filePathField = new TextField(LuceneConstants.FILE_PATH,
                file.getCanonicalPath(), Field.Store.YES);
        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        return document;
    }

    private void indexFile(File file) throws IOException {
        System.out.println("Indexing "+file.getCanonicalPath());
        Document document = getDocument(file);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter)
            throws IOException {
        //get all files in the data directory
        File[] files = new File(dataDirPath).listFiles();

        for (File file : files) {
            if(!file.isDirectory()
                    && !file.isHidden()
                    && file.exists()
                    && file.canRead()
                    && filter.accept(file)
            ){
                indexFile(file);
            }
        }
        return writer.getDocStats().numDocs;
    }
}
