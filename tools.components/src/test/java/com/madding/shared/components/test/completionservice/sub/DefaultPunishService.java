package com.madding.shared.components.test.completionservice.sub;

public class DefaultPunishService implements PunishService {

    @Override
    public String isPunish(Long mid) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "punish";
    }

}
