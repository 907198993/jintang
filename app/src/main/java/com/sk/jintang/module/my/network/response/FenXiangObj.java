package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by Administrator on 2017/11/1.
 */

public class FenXiangObj extends BaseObj {
    /**
     * title : 爱尚养御可以下载啦!
     * content : 爱尚养御是一款综合性功能的APP，为以足浴沐浴养生行业为中心的整个闭环产业链提供全方位一站式服务，平台整合了行业资源以满足各方面业者的一切所需，让您足不出户就能方便快捷地寻找到性价比更高的资源与服务。
     * share_link : https://www.baidu.com
     */

    private String title;
    private String content;
    private String share_link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}
