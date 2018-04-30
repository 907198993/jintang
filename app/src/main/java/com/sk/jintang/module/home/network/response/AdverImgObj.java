package com.sk.jintang.module.home.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by Administrator on 2017/12/11.
 */

public class AdverImgObj extends BaseObj {
    /**
     * image_url : http://121.40.186.118:5008/upload/201712/08/201712081610559301.png
     * link : http://www.baidu.com
     */

    private String image_url;
    private String link;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
