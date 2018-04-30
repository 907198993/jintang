package com.sk.jintang.module.home.network.response;

import java.util.List;

/**
 * Created by administartor on 2017/9/13.
 */

public class FactoryObj {

    /**
     * xilie_name : 办公用品系列
     * factory_list : [{"factory_id":30,"factory_name":"布草直供","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":31,"factory_name":"耗品用品类","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":32,"factory_name":"二手设备","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":33,"factory_name":"办公文具","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":34,"factory_name":"工艺摆件","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":35,"factory_name":"茶具果盘","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":36,"factory_name":"影院式足道","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":37,"factory_name":"茶桌麻将桌","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":38,"factory_name":"电器电视","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":39,"factory_name":"沙发家具","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":40,"factory_name":"家具设备类","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":41,"factory_name":"注册公司","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":42,"factory_name":"维修服务","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":43,"factory_name":"保洁服务","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":44,"factory_name":"窗帘定制","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":45,"factory_name":"热水系统","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"},{"factory_id":54,"factory_name":"招聘求职","factory_image":"http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg"}]
     */

    private String xilie_name;
    private List<FactoryListBean> factory_list;

    public String getXilie_name() {
        return xilie_name;
    }

    public void setXilie_name(String xilie_name) {
        this.xilie_name = xilie_name;
    }

    public List<FactoryListBean> getFactory_list() {
        return factory_list;
    }

    public void setFactory_list(List<FactoryListBean> factory_list) {
        this.factory_list = factory_list;
    }

    public static class FactoryListBean {
        /**
         * factory_id : 30
         * factory_name : 布草直供
         * factory_image : http://121.40.186.118:5008/upload/201712/22/201712221145294962.jpg
         */

        private int factory_id;
        private String factory_name;
        private String factory_image;

        public int getFactory_id() {
            return factory_id;
        }

        public void setFactory_id(int factory_id) {
            this.factory_id = factory_id;
        }

        public String getFactory_name() {
            return factory_name;
        }

        public void setFactory_name(String factory_name) {
            this.factory_name = factory_name;
        }

        public String getFactory_image() {
            return factory_image;
        }

        public void setFactory_image(String factory_image) {
            this.factory_image = factory_image;
        }
    }
}
