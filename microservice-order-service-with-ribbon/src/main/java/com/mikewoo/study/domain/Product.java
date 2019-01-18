package com.mikewoo.study.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
     * 商品购买价格（单位十分之一分）
     */
    private Integer price;

    /**
     * 购买数量
     */
    private Integer count;

    public Product(Long id, String name, Integer count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
