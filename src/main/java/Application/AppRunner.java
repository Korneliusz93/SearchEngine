package Application;

import dbconnectors.DriverSQLite;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AppRunner {
    public static void main(String[] args) throws SQLException {

        DriverSQLite.createTable("search_hits.db");

    }
}
