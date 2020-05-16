package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.api.AdApi;
import com.rkele.yxhshop.user.po.Ad;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;

@Component
public class AdApiFallback extends ApiFallback<Ad> implements AdApi {
}
