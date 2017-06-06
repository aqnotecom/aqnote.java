/*
 * Copyright 2013-2023 Peng Li <aqnote@qq.com> Licensed under the AQNote License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.aqnote.com/licenses/LICENSE-1.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.aqnote.shared.test.script;

import java.util.HashMap;
import java.util.Map;

import com.aqnote.shared.lang.StringUtil;

/**
 * ThreadLocalMap.java desc：TODO
 * 
 * @author madding Jan 9, 2017 5:17:04 PM
 */
public class QueryData {

    private Map<String, String>                 params      = new HashMap<String, String>();
    private static final ThreadLocal<QueryData> threadlocal = new ThreadLocal<QueryData>();

    public static QueryData get() {
        QueryData map = threadlocal.get();
        if (map == null) {
            map = new QueryData();
            threadlocal.set(map);
        }
        return map;
    }

    public static QueryData set(QueryData map) {
        QueryData old = get();
        threadlocal.set(map);
        return old;
    }

    public static void reset() {
        threadlocal.set(null);
    }

    public static Device getDevice() {
        String deviceid = get().params.get("deviceid");
        if (deviceid == null) {
            deviceid = "111111111";
        }
        return Query.instance().findDevice(deviceid);
    }

    public static User getUser() {
        String userid = get().params.get("userid");
        if (StringUtil.isEmpty(userid)) {
            userid = "222222222";
        }
        return Query.instance().findUser(userid);
    }

    public static Session getSession() {
        String sid = get().params.get("sid");
        if (sid == null) {
            sid = "333333333";
        }
        return Query.instance().findSession(sid);
    }
}
