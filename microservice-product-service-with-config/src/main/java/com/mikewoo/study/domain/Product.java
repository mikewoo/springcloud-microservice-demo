package com.mikewoo.study.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Data
public class Product implements Serializable {

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格（单位：十分之一分）
     */
    private int price;

    /**
     * 商品库存
     */
    private int inventory;

    public Product(Long id, String name, Integer price, Integer inventory) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }
}
