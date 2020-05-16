package com.rkele.yxhshop.order.mapper;

import com.rkele.yxhshop.common.utils.MyMapper;
import com.rkele.yxhshop.order.po.Cart;

public interface CartMapper extends MyMapper<Cart> {

    int updateNumberById(Integer number, Integer id);

}
