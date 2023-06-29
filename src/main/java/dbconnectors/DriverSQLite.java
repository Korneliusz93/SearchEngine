package dbconnectors;

import java.sql.*;

public class DriverSQLite {

    public static Connection connection;
    //public static String resources_url = "jdbc:sqlite:/Users/admin/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/SearchEngine/src/main/webapp/";
    public static String resources_url = "jdbc:sqlite:/Users/kornelrzeczycki/Documents/Studia mgr/Zwinne Metodyki wytwarzania oprogramowania/SearchEngine/src/main/webapp/";

    public void createDataBase(String fileName) throws SQLException {
        String url = resources_url + fileName;
        try (Connection connect = DriverManager.getConnection(url)) {
            if (connect!=null){
                DatabaseMetaData meta = connect.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTable(String fileName) throws SQLException{
        String url = resources_url + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS hits (\n"
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                +"fileName TEXT,\n"
                +"lineNumber INTEGER,\n"
                +"words TEXT\n"
                +");";

        try (Connection connect = DriverManager.getConnection(url);
            Statement statement = connect.createStatement()){
                statement.execute(sql);
            } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void connect() throws SQLException{
        try{
            String url = resources_url + "search_hits.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection connect(String fileName) throws SQLException{
        try{
            String url = resources_url + fileName;
            connection = DriverManager.getConnection(url);
            //System.out.println("Connection to SQLite "+ fileName +" has been established.");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void disconnect(){
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}
