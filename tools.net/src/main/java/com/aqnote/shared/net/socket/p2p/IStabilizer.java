/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

/**
 * Interface for objects that may be used to stabilize th esta of a P2P Network
 * 
 * @author axvelazq
 */
public interface IStabilizer {

    public void stabilize();
}
