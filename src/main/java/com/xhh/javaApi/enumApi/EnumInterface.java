package com.xhh.javaApi.enumApi;

public interface EnumInterface {

    // 项目状态
    enum ProjectStatus implements EnumInterface{
        NO_ISSUE,//未发布
        ISSUE;//已发布



        private ProjectStatus(){

        }
    }

    //订单状态
    enum  OrderStatus implements  EnumInterface{
        ALREADY, //已生成
        NOT_HAVE; //未生成
    }

}

class InterFaceDemo{

    public static void main(String[] args) {

    }


}
