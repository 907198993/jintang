package com.sk.jintang.module.community.network.response;

/**
 * Created by administartor on 2017/9/18.
 */

public class HuaTiObj {
    /**
     * forum_id : 1
     * img_url : http://121.40.186.118:5008/upload/a.png
     * title : 美容界的扛把子，你都知道吗？
     * topic : #美妆直播季节#
     * content : 苹果营养丰富，是一种广泛使用的天然美容品，被许多爱美人士奉为美容圣品。苹果中含有0.3%的蛋白质，0.4%的脂肪，0.9%的粗纤维和各种矿物质、芳香醇类等。其所含的大量水分和各种保湿因子对皮肤有保湿作用，维他命C能抑制皮肤中黑色素的沉着，常食苹果可淡化面部雀斑及黄褐斑。另外，苹果中所含的丰富果酸成分可以使毛孔通畅，有祛痘作用。
     * add_time : 2017/8/31
     * page_view : 6420
     * comment_count : 0
     * thumbup_count : 0
     * is_thumbup : 0
     */

    private int forum_id;
    private String img_url;
    private String title;
    private String topic;
    private String content;
    private String add_time;
    private int page_view;
    private int comment_count;
    private int thumbup_count;
    private int is_thumbup;

    public int getForum_id() {
        return forum_id;
    }

    public void setForum_id(int forum_id) {
        this.forum_id = forum_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getPage_view() {
        return page_view;
    }

    public void setPage_view(int page_view) {
        this.page_view = page_view;
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
