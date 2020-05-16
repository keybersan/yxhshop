package com.rkele.yxhshop.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.enums.YxhshopWechatResultStatus;
import com.rkele.yxhshop.common.exception.YxhshopException;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.goods.api.GoodsApi;
import com.rkele.yxhshop.goods.api.GoodsSpecificationApi;
import com.rkele.yxhshop.goods.api.ProductApi;
import com.rkele.yxhshop.goods.po.Goods;
import com.rkele.yxhshop.goods.po.Product;
import com.rkele.yxhshop.order.po.Cart;
import com.rkele.yxhshop.order.service.CartService;
import com.rkele.yxhshop.order.vo.*;
import com.rkele.yxhshop.user.api.AddressApi;
import com.rkele.yxhshop.user.api.RegionApi;
import com.rkele.yxhshop.user.api.UserCouponApi;
import com.rkele.yxhshop.user.po.Address;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.po.UserCoupon;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl extends BaseService<Cart> implements CartService {

    @Autowired
    GoodsApi goodsApi;

    @Autowired
    ProductApi productApi;
    @Autowired
    AddressApi addressApi;
    @Autowired
    RegionApi regionApi;

    @Autowired
    GoodsSpecificationApi goodsSpecificationApi;
    @Autowired
    UserCouponApi userCouponApi;

    @Override
    public CartResultVO getCart(String token) {

        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        List<Cart> cartList = this.queryList(new Cart().setUserId(user.getId()).setSessionId(claims.getId()));
        CartResultVO.CartTotalVO cartTotalVO = new CartResultVO.CartTotalVO();

        Integer goodsCount = 0;
        BigDecimal goodsAmount = BigDecimal.ZERO;
        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = BigDecimal.ZERO;
        if(cartList==null){
            cartTotalVO.setGoodsCount(goodsCount)
                .setGoodsAmount(goodsAmount)
                .setCheckedGoodsCount(checkedGoodsCount)
                .setCheckedGoodsAmount(checkedGoodsAmount);
            return new CartResultVO(cartList, cartTotalVO);
        }
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
            //goodsAmount = goodsAmount + retailPrice * number
            goodsAmount = goodsAmount.add(
                cart.getRetailPrice().multiply(new BigDecimal(cart.getNumber()))
            );
            if (cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                //checkedGoodsAmount = checkedGoodsAmount + retailPrice * number
                checkedGoodsAmount = checkedGoodsAmount.add(
                    cart.getRetailPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }

        cartTotalVO.setGoodsCount(goodsCount)
            .setGoodsAmount(goodsAmount)
            .setCheckedGoodsCount(checkedGoodsCount)
            .setCheckedGoodsAmount(checkedGoodsAmount);

        return new CartResultVO(cartList, cartTotalVO);
    }

    @Override
    @Transactional
    public void addGoodsToCart(CartParamVO cartParamDTO,String token) {
        //后续抽离出来放到线程变量中
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        Goods goods = goodsApi.queryById(cartParamDTO.getGoodsId()).getData();
        if (goods == null || goods.getDelete()) {
            //商品已下架
            throw new YxhshopException(YxhshopWechatResultStatus.GOODS_HAVE_BEEN_TAKEN_OFF_THE_SHELVES);
        }
        Product product = productApi.queryOne(new Product()
            .setGoodsId(cartParamDTO.getGoodsId())
            .setId(cartParamDTO.getProductId())
        ).getData();
        if (product == null || product.getGoodsNumber() < cartParamDTO.getNumber()) {
            //库存不足
            throw new YxhshopException(YxhshopWechatResultStatus.UNDER_STOCK);
        }
        Cart cart = this.queryOne(new Cart()
            .setGoodsId(cartParamDTO.getGoodsId())
            .setProductId(cartParamDTO.getProductId())
        );
        if (cart == null) {
            // 判断购物车中是否存在此规格商品
            List<String> goodsSpecificationValueList = new LinkedList<>();
            if (product.getGoodsSpecificationIds() != null) {
                List<Integer> specificationIdList = Arrays.stream(product.getGoodsSpecificationIds().split("_"))
                    .filter(id -> id.length() > 0)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
                if (!specificationIdList.isEmpty()) {
                    goodsSpecificationValueList = goodsSpecificationApi.queryValueByGoodsIdAndIdIn(cartParamDTO.getGoodsId(), specificationIdList).getData();
                }
            }
            Cart cartData = new Cart()
                .setGoodsId(cartParamDTO.getGoodsId())
                .setProductId(cartParamDTO.getProductId())
                .setGoodsSn(product.getGoodsSn())
                .setGoodsName(goods.getName())
                .setListPicUrl(goods.getListPicUrl())
                .setNumber(cartParamDTO.getNumber().shortValue())
                .setSessionId(claims.getId())
                .setUserId(user.getId())
                .setRetailPrice(product.getRetailPrice())
                .setMarketPrice(product.getRetailPrice())
                .setGoodsSpecificationNameValue(
                    goodsSpecificationValueList.stream()
                        .collect(Collectors.joining(";"))
                )
                .setGoodsSpecificationIds(product.getGoodsSpecificationIds())
                .setChecked(true);
            this.create(cartData);
        } else {
            // 如果已经存在购物车中，则数量增加
            if (product.getGoodsNumber() < (cartParamDTO.getNumber() + cart.getNumber())) {
                throw new YxhshopException(YxhshopWechatResultStatus.UNDER_STOCK);
            }
            this.updateNotNull(new Cart().setNumber(cartParamDTO.getNumber().shortValue()).setId(cart.getId()));
        }
    }

    @Override
    @Transactional
    public void updateGoods(CartParamVO cartParamDTO, String token) {
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        // 取得规格的信息,判断规格库存
        Product product = productApi.queryOne(new Product()
            .setGoodsId(cartParamDTO.getGoodsId())
            .setId(cartParamDTO.getProductId())
        ).getData();
        if (product == null || product.getGoodsNumber() < cartParamDTO.getNumber()) {
            //库存不足
            throw new YxhshopException(YxhshopWechatResultStatus.UNDER_STOCK);
        }
        // 判断是否已经存在product_id购物车商品
        Cart cart = this.queryById(cartParamDTO.getId());
        if (cart.getProductId().equals(cartParamDTO.getProductId())) {
            // 只是更新number
            this.updateNotNull(new Cart()
                .setNumber(cartParamDTO.getNumber().shortValue())
                .setId(cartParamDTO.getId())
            );
            return;
        }
        Cart newCartInfo = this.queryOne(
            new Cart()
                .setUserId(user.getId())
                .setSessionId(claims.getId())
                .setGoodsId(cartParamDTO.getGoodsId())
                .setProductId(cartParamDTO.getProductId())
        );
        if (newCartInfo == null) {
            //直接更新原来的cartInfo
            // 判断购物车中是否存在此规格商品
            List<String> goodsSpecificationValueList = new LinkedList<>();
            if (product.getGoodsSpecificationIds() != null) {
                List<Integer> specificationIdList = Arrays.stream(product.getGoodsSpecificationIds()
                    .split("_"))
                    .filter(id -> id.length() > 0)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
                if (!specificationIdList.isEmpty()) {
                    goodsSpecificationValueList = goodsSpecificationApi.queryValueByGoodsIdAndIdIn(cartParamDTO.getGoodsId(), specificationIdList).getData();
                }
            }
            Cart cartData = new Cart()
                .setId(cartParamDTO.getId())
                .setNumber(cartParamDTO.getNumber().shortValue())
                .setGoodsSpecificationNameValue(
                    goodsSpecificationValueList.stream()
                        .collect(Collectors.joining(";"))
                )
                .setGoodsSpecificationIds(product.getGoodsSpecificationIds())
                .setRetailPrice(product.getRetailPrice())
                .setMarketPrice(product.getRetailPrice())
                .setProductId(cartParamDTO.getProductId())
                .setGoodsSn(product.getGoodsSn());
            this.updateNotNull(cartData);
        } else {
            // 合并购物车已有的product信息，删除已有的数据
            Integer newNumber = cartParamDTO.getNumber() + newCartInfo.getNumber();
            if (product == null || product.getGoodsNumber() < newNumber) {
                //库存不足
                throw new YxhshopException(YxhshopWechatResultStatus.UNDER_STOCK);
            }
            this.deleteById(newCartInfo.getId());
            Cart cartData = new Cart()
                .setId(cartParamDTO.getId())
                .setNumber(newNumber.shortValue())
                .setGoodsSpecificationNameValue(newCartInfo.getGoodsSpecificationNameValue())
                .setGoodsSpecificationIds(newCartInfo.getGoodsSpecificationIds())
                .setRetailPrice(product.getRetailPrice())
                .setMarketPrice(product.getRetailPrice())
                .setProductId(cartParamDTO.getProductId())
                .setGoodsSn(product.getGoodsSn());
            this.updateNotNull(cartData);
        }

    }

    @Override
    public void deleteCartGoods(CartGoodsDeleteVO deleteVO) {
        List<Integer> productIds = Arrays.stream(deleteVO.getProductIds().split(",")).map(Integer::valueOf).collect(Collectors.toList());
        this.queryByCriteria(Criteria.of(Cart.class).fields(Cart::getId).andIn(Cart::getProductId, productIds)).stream()
            .map(Cart::getId)
            .forEach(cartId -> this.deleteById(cartId));
    }

    @Override
    public void checkedCartGoods(CartCheckedVO cartCheckedVO) {
        List<Integer> productIds = Arrays.stream(cartCheckedVO.getProductIds().split(",")).map(Integer::valueOf).collect(Collectors.toList());
        this.queryByCriteria(Criteria.of(Cart.class).fields(Cart::getId).andIn(Cart::getProductId, productIds)).stream()
            .map(Cart::getId)
            .forEach(cartId -> this.updateNotNull(new Cart().setChecked(cartCheckedVO.getChecked()).setId(cartId)));
    }

    @Override
    public CartCheckoutVO checkoutCart(Integer addressId, Integer couponId,String token) {
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        CartCheckoutVO cartCheckoutDTO = new CartCheckoutVO();
        //选择收货地址
        Address checkedAddress = null;
        if (addressId != null &&addressId!=0) {
            checkedAddress = addressApi.queryOne(new Address()
                .setId(addressId)
                .setUserId(user.getId())
            ).getData();
        } else {
            checkedAddress = addressApi.queryOne(new Address().setUserId(user.getId()).setIsDefault(true)).getData();
        }

        CartCheckoutVO.CheckedAddressVO checkedAddressVO = null;
        if (checkedAddress != null) {
            checkedAddressVO = new CartCheckoutVO.CheckedAddressVO(checkedAddress)
                .setProvinceName(
                    regionApi.queryNameById(checkedAddress.getProvinceId()).getData()
                )
                .setCityName(
                    regionApi.queryNameById(checkedAddress.getCityId()).getData()
                )
                .setDistrictName(
                    regionApi.queryNameById(checkedAddress.getDistrictId()).getData()
                );

            checkedAddressVO.setFullRegion(
                checkedAddressVO.getProvinceName() + checkedAddressVO.getCityName() + checkedAddressVO.getDistrictName()
            );
        }
        // 根据收货地址计算运费，未实现
        BigDecimal freightPrice = BigDecimal.ZERO;

        CartResultVO cartData = this.getCart(token);
        List<Cart> checkedGoodsList = cartData.getCartList().stream()
            .filter(Cart::getChecked)
            .collect(Collectors.toList());

        // 获取可用的优惠券信息
        List<UserCoupon> userCouponList = userCouponApi.queryList(new UserCoupon() {{
            setUserId(user.getId());
        }}).getData();
        BigDecimal couponPrice = BigDecimal.ZERO;

        //计算订单的费用

        //商品总价
        BigDecimal goodsTotalPrice = cartData.getCartTotal().getCheckedGoodsAmount();
        BigDecimal orderTotalPrice = cartData.getCartTotal().getCheckedGoodsAmount()
            .add(freightPrice)
            .subtract(couponPrice);

        //减去其它支付的金额后，要实际支付的金额
        BigDecimal actualPrice = orderTotalPrice
            .subtract(new BigDecimal(0.00));

        cartCheckoutDTO.setCheckedAddress(checkedAddressVO);
        cartCheckoutDTO.setFreightPrice(freightPrice);
        cartCheckoutDTO.setCheckedCoupon(null);
        cartCheckoutDTO.setCouponList(userCouponList);
        cartCheckoutDTO.setCouponPrice(couponPrice);
        cartCheckoutDTO.setCheckedGoodsList(checkedGoodsList);
        cartCheckoutDTO.setGoodsTotalPrice(goodsTotalPrice);
        cartCheckoutDTO.setOrderTotalPrice(orderTotalPrice);
        cartCheckoutDTO.setActualPrice(actualPrice);
        return cartCheckoutDTO;
    }
}
