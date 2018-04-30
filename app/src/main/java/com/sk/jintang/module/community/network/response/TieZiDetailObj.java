package com.sk.jintang.module.community.network.response;

import android.util.Log;

import com.github.androidtools.DateUtils;
import com.google.gson.annotations.SerializedName;
import com.sk.jintang.base.BaseObj;

import java.util.Date;
import java.util.List;

/**
 * Created by administartor on 2017/10/13.
 */

public class TieZiDetailObj extends BaseObj {

    /**
     * forum_id : 1
     * title : 美容界的扛把子，你都知道吗？
     * content : 啦啦啦<img src="http://121.40.186.118:5008/upload/201710/13/171013143150189103.jpg"/>
     * img_url : http://121.40.186.118:5008/upload/a.png
     * photo : http://121.40.186.118:5008/upload/201711/02/171102170350530452.jpg
     * nickname : 一人饮酒醉
     * add_time : 2017/11/30
     * comment_count : 3
     * thumbup_count : 4
     * comment_list : [{"comments_id":1,"content":"哎哟不错哦","photo":"http://121.40.186.118:5008/upload/201711/02/171102170350530452.jpg","nickname":"一人饮酒醉","is_thumbup":1,"comment_time":1507875484,"thumbup_count":2,"reply_count":4,"reply_list":[{"reply_id":3,"nickname":"德玛西亚","nickname_to":"","content":"还不错","code":"commen"},{"reply_id":2,"nickname":"德玛西亚","nickname_to":"","content":"那肯定错的咯","code":"commen"},{"reply_id":1,"nickname":"德玛西亚","nickname_to":"","content":"那肯定不错的咯","code":"commen"}]}]
     */

    private int forum_id;
    private String title;
    private String content;
    private String img_url;
    private String photo;
    private String nickname;
    private String add_time;
    private int comment_count;
    private int thumbup_count;
    private List<CommentListBean> comment_list;

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

    public List<CommentListBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentListBean> comment_list) {
        this.comment_list = comment_list;
    }

    public static class CommentListBean {
        /**
         * comments_id : 1
         * content : 哎哟不错哦
         * photo : http://121.40.186.118:5008/upload/201711/02/171102170350530452.jpg
         * nickname : 一人饮酒醉
         * is_thumbup : 1
         * comment_time : 1507875484
         * thumbup_count : 2
         * reply_count : 4
         * reply_list : [{"reply_id":3,"nickname":"德玛西亚","nickname_to":"","content":"还不错","code":"commen"},{"reply_id":2,"nickname":"德玛西亚","nickname_to":"","content":"那肯定错的咯","code":"commen"},{"reply_id":1,"nickname":"德玛西亚","nickname_to":"","content":"那肯定不错的咯","code":"commen"}]
         */

        private int comments_id;
        private String content;
        private String photo;
        private String nickname;
        @SerializedName("is_thumbup")
        private int is_thumbupX;
        private long comment_time;
        private int thumbup_count;
        private int reply_count;
        private List<ReplyListBean> reply_list;

        public int getComments_id() {
            return comments_id;
        }

        public void setComments_id(int comments_id) {
            this.comments_id = comments_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getIs_thumbupX() {
            return is_thumbupX;
        }

        public void setIs_thumbupX(int is_thumbupX) {
            this.is_thumbupX = is_thumbupX;
        }

        public long getComment_time() {
            return comment_time;
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

        public void setComment_time(long comment_time) {
            this.comment_time = comment_time;
        }

        public int getThumbup_count() {
            return thumbup_count;
        }

        public void setThumbup_count(int thumbup_count) {
            this.thumbup_count = thumbup_count;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public List<ReplyListBean> getReply_list() {
            return reply_list;
        }

        public void setReply_list(List<ReplyListBean> reply_list) {
            this.reply_list = reply_list;
        }

        public static class ReplyListBean {
            /**
             * reply_id : 3
             * nickname : 德玛西亚
             * nickname_to :
             * content : 还不错
             * code : commen
             */

            private int reply_id;
            private String nickname;
            private String nickname_to;
            private String content;
            private String code;

            public int getReply_id() {
                return reply_id;
            }

            public void setReply_id(int reply_id) {
                this.reply_id = reply_id;
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
        }
    }
}
