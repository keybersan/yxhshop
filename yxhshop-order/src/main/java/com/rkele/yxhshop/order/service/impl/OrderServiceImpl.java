package com.rkele.yxhshop.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rkele.yxhshop.common.enums.YxhshopWechatResultStatus;
import com.rkele.yxhshop.common.exception.YxhshopException;
import com.rkele.yxhshop.common.utils.Criteria;
import com.rkele.yxhshop.common.utils.IdGenerator;
import com.rkele.yxhshop.common.utils.JwtHelper;
import com.rkele.yxhshop.order.api.CartApi;
import com.rkele.yxhshop.order.enums.OrderStatusEnum;
import com.rkele.yxhshop.order.enums.PayStatusEnum;
import com.rkele.yxhshop.order.mapper.OrderMapper;
import com.rkele.yxhshop.order.po.Cart;
import com.rkele.yxhshop.order.po.Order;
import com.rkele.yxhshop.order.po.OrderExpress;
import com.rkele.yxhshop.order.po.OrderGoods;
import com.rkele.yxhshop.order.query.OrderQuery;
import com.rkele.yxhshop.order.service.CartService;
import com.rkele.yxhshop.order.service.OrderExpressService;
import com.rkele.yxhshop.order.service.OrderGoodsService;
import com.rkele.yxhshop.order.service.OrderService;
import com.rkele.yxhshop.order.vo.*;
import com.rkele.yxhshop.user.api.AddressApi;
import com.rkele.yxhshop.user.api.RegionApi;
import com.rkele.yxhshop.user.po.Address;
import com.rkele.yxhshop.user.po.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rkele.yxhshop.common.service.BaseService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhs@outlook.com
 */
