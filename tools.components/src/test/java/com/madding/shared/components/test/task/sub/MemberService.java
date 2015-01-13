package com.madding.shared.components.test.task.sub;


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
