package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author liuam
 * @version 2018/4/10 0010 下午 17:59 创建文件
 */
@Service
public class PropertiesService {
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;

    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
}
