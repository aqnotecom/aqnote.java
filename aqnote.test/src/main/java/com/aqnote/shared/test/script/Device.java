/*
 * Copyright 2013-2023 Peng Li <aqnote@qq.com> Licensed under the AQNote License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.aqnote.com/licenses/LICENSE-1.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.aqnote.shared.test.script;

/**
 * Device.java desc：TODO
 * 
 * @author "Peng Li"<aqnote@aqnote.com> Jan 9, 2017 5:01:03 PM
 */
public class Device {

    public String  did;
    // 设备类型：ios,andriod
    public String  type;
    // 是否可信设备：
    public Boolean isTrustDevice;

    public String toString() {
        return "Device[" + did + " " + " " + type + " " + isTrustDevice + "]";
    }
}
