/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.resultcode;

import java.util.Map;

/**
 * 富文案显示扩展接口
 * 
 * @author madding.lip
 */
public interface IRichResultCode extends IResultCode {

    /**
     * 获取富文本,如：html
     */
    public String getRichMessage(Map<String, Object> param);
}
