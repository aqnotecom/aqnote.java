package com.madding.shared.components.test.task.sub;

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
