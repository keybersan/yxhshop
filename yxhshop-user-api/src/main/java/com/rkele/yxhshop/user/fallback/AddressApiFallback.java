package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.AddressApi;
import com.rkele.yxhshop.user.po.Address;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class AddressApiFallback extends ApiFallback<Address> implements AddressApi {

}
