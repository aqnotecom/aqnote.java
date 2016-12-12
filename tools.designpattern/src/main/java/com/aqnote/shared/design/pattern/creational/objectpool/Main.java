/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.design.pattern.creational.objectpool;

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
