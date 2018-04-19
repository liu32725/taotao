package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuam
 * @version 2018/4/16 0016 下午 17:59 创建文件
 */
@Repository
public interface ContentMapper extends Mapper<Content> {
    List<Content> queryListByCategoryId(Long categoryId);
}
