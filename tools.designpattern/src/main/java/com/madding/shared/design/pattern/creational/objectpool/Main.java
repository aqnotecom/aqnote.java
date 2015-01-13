package com.madding.shared.design.pattern.creational.objectpool;

import java.sql.Connection;

public class Main {

    public static void main(String args[]) {
        // Do something...

        // Create the ConnectionPool:
        JDBCConnectionPool pool = new JDBCConnectionPool("org.hsqldb.jdbcDriver", "jdbc:hsqldb://localhost/mydb", "sa",
                                                         "secret");

        // Get a connection:
        Connection con = pool.getObject();

        // Use the connection

        // Return the connection:
        pool.release(con);

    }
}
