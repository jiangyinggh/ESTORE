package com.bvear.estore.common.bean;

/**
 * 支付状态
 *
 * @author jiangying
 * @email jiangying.man@qq.com
 * @date 2018/8/2
 */
public enum  PayStatus {
    NotPaid("未付款"),
    Paid("已付款");

    private String desc;//中文描述

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    private PayStatus(String desc){
        this.desc=desc;
    }

    /**
     * 覆盖
     * @return
     */
    @Override
    public String toString() {
        return desc;
    }

    public String getName(){
        return name();
    }

    public int getOrdinal(){
        return ordinal();
    }
    public String getString(){
        return toString();
    }
}
