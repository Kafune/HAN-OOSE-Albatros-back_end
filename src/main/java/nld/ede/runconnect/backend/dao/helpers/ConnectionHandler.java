package nld.ede.runconnect.backend.dao.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionHandler {
    public static void close(PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                throw exception;
            }
        }

        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException exception) {
                throw exception;
            }
        }
    }
}
