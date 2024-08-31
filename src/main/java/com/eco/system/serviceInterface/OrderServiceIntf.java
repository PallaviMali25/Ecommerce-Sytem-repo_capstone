package com.eco.system.serviceInterface;

import com.eco.system.beans.PlaceOrderRequest;
import com.eco.system.entity.Order;

public interface OrderServiceIntf {

    Order getOrderById(Integer orderId);

    Order placeOrder(PlaceOrderRequest placeOrderRequest);
}
