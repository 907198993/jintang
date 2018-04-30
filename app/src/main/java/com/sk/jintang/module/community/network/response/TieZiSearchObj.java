package com.sk.jintang.module.community.network.response;

/**
 * Created by Administrator on 2017/11/15.
 */

public class TieZiSearchObj  {

    /**
     * forum_id : 91
     * title : 111111111
     * content : 哈哈哈哈哈 哈哈哈哈哈哈哈
     * img_url : http://121.40.186.118:5008/upload/201711/10/171110114424091831.jpg
     * is_hot : 0
     * page_view : 15
     * add_time : 2017/11/10
     * comment_count : 1
     * thumbup_count : 2
     * is_thumbup : 0
     */

    private int forum_id;
    private String title;
    private String content;
    private String img_url;
    private int is_hot;
    private int page_view;
    private String add_time;
    private int comment_count;
    private int thumbup_count;
    private int is_thumbup;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
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

    public int getIs_thumbup() {
        return is_thumbup;
    }

    public void setIs_thumbup(int is_thumbup) {
        this.is_thumbup = is_thumbup;
    }
}