@Service
public class OrderServiceImpl extends BaseService<Order> implements OrderService {
    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    OrderExpressService orderExpressService;
    @Autowired
    RegionApi regionApi;
    @Autowired
    AddressApi addressApi;
    @Autowired
    CartService cartService;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<OrderListVO> queryOrderList(OrderQuery orderQuery, String token) {
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        List<Order> orderList = this.queryByCriteria(Criteria.of(Order.class).andEqualTo(Order::getUserId, user.getId()).page(orderQuery.getPageNum(), orderQuery.getPageSize()));
        List<OrderListVO> orderVOList = new LinkedList<>();
        for (Order order : orderList) {
            OrderListVO orderVO = new OrderListVO(order)
                .setGoodsList(orderGoodsService.queryList(new OrderGoods().setOrderId(order.getId())))
                .setHandleOption(new HandleOptionVO(order))
                .setOrderStatusText(order.getPayStatus().getName());
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public OrderDetailVO queryOrderDetail(Integer orderId) {
        Order order = Optional.ofNullable(this.queryById(orderId))
            .orElseThrow(() -> new YxhshopException(YxhshopWechatResultStatus.ORDER_NOT_EXIST));

        OrderDetailVO.OrderInfoVO orderInfoVO = new OrderDetailVO.OrderInfoVO(order)
            .setOrderExpress(orderExpressService.queryOne(new OrderExpress().setOrderId(orderId)));

        orderInfoVO.setProvinceName(
            regionApi.queryNameById(orderInfoVO.getProvince()).getData()
        ).setCityName(
            regionApi.queryNameById(orderInfoVO.getCity()).getData()
        ).setDistrictName(
            regionApi.queryNameById(orderInfoVO.getDistrict()).getData()
        );
        orderInfoVO.setFullRegion(
            orderInfoVO.getProvinceName() + orderInfoVO.getCityName() + orderInfoVO.getDistrictName()
        );

        List<OrderGoods> orderGoodsList = orderGoodsService.queryList(new OrderGoods().setOrderId(orderId));

        return new OrderDetailVO(orderInfoVO, orderGoodsList, new HandleOptionVO(order));
    }

    @Override
    public OrderSubmitResultVO submitOrder(OrderSubmitParamVO orderSubmitParamDTO, String token) {
        Claims claims = JwtHelper.parseJWT(token);
        User user  = JSONObject.parseObject(claims.getSubject(),User.class);
        Address checkedAddress = addressApi.queryById(orderSubmitParamDTO.getAddressId()).getData();
        if (checkedAddress == null) {
            throw new YxhshopException(YxhshopWechatResultStatus.PLEASE_SELECT_SHIPPING_ADDRESS);
        }

        //获取要购买的商品
        List<Cart> checkedGoodsList = cartService.queryList(
            new Cart()
                .setUserId(user.getId())
                .setSessionId(claims.getId())
                .setChecked(true)
        );
        if (checkedGoodsList.isEmpty()) {
            throw new YxhshopException(YxhshopWechatResultStatus.PLEASE_SELECT_GOODS);
        }

        //统计商品总价
        BigDecimal goodsTotalPrice = BigDecimal.ZERO;
        for (Cart cart : checkedGoodsList) {
            goodsTotalPrice = goodsTotalPrice.add(
                cart.getRetailPrice().multiply(new BigDecimal(cart.getNumber()))
            );
        }

        //运费价格
        BigDecimal freightPrice = BigDecimal.ZERO;

        //获取订单使用的优惠券
        BigDecimal couponPrice = BigDecimal.ZERO;
        if (orderSubmitParamDTO.getCouponId() != null) {
            //计算优惠券的价格 未实现
        }

        // 订单价格计算  实际价格 = 商品价格 + 运费价格 - 优惠券价格
        BigDecimal orderTotalPrice = goodsTotalPrice.add(freightPrice).subtract(couponPrice);
        // 减去其它支付的金额后，要实际支付的金额
        BigDecimal actualPrice = orderTotalPrice.subtract(new BigDecimal(0.00));
        Date currentTime = new Date();

        Order orderInfo = new Order();
        //数据量大时可以使用雪花算法获取唯一id
        orderInfo.setOrderSN(IdGenerator.INSTANCE.nextId());
        orderInfo.setUserId(user.getId());

        //收货地址和运费
        orderInfo.setConsignee(checkedAddress.getName());
        orderInfo.setMobile(checkedAddress.getMobile());
        orderInfo.setProvince(checkedAddress.getProvinceId());
        orderInfo.setCity(checkedAddress.getCityId());
        orderInfo.setDistrict(checkedAddress.getDistrictId());
        orderInfo.setAddress(checkedAddress.getAddress());
        orderInfo.setFreightPrice(new BigDecimal(0.00));

        //留言
        orderInfo.setPostscript(orderSubmitParamDTO.getPostscript());

        //使用优惠券
        orderInfo.setCouponId(0);
        orderInfo.setCouponPrice(couponPrice);
        orderInfo.setCreateTime(currentTime);
        orderInfo.setGoodsPrice(goodsTotalPrice);
        orderInfo.setOrderPrice(orderTotalPrice);
        orderInfo.setActualPrice(actualPrice);

        //订单状态：提交订单
        orderInfo.setOrderStatus(OrderStatusEnum.SUBMIT_ORDER);
        //支付状态：待付款
        orderInfo.setPayStatus(PayStatusEnum.PENDING_PAYMENT);

        orderMapper.insertOrder(orderInfo);





        //统计商品总价
        List<OrderGoods> orderGoodsList = new LinkedList<>();
        for (Cart goodsItem : checkedGoodsList) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(orderInfo.getId());
            orderGoods.setGoodsId(goodsItem.getGoodsId());
            orderGoods.setGoodsSn(goodsItem.getGoodsSn());
            orderGoods.setProductId(goodsItem.getProductId());
            orderGoods.setGoodsName(goodsItem.getGoodsName());
            orderGoods.setListPicUrl(goodsItem.getListPicUrl());
            orderGoods.setMarketPrice(goodsItem.getMarketPrice());
            orderGoods.setRetailPrice(goodsItem.getRetailPrice());
            orderGoods.setNumber(goodsItem.getNumber());
            orderGoods.setGoodsSpecificationNameValue(goodsItem.getGoodsSpecificationNameValue());
            orderGoods.setGoodsSpecificationIds(goodsItem.getGoodsSpecificationIds());
            orderGoods.setReal(true);
            orderGoodsList.add(orderGoods);
        }
        orderGoodsService.createBatch(orderGoodsList);

//        清空购物车已购买商品
        cartService.delete(new Cart()
            .setUserId(user.getId())
            .setSessionId(claims.getId())
            .setChecked(true)
        );

        return new OrderSubmitResultVO(orderInfo);
    }
}
