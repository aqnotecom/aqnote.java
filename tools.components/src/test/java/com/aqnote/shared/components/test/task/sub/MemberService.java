/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.test.task.sub;

/**
 * 会员对外暴露服务接口
 * 
 * @author madding.lip
 */
public interface MemberService {

    public Member getMemberById(long mid);

    public boolean createMember(Member member);

    public boolean deleteMemberById(long mid);

}
