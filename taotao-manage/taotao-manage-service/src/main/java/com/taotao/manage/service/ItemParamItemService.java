package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuam
 * @version 2018/4/12 0012 上午 10:20 创建文件
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    public Integer updateItemParamItem(Long itemId, String itemParams) {
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        itemParamItem.setItemId(itemId);

        Example example = new Example(ItemParamItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        int count = itemParamItemMapper.updateByExample(itemParamItem, example);
        return count;
    }
}
