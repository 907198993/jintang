package com.sk.jintang.module.category.network.response;


import java.util.List;

/**
 * Created by Administrator on 2018/3/31.
 */

public class CategoryObj {

        private List<TypeListBean> typeList;

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public static class TypeListBean {
            /**
             * goods_type_id : 16
             * goods_type_name : 男装
             * goods_type_parentId : 0
             * goods_type_img :
             */

            private String goods_type_id;
            private String goods_type_name;
            private String goods_type_parentId;
            private String goods_type_img;

            public String getGoods_type_id() {
                return goods_type_id;
            }

            public void setGoods_type_id(String goods_type_id) {
                this.goods_type_id = goods_type_id;
            }

            public String getGoods_type_name() {
                return goods_type_name;
            }

            public void setGoods_type_name(String goods_type_name) {
                this.goods_type_name = goods_type_name;
            }

            public String getGoods_type_parentId() {
                return goods_type_parentId;
            }

            public void setGoods_type_parentId(String goods_type_parentId) {
                this.goods_type_parentId = goods_type_parentId;
            }

            public String getGoods_type_img() {
                return goods_type_img;
            }

            public void setGoods_type_img(String goods_type_img) {
                this.goods_type_img = goods_type_img;
            }
        }

}
