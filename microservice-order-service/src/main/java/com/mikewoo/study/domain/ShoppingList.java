package com.mikewoo.study.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
@Data
public class ShoppingList implements Serializable {

    private Long userId;

    private List<Product> products;
}
