package com.sk.jintang.module.community.network.response;

import android.util.Log;

import com.github.androidtools.DateUtils;
import com.google.gson.annotations.SerializedName;
import com.sk.jintang.base.BaseObj;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class TieZiAllEvaluateObj extends BaseObj {
    /**
     * comments_id : 1
     * photo : http://121.40.186.118:5008/upload/201711/02/171102170350530452.jpg
     * nickname : 一人饮酒醉
     * thumbup_count : 3
     * comment_time : 1510208284
     * content : 哎哟不错哦
     * reply_list : [{"reply_id":4,"photo":"http://121.40.186.118:5008/upload/201710/23/171023105612304452.jpg","nickname":"狂风绝息斩","nickname_to":"亚索","content":"必须不错啊自行车","code":"reply","comment_time":1510191893,"thumbup_count":2,"is_thumbup":0},{"reply_id":3,"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"亚索","nickname_to":"","content":"还不错第三方第三方利息支出酷暑难当放假了在","code":"commen","comment_time":1510191805,"thumbup_count":0,"is_thumbup":0},{"reply_id":2,"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"亚索","nickname_to":"","content":"那肯定错的咯胜多负少的免费，水电费了整理出来","code":"commen","comment_time":1507888092,"thumbup_count":0,"is_thumbup":0},{"reply_id":1,"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"亚索","nickname_to":"","content":"那肯定不错的咯介绍的开发你是想山东济南看返回再","code":"commen","comment_time":1507885263,"thumbup_count":0,"is_thumbup":0}]
     */

    private int comments_id;
    private String photo;
    private String nickname;
    private int thumbup_count;
    private long comment_time;
    private String content;
    private int reply_count;

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    private List<ReplyListBean> reply_list;

    public int getComments_id() {
        return comments_id;
    }

    public void setComments_id(int comments_id) {
        this.comments_id = comments_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getThumbup_count() {
        return thumbup_count;
    }

    public void setThumbup_count(int thumbup_count) {
        this.thumbup_count = thumbup_count;
    }

    public long getComment_time() {
        return comment_time;
    }

    public void setComment_time(long comment_time) {
        this.comment_time = comment_time;
    }
    public String getFormatTime(){
        long time = getComment_time() * 1000;
        long newTime = new Date().getTime();
        long resultTime=newTime-time;
        if(resultTime>=(24*60*60*1000)){
            Log.i("===","==="+time);
            return DateUtils.dateToString(new Date(time),"yyyy-MM-dd");
        }else if(resultTime>=(2*60*60*1000)){
            int hour = (int) (resultTime / (60 * 60 * 1000));
            return hour+"小时前";
        }else{
            return DateUtils.dateToString(new Date(time),"HH:mm:ss");
        }
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReplyListBean> getReply_list() {
        return reply_list;
    }

    public void setReply_list(List<ReplyListBean> reply_list) {
        this.reply_list = reply_list;
    }

    public static class ReplyListBean {
        /**
         * reply_id : 4
         * photo : http://121.40.186.118:5008/upload/201710/23/171023105612304452.jpg
         * nickname : 狂风绝息斩
         * nickname_to : 亚索
         * content : 必须不错啊自行车
         * code : reply
         * comment_time : 1510191893
         * thumbup_count : 2
         * is_thumbup : 0
         */

        private int reply_id;
        private String photo;
        private String nickname;
        private String nickname_to;
        private String content;
        private String code;
        private long comment_time;
        private int thumbup_count;
        @SerializedName("is_thumbup")
        private int is_thumbupX;

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname_to() {
            return nickname_to;
        }

        public void setNickname_to(String nickname_to) {
            this.nickname_to = nickname_to;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public long getComment_time() {
            return comment_time;
        }

        public void setComment_time(long comment_time) {
            this.comment_time = comment_time;
        }
        public String getFormatTime(){
            long time = getComment_time() * 1000;
            long newTime = new Date().getTime();
            long resultTime=newTime-time;
            if(resultTime>=(24*60*60*1000)){
                Log.i("===","==="+time);
                return DateUtils.dateToString(new Date(time),"yyyy-MM-dd");
            }else if(resultTime>=(2*60*60*1000)){
                int hour = (int) (resultTime / (60 * 60 * 1000));
                return hour+"小时前";
            }else{
                return DateUtils.dateToString(new Date(time),"HH:mm:ss");
            }
        }
        public int getThumbup_count() {
            return thumbup_count;
        }

        public void setThumbup_count(int thumbup_count) {
            this.thumbup_count = thumbup_count;
        }

        public int getIs_thumbupX() {
            return is_thumbupX;
        }

        public void setIs_thumbupX(int is_thumbupX) {
            this.is_thumbupX = is_thumbupX;
        }
    }
}
