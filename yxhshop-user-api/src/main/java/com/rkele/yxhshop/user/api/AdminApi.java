package com.rkele.yxhshop.user.api;

import com.rkele.yxhshop.user.fallback.AdminApiFallback;
import com.rkele.yxhshop.user.po.Admin;
import org.springframework.cloud.openfeign.FeignClient;
import com.rkele.yxhshop.common.api.Api;

/**
 * Created by keybersan on 2020/2/27.
 */
@FeignClient(value = "yxhshop-user", path = "admin", fallback = AdminApiFallback.class)
public interface AdminApi extends Api<Admin> {
}
