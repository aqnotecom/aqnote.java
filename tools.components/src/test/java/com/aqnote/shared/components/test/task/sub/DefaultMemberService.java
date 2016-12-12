/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.test.task.sub;

/**
 * 会员接口实现
 * 
 * @author madding.lip
 */
public class DefaultMemberService implements MemberService {

    @Override
    public Member getMemberById(long mid) {
        return new Member();
    }

    @Override
    public boolean createMember(Member member) {
        return true;
    }

    @Override
    public boolean deleteMemberById(long mid) {
        return false;
    }

}
