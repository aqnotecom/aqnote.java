/*
 * Copyright 2013-2023 Peng Li <aqnote@qq.com> Licensed under the AQNote License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.aqnote.com/licenses/LICENSE-1.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.aqnote.shared.test.script;

import java.util.Date;

/**
 * Query.java desc：TODO
 * 
 * @author madding Jan 9, 2017 5:05:46 PM
 */
public class Query {

    public static Query instance = null;

    public static Query instance() {
        if (instance == null) {
            instance = new Query();
        }
        return instance;
    }

    private Query(){

    }

    public User findUser(String userid) {
        User user = new User();
        user.userid = userid;
        user.mobile = "电话" + userid;
        user.nick = "别名" + userid;
        user.password = "密码" + userid;
        return user;
    }

    public Session findSession(String sid) {
        Session session = new Session();
        session.sid = sid;
        session.createDate = new Date();
        session.isLogin = Boolean.TRUE;
        session.loginType = "UnKnown";
        session.utdid = String.valueOf(Math.random());
        return session;
    }

    public Device findDevice(String did) {
        Device device = new Device();
        device.did = did;
        device.isTrustDevice = Boolean.TRUE;
        device.type = "android";
        return device;
    }
}
