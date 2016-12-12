/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.irecom;

import java.util.HashMap;
import java.util.Map;

import com.aqnote.shared.net.httpclient.HttpClientUtil;

public class IrecomUtil {

    private static final String PARAM_NAME_MEMBERID = "memberids";
    private static final String PARAM_NAME_OFFERID  = "offerids";

    private static final String PARAM_NAME_UID      = "uid";
    private static final String PARAM_NAME_PAGEID   = "pageid";
    private static final String PARAM_NAME_RECID    = "recid";
    private static final String PARAM_VALUE_RECID   = "1179";
    private static final String PARAM_VALUE_PAGEID  = "not-from-browser";

    public static String loadIrecomInfo(String currentLoginId, String memberId, long offerId) {

        String url = "http://res.china.alibaba.com/fly/irecom.do";
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_NAME_MEMBERID, memberId);
        params.put(PARAM_NAME_OFFERID, String.valueOf(offerId));
        params.put(PARAM_NAME_PAGEID, PARAM_VALUE_PAGEID);
        params.put(PARAM_NAME_RECID, PARAM_VALUE_RECID);
        params.put(PARAM_NAME_UID, currentLoginId);
        return HttpClientUtil.postAsUTF8(url, params);

    }

}
