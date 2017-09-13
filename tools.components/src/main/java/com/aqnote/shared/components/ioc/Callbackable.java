package com.aqnote.shared.components.ioc;

/**
 * Created by aqnote on 6/13/17.
 *
 * @author "Peng Li"<aqnote@qq.com>
 */
public interface Callbackable<T> {

    // 回调处理逻辑
    public void call(Result<T> result);

}


class Result<T> {
    int code;
    String message;
    T bean;
    Exception e;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}