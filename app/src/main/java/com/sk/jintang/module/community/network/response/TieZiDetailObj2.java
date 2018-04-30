package com.sk.jintang.module.community.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/10/13.
 */

public class TieZiDetailObj2 extends BaseObj {
    /**
     * forum_id : 1
     * title : 美容界的扛把子，你都知道吗？
     * content : 苹果营养丰富，是一种广泛使用的天然美容品，被许多爱美人士奉为美容圣品。苹果中含有0.3%的蛋白质，0.4%的脂肪，0.9%的粗纤维和各种矿物质、芳香醇类等。其所含的大量水分和各种保湿因子对皮肤有保湿作用，维他命C能抑制皮肤中黑色素的沉着，常食苹果可淡化面部雀斑及黄褐斑。另外，苹果中所含的丰富果酸成分可以使毛孔通畅，有祛痘作用。
     * img_url : http://121.40.186.118:5008/upload/a.png
     * photo : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
     * nickname : 哟嚯
     * add_time : 2017/8/31
     * comment_count : 1
     * comment_list : [{"comments_id":1,"content":"哎哟不错哦","photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"哟嚯","comment_time":"56分钟前","reply_list":[{"id":1,"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname1":"13872228829","nickname2":"哟嚯","content":"那肯定不错的咯"}]}]
     */

    private int forum_id;
    private String title;
    private String content;
    private String img_url;
    private String photo;
    private String nickname;
    private String add_time;
    private int comment_count;
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
         * photo : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
         * nickname : 哟嚯
         * comment_time : 56分钟前
         * reply_list : [{"id":1,"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname1":"13872228829","nickname2":"哟嚯","content":"那肯定不错的咯"}]
         */

        private int comments_id;
        private String content;
        private String photo;
        private String nickname;
        private String comment_time;
        private int thumbup_count;//thumbup_count评论点赞数,
        private int is_thumbup;//is_thumbup是否已点赞(0否 1是)
        private List<ReplyListBean> reply_list;

        public int getComments_id() {
            return comments_id;
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

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public List<ReplyListBean> getReply_list() {
            return reply_list;
        }

        public void setReply_list(List<ReplyListBean> reply_list) {
            this.reply_list = reply_list;
        }

        public static class ReplyListBean {
            /**
             * id : 1
             * photo : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
             * nickname1 : 13872228829
             * nickname2 : 哟嚯
             * content : 那肯定不错的咯
             */

            private int id;
            private String photo;
            private String nickname1;
            private String nickname2;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getNickname1() {
                return nickname1;
            }

            public void setNickname1(String nickname1) {
                this.nickname1 = nickname1;
            }

            public String getNickname2() {
                return nickname2;
            }

            public void setNickname2(String nickname2) {
                this.nickname2 = nickname2;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
