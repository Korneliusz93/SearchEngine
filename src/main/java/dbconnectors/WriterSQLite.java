package dbconnectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WriterSQLite {
    public static DriverSQLite driverSQLite;

    /**
     * Insert a new row into the db_name table
     *
     * @param db_name
     * @param lineNumber
     * @param words
     */
    public static void insert(String db_name, String fileName, Long lineNumber, String words) throws SQLException {
        String sql = "INSERT INTO hits(fileName,lineNumber,words) VALUES(?,?,?)";

        try (Connection connect = driverSQLite.connect(db_name);
            PreparedStatement preparedStatement = connect.prepareStatement(sql)){
                preparedStatement.setString(1, fileName);
                preparedStatement.setLong(2, lineNumber);
                preparedStatement.setString(3, words);
                preparedStatement.executeUpdate();
        } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
}
