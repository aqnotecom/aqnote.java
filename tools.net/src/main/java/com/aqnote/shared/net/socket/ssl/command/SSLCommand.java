/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.ssl.command;

import java.util.HashSet;
import java.util.Set;

/**
 * SSLCommand.java desc：交互命令
 * 
 * @author madding.lip Sep 10, 2014 11:23:39 AM
 */
public class SSLCommand {

    public static final Set<String> command = new HashSet<String>();

    public static final String      HELLO   = "hello";
    public static final String      BYE     = "bye";
    public static final String      DATE    = "date";
    public static final String      HELP    = "help";
    public static final String      UNKNOWN = "unknown";

    static {
        command.add(HELLO);
        command.add(BYE);
        command.add(DATE);
        command.add(HELP);
    }

}
