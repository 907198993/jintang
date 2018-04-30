package com.sk.jintang.module.shoppingcart.network.response;

import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

public class ShopGoodsTypeObj  {


    /**
     * ErrCode : 0
     * ErrMsg :
     * Response : {"typeList":[{"typeName":"春季打折","typeId":"22"},{"typeName":"时尚女装","typeId":"23"},{"typeName":"时尚男装","typeId":"21"}]}
     */


        private List<TypeListBean> typeList;

        public List<TypeListBean> getTypeList() {
            return typeList;
        }

        public void setTypeList(List<TypeListBean> typeList) {
            this.typeList = typeList;
        }

        public static class TypeListBean {
            /**
             * typeName : 春季打折
             * typeId : 22
             */

            private String typeName;
            private String typeId;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getTypeId() {
                return typeId;
            }

            public void setTypeId(String typeId) {
                this.typeId = typeId;
            }
        }

}
