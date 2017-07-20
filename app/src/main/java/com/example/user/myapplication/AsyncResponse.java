package com.example.user.myapplication;


/**
 * Created by user on 2017/7/20.
 */

public interface AsyncResponse<T extends  Object> {
    void taskFinish(T result);
}
