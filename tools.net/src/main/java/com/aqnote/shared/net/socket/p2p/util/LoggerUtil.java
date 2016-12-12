/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p.util;

import java.util.logging.*;
/**
 * Logging Util
 * @author axvelazq
 *
 */
public class LoggerUtil {
	private static final String LOGGER = "p2p.logging";
	static{
		Logger.getLogger(LOGGER).setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		Logger.getLogger(LOGGER).addHandler(handler);
	}
	
	public static void setHandlerLevel(Level level){
		Handler[] handlers = Logger.getLogger(LOGGER).getHandlers();
		for(Handler h: handlers){
			h.setLevel(level);
		}
		Logger.getLogger(LOGGER).setLevel(level);
	}
	
	public static Logger getLogger(){
		return Logger.getLogger(LOGGER);
	}
	
}
