package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.user.po.Region;

/**
 * @author zhs@outlook.com
 */
public interface RegionService extends IService<Region> {

    String queryNameById(Short id);

}
