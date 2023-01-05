package vn.com.core.common.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    public static void closeObject(Statement obj) {
        try {
            obj.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeObject(ResultSet obj) {
        try {
            obj.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeObject(Connection obj) {
        try {
            obj.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
