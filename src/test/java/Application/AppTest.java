package Application;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testSearch() throws SQLException, IOException, ParseException {
        String searchedText = "Baker";
        App app = new App();
        app.search(searchedText);
    }

}