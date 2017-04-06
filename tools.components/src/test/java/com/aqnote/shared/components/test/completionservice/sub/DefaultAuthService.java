/*
 * Copyright (C) 2013-2016 Peng Li<aqnote.com@gmail.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.test.completionservice.sub;

public class DefaultAuthService implements AuthService {

    @Override
    public String auth(Long mid) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "auth";
    }

}
