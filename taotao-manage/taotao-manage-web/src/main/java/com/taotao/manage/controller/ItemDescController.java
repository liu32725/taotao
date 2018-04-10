package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author liuam
 * @version 2018/4/10 0010 下午 14:25 创建文件
 */
@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/{itemDescId}", method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryItemDescById(@PathVariable("itemDescId") Long itemDescId) {
        try {
            ItemDesc itemDesc = itemDescService.queryById(itemDescId);
            if (itemDesc == null) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
