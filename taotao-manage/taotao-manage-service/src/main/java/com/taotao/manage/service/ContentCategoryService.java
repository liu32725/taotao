package com.taotao.manage.service;

import com.taotao.manage.pojo.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuam
 * @version 2018/4/16 0016 下午 16:49 创建文件
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    public ContentCategory saveAndUpdateParent(ContentCategory contentCategory) {
        contentCategory.setId(null);
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        this.save(contentCategory);

        ContentCategory parent = this.queryById(contentCategory.getParentId());
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            this.updateSelective(parent);
        }
        return contentCategory;
    }

    public void deleteAll(ContentCategory contentCategory) {
        List<Object> ids = new ArrayList<>();
        ids.add(contentCategory.getId());
        this.findAllSubNode(ids, contentCategory.getId());
        super.deleteByIds(ContentCategory.class, "id", ids);
        // 判断该节点是否还有兄弟节点，如果没有，修改父节点的 isParent 为 false
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> list = super.queryListByWhere(record);
        if (list == null || list.isEmpty()) {
            ContentCategory parent = new ContentCategory();
            parent.setIsParent(false);
            parent.setId(contentCategory.getParentId());
            super.updateSelective(parent);
        }
    }

    private void findAllSubNode(List<Object> ids, Long pid) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(pid);
        List<ContentCategory> list = super.queryListByWhere(contentCategory);
        for (ContentCategory category : list) {
            ids.add(category);
            // 判断是否为父节点，如果是，继续调用该方法查找子节点
            if(category.getIsParent()) {
                // 开始递归
                findAllSubNode(ids, category.getId());
            }
        }
    }
}
