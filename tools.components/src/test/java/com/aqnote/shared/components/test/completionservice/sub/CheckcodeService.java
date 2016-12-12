/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.test.completionservice.sub;

public interface CheckcodeService {

    public String getCheckcodeLink();
    
    public String valid(String sid, String input);
}
