package com.madding.shared.components.test.completionservice;

import java.util.Map;
import java.util.Vector;

import com.madding.shared.components.completionservice.CallReturnModelPool;
import com.madding.shared.components.completionservice.CallServiceModel;
import com.madding.shared.components.completionservice.MadCompletionService;
import com.madding.shared.components.completionservice.SpringConfig;
import com.madding.shared.components.test.completionservice.sub.AuthService;
import com.madding.shared.components.test.completionservice.sub.CheckcodeService;
import com.madding.shared.components.test.completionservice.sub.PunishService;

/**
 * 类CompletionServiceTest.java的实现描述：并发远程调用服务
 * 
 * @author madding.lip Aug 8, 2013 10:33:33 AM
 */
public class MadCompletionServiceTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        int i = 0;
        int N = 10000;
        long start = System.currentTimeMillis();
        while (i++ < N) {
            concurrentInvoke(i, -i);
        }
        long end = System.currentTimeMillis();
        System.out.print("concurrentInvoke: ");
        System.out.println(end - start);

//        i = 0;
//        start = System.currentTimeMillis();
//        while (i++ < N) {
//            invoke();
//        }
//        end = System.currentTimeMillis();
//        System.out.print("invoke: ");
//        System.out.println(end - start);
    }

    public static void concurrentInvoke(long i,long j) {
        MadCompletionService mad = new MadCompletionService();
        Vector<CallServiceModel> models = new Vector<CallServiceModel>();
        models.add(new CallServiceModel("authService", "auth", i)); // memberId
        models.add(new CallServiceModel("checkcodeService", "valid", String.valueOf(i), String.valueOf(j)));
        models.add(new CallServiceModel("punishService", "isPunish", j));
        mad.init(models);

        mad.submitService();
        mad.waitService();
        Map<CallServiceModel, Object> resultPool = CallReturnModelPool.getCallReturnPool();

        @SuppressWarnings("unused")
        String authResult = (String) resultPool.get(models.get(0));

        @SuppressWarnings("unused")
        String checkcodeValidResult = (String) resultPool.get(models.get(1));

        @SuppressWarnings("unused")
        String punishResult = (String) resultPool.get(models.get(2));

//        System.out.println("authService.auth: " + authResult);
//        System.out.println("checkcodeService.valid: " + checkcodeValidResult);
//        System.out.println("punishService.isPunish: " + punishResult);
        
        CallReturnModelPool.clean(models);
    }

    public static void invoke() {
        AuthService authService = (AuthService) SpringConfig.context.getBean("authService");
        CheckcodeService checkcodeService = (CheckcodeService) SpringConfig.context.getBean("checkcodeService");
        PunishService punishService = (PunishService) SpringConfig.context.getBean("punishService");

        @SuppressWarnings("unused")
        String authResult = authService.auth(1234567l);

        @SuppressWarnings("unused")
        String checkcodeValidResult = (String) checkcodeService.valid("1", "1");

        @SuppressWarnings("unused")
        String punishResult = (String) punishService.isPunish(1234567l);

//        System.out.println("authService.auth: " + authResult);
//        System.out.println("checkcodeService.valid: " + checkcodeValidResult);
//        System.out.println("punishService.isPunish: " + punishResult);
    }
}
