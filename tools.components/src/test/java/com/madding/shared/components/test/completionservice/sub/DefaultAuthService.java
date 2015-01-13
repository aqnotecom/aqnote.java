package com.madding.shared.components.test.completionservice.sub;

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
