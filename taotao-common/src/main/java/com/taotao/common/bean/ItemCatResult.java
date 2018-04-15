package com.taotao.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuam
 * @version 2018/4/15 0015 下午 13:29 创建文件
 */
public class ItemCatResult {
    @JsonProperty("data") //指定对象序列化成josn时的名称
    private List<ItemCatData> itemCats = new ArrayList<>();

    public List<ItemCatData> getItemCats() {
        return itemCats;
    }

    public void setItemCats(List<ItemCatData> itemCats) {
        this.itemCats = itemCats;
    }
}
