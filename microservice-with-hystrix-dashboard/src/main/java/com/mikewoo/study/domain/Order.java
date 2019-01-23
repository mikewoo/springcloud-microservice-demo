package com.mikewoo.study.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Data
public class Order implements Serializable {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 购买的商品信息列表
     */
    private List<Product> products;

    /**
     * 订单总金额
     */
    private Integer totalPay;

    /**
     * 订单创建时间
     */
    private Date createTime;

}
