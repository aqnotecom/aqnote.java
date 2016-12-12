/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import com.aqnote.shared.net.socket.p2p.Connection;
import com.aqnote.shared.net.socket.p2p.Handler;
import com.aqnote.shared.net.socket.p2p.Message;
import com.aqnote.shared.net.socket.p2p.Node;
import com.aqnote.shared.net.socket.p2p.sharer.SharerMessage;
import com.aqnote.shared.net.socket.p2p.sharer.SharerNode;
import com.aqnote.shared.net.socket.p2p.util.LoggerUtil;

/**
 * Handler for the FGET Messages FGET file-name: Request a peer to reply with the contents of the specified file.
 * 
 * @author axvelazq
 */
public class FileGetHandler extends Handler {

    public FileGetHandler(Node node){
        super(node);
    }

    @Override
    public void handleMessage(Connection conn, Message message) {
        String filename = message.getMsgData();
        SharerNode peer = (SharerNode) this.getNode();
        Hashtable<String, String> files = peer.getTableFiles();
        if (!files.containsKey(filename)) {
            conn.sendData(new Message(SharerMessage.ERROR, "FGET: FILE NOT FOUND" + filename));
        }

        byte[] filedata = null;
        try {
            FileInputStream inFile = new FileInputStream(filename);
            int length = inFile.available();
            filedata = new byte[length];
            inFile.read(filedata);
            inFile.close();
        } catch (IOException ex) {
            LoggerUtil.getLogger().severe("FGET: ERROR READING FILE: " + ex);
            conn.sendData(new Message(SharerMessage.ERROR, "FGET: ERROR READING FILE " + filename));
            return;
        }
        conn.sendData(new Message(SharerMessage.REPLY, filedata));

    }

}
