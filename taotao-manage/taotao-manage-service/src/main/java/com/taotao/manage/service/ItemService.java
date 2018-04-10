package com.taotao.manage.service;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuam
 * @version 2018/4/10 0010 下午 14:35 创建文件
 */
@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemDescService itemDescService;

    public boolean saveItem(Item item, String desc){
        item.setStatus(1);
        item.setId(null);//出于安全考虑，强制设为null
        Integer count1 = this.save(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 = itemDescService.save(itemDesc);
        return count1.intValue() == 1 && count2.intValue() == 1;
    }

}
