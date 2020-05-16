package com.rkele.yxhshop.order.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.order.po.Cart;
import com.rkele.yxhshop.order.vo.*;
import com.rkele.yxhshop.user.po.User;

public interface CartService extends IService<Cart> {

    public CartResultVO getCart(String token);
    public void addGoodsToCart(CartParamVO cartParamDTO,String token);
    public void updateGoods(CartParamVO cartParamDTO,String token);
    public void deleteCartGoods(CartGoodsDeleteVO deleteVO);
    public void checkedCartGoods(CartCheckedVO cartCheckedVO);
    public CartCheckoutVO checkoutCart(Integer addressId, Integer couponId,String token);
}
