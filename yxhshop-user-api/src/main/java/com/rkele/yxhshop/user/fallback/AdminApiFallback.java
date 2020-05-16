package com.rkele.yxhshop.user.fallback;

import com.rkele.yxhshop.user.po.Admin;
import org.springframework.stereotype.Component;
import com.rkele.yxhshop.common.fallback.ApiFallback;
import com.rkele.yxhshop.user.api.AdminApi;

/**
 * Created by keybersan on 2020/2/27.
 */
@Component
public class AdminApiFallback extends ApiFallback<Admin> implements AdminApi{
}
