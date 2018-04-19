package com.taotao.web.controller;

import com.taotao.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author liuam
 * @version 2018/4/13 0013 上午 9:45 创建文件
 */
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        String indexAd1 = indexService.queryIndexAd1();
        modelAndView.addObject("indexAd1", indexAd1);
        return modelAndView;
    }

}
