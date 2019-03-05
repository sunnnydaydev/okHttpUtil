package com.example.administrator.okhttputil.net.listener;

/**
 * Create by SunnyDay on 2019/03/05
 */
public class DisposeDataHandle<T> {
    public DisposeDataListener listener = null;
    public Class<?> mClass = null;

    public DisposeDataHandle(DisposeDataListener listener) {
        this.listener = listener;
    }


    public DisposeDataHandle(DisposeDataListener listener,Class<?> mClass ) {
        this.listener = listener;
        this.mClass = mClass;

    }
}
