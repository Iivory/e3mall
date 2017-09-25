package cn.e3.order.service;

import cn.e3.common.utils.E3Result;
import cn.e3.order.pojo.OrderInfo;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}
