package com.sk.jintang.module.community.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by Administrator on 2017/11/1.
 */

public class YangShengXiaoZhiShiObj extends BaseObj {
    /**
     * forum_id : 5
     * title : 美容界的扛把子，你都知道吗？
     * zhaiyao : 苹果营养丰富，是一种广泛使用的天然美容品，被许多爱美人士奉为美容圣品。苹果中含有0.3%的蛋白质，0.4%的脂肪，0.9%的粗纤维和各种矿物质、芳香醇类等
     * img_url : http://121.40.186.118:5008/upload/a.png
     * page_view : 3050
     * add_time : 2017/8/31
     * comment_count : 0
     * thumbup_count : 0
     */

    private int forum_id;
    private String title;
    private String zhaiyao;
    private String img_url;
    private int page_view;
    private String add_time;
    private int comment_count;
    private int thumbup_count;

    public int getForum_id() {
        return forum_id;
    }

    public void setForum_id(int forum_id) {
        this.forum_id = forum_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZhaiyao() {
        return zhaiyao;
    }

    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPage_view() {
        return page_view;
    }

    public void setPage_view(int page_view) {
        this.page_view = page_view;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getThumbup_count() {
        return thumbup_count;
    }

    public void setThumbup_count(int thumbup_count) {
        this.thumbup_count = thumbup_count;
    }
}
