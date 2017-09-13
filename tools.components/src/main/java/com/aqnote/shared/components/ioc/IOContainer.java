package com.aqnote.shared.components.ioc;

import java.io.InputStream;

/**
 * Created by aqnote on 6/13/17.
 *
 * @author "Peng Li"<aqnote@qq.com>
 */
public interface IOContainer {

    public <T> T getBean(String id);

    public <T> T getBean(String id, Object... args);

    public <T> void getBeanAsync(String id, Callbackable<T> callback);

    public <T> void getBeanAsync(String id, Callbackable<T> callback, Object... args);

    public void init(InputStream... in);

    public void destory();

    public int size();
}
