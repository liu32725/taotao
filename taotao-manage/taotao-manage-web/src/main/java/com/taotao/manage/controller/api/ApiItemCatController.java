package com.taotao.manage.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * @author liuam
 * @version 2018/4/15 0015 下午 13:34 创建文件
 */
@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @Autowired
    private RedisService redisService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<ItemCatResult> queryItemCat() {
//        try {
//            ItemCatResult itemCatResult = itemCatService.queryAllToTree();
//            return ResponseEntity.ok(itemCatResult);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<String> queryItemCatList(@RequestParam(value = "callback",
//            required = false) String callback) {
//        try {
//            ItemCatResult itemCatResult = itemCatService.queryAllToTree();
//            String json = JsonUtils.objectToJson(itemCatResult);
//            if (StringUtils.isEmpty(callback)) {
//                return ResponseEntity.ok(json);
//            }
//            return ResponseEntity.ok(callback + "(" + json + ");");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCatList() {
        String key = "TAO_TAO_MANAGE_ITEM_CAT_ALL";
        String cacheData = redisService.get(key);
        if (StringUtils.isNotEmpty(cacheData)) {
            try {
                return ResponseEntity.ok(MAPPER.readValue(cacheData, ItemCatResult.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ItemCatResult itemCatResult = this.itemCatService.queryAllToTree();
            redisService.set(key, MAPPER.writeValueAsString(itemCatResult), 60*60*24*30*3);
            return ResponseEntity.ok(itemCatResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
