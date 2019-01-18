package com.mikewoo.study.service;

import com.mikewoo.study.domain.Order;
import com.mikewoo.study.domain.ShoppingList;

import java.io.IOException;

/**
 * @author Eric Gui
 * @date 2019/1/17
 */
public interface OrderService {

    Order save(ShoppingList shoppingList) throws IOException;
}
