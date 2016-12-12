/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.test.completionservice.sub;

public class DefaultCheckcodeService implements CheckcodeService {

    @Override
    public String getCheckcodeLink() {
        return "http://checkcode.com/sid/EANDK-DJLDL-124";
    }

    @Override
    public String valid(String sid, String input) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "checkcode";
        // return StringUtil.equals(sid, input);
    }

}
