package com.madding.shared.components.test.completionservice.sub;

public interface CheckcodeService {

    public String getCheckcodeLink();
    
    public String valid(String sid, String input);
}
