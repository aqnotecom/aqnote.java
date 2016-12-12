/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.serializer;

import java.io.Serializable;

/**
 * 类MemberDo.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Sep 2, 2013 1:48:24 PM
 */
public class MemberDO extends MemberPureDO implements Serializable {

    private static final long serialVersionUID = -6680040702874823390L;
    
    private Long   id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return "id=" + id+";name=" + name;
    }
}
