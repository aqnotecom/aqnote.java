/*
 * Copyright 2013-2023 Peng Li <madding.lip@gmail.com> Licensed under the AQNote License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.aqnote.com/licenses/LICENSE-1.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.aqnote.shared.test.script;

import java.util.Date;

/**
 * Session.java desc：TODO
 * 
 * @author madding Jan 9, 2017 5:00:55 PM
 */
public class Session {

    // 会话id
    public String  sid;
    // 是否登陆
    public Boolean isLogin;
    // 会员id
    public long    userid;
    // 登陆类型
    public String  loginType;
    // 设备id
    public String  utdid;
    // 登陆日期
    public Date    createDate;

    
    public String toString() {
        return "Session[" + sid + " " + isLogin + " " + userid + " " + loginType + " " + utdid + " " + createDate + "]";
    }
}
