package com.taotao.manage.controller;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuam
 * @version 2018/4/10 0010 上午 11:19 创建文件
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    @RequestMapping(value = "/{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId) {
        ItemParam record = new ItemParam();
        record.setItemCatId(itemCatId);
        try {
            ItemParam itemParam = itemParamService.queryOne(record);
            if (itemParam == null) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //200
            return ResponseEntity.ok(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId,
                                              @RequestParam("paramData") String paramData) {
        try {
            ItemParam recode = new ItemParam();
            recode.setId(null);
            recode.setParamData(paramData);
            recode.setItemCatId(itemCatId);
            itemParamService.save(recode);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> getItemParamList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
        try {
            PageInfo<ItemParam> pageInfo = itemParamService.queryListByWhere(page, rows, null);
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /*@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteItemParam(@RequestParam("params")Long[] ids) {
        try {
            List<Long> list = Arrays.asList(ids);
            for (Long l : list) {
                Integer integer = itemParamService.deleteById(l);
            }
            if(count != 0) {
                return ResponseEntity.ok(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }*/
}
