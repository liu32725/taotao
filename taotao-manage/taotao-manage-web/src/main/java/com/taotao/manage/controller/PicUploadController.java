package com.taotao.manage.controller;

import com.taotao.common.bean.PicUploadResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.manage.service.PropertiesService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author liuam
 * @version 2018/4/10 0010 下午 15:52 创建文件
 */
@Controller
@RequestMapping("/pic")
public class PicUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg", ".jpeg", ".gif", ".png" };

    @Autowired
    private PropertiesService propertiesService;

    /**
     *
     * @param uploadFile
     * @param response
     * @return 返回文本类型的json数据
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response) throws IOException {
        // 校验图片格式
        boolean isLegal = false;
        for (String s : IMAGE_TYPE) {
            if(StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), s)) {
                isLegal = true;
                break;
            }
        }
        // 封装Result对象，并且将文件的byte数组放置到result对象中
        PicUploadResult picUploadResult = new PicUploadResult();

        // 状态
        picUploadResult.setError(isLegal ? 0 : 1);

        // 文件新路径
        String filePath = getFilePath(uploadFile.getOriginalFilename());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Pic file upload .[{}] to [{}] .", uploadFile.getOriginalFilename(), filePath);
        }
        // 生成图片的绝对引用地址
        String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, propertiesService.REPOSITORY_PATH),
                "\\", "/");
        picUploadResult.setUrl(propertiesService.IMAGE_BASE_URL + picUrl);
        File newFile = new File(filePath);

        // 写文件到磁盘
        uploadFile.transferTo(newFile);
        // 校验图片是否合法
        isLegal = false;
        try {
            BufferedImage image = ImageIO.read(newFile);
            if (image != null) {
                picUploadResult.setWidth(image.getWidth() + "");
                picUploadResult.setHeight(image.getHeight() + "");
                isLegal = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 状态
        picUploadResult.setError(isLegal ? 0 : 1);

        if (!isLegal) {
            // 不合法，将磁盘上的文件删除
            newFile.delete();
        }

        //将java对象序列化为json字符串
        return JsonUtils.objectToJson(picUploadResult);
    }

    private String getFilePath(String sourceFileName) {
        String baseFolder = propertiesService.REPOSITORY_PATH;
        Date date = new Date();
        //yyyy/MM/dd
        String fileFolder = baseFolder + File.separator + new DateTime(date).toString("yyyy")
                + File.separator + new DateTime(date).toString("MM") + File.separator
                + new DateTime(date).toString("dd");
        File file = new File(fileFolder);
        if(!file.isDirectory()) {
            //如果目录不存在，创建目录
            file.mkdirs();
        }
        String fileName = new DateTime(date).toString("yyyyMMddhhmmssSSSS")
                + RandomUtils.nextInt(100, 9999) + "." +StringUtils.substringAfterLast(sourceFileName, ".");
        return fileFolder + File.separator + fileName;
    }
}
