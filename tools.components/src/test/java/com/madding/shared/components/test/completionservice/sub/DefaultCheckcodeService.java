package com.madding.shared.components.test.completionservice.sub;

public class DefaultCheckcodeService implements CheckcodeService {

    public String getCheckcodeLink() {
        return "http://checkcode.com/sid/EANDK-DJLDL-124";
    }

    public String valid(String sid, String input) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "checkcode";
        // return StringUtils.equals(sid, input);
    }

}
