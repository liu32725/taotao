package com.taotao.manage.service;

/**
 * @author liuam
 * @version 2018/4/17 0017 下午 14:43 创建文件
 */
public interface Function<T, E> {
    public T callback(E e);
}
