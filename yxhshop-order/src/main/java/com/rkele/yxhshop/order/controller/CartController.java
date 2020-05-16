package com.rkele.yxhshop.order.controller;

import com.rkele.yxhshop.common.contans.JWTConstants;
import com.rkele.yxhshop.common.utils.Result;
import com.rkele.yxhshop.order.service.CartService;
import com.rkele.yxhshop.order.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.rkele.yxhshop.common.api.BaseApi;
import com.rkele.yxhshop.order.api.CartApi;
import com.rkele.yxhshop.order.po.Cart;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/cart")
@RestController
public class CartController extends BaseApi<Cart> implements CartApi {

    @Autowired
    CartService cartService;

    @GetMapping("/index")
    public Result<CartResultVO> getCart(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        return Result.success(cartService.getCart(token));
    }

    @PostMapping("/add")
    public Result<CartResultVO> addGoodsToCart(@RequestBody @Validated CartParamVO cartParamDTO, HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        cartService.addGoodsToCart(cartParamDTO,token);
        return Result.success(cartService.getCart(token));
    }

    @PostMapping("/update")
    public Result<CartResultVO> updateCartGoods(@RequestBody @Validated(CartParamVO.CartUpdateChecks.class) CartParamVO cartParamDTO,HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        cartService.updateGoods(cartParamDTO,token);
        return Result.success(cartService.getCart(token));
    }

    @PostMapping("/delete")
    public Result<CartResultVO> deleteCartGoods(@RequestBody @Validated CartGoodsDeleteVO cartGoodsDeleteVO,HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        cartService.deleteCartGoods(cartGoodsDeleteVO);
        return Result.success(cartService.getCart(token));
    }

    @PostMapping("/checked")
    public Result<CartResultVO> checkedCartGoods(@RequestBody @Validated CartCheckedVO cartCheckedVO, HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        cartService.checkedCartGoods(cartCheckedVO);
        return Result.success(cartService.getCart(token));
    }

    @GetMapping("/goods-count")
    public Result<Integer> goodsCount(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        CartResultVO cart = cartService.getCart(token);
        CartResultVO.CartTotalVO cartTotal = cart.getCartTotal();
        return Result.success(cartTotal.getGoodsCount());
    }

    @GetMapping("/checkout")
    public Result<CartCheckoutVO> checkoutCartGoods(Integer addressId, Integer couponId,HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.JWT_KEY_NAME);
        return Result.success(cartService.checkoutCart(addressId, couponId,token));
    }
}
