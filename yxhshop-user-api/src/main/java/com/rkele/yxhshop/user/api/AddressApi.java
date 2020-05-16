package com.rkele.yxhshop.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;
import com.rkele.yxhshop.user.fallback.AddressApiFallback;
import com.rkele.yxhshop.user.po.Address;

@FeignClient(value = "yxhshop-user", path = "address", fallback = AddressApiFallback.class)
public interface AddressApi extends Api<Address> {

}
