package com.rkele.yxhshop.user.service;

import com.rkele.yxhshop.common.service.IService;
import com.rkele.yxhshop.user.po.Address;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.vo.AddressVO;

import java.util.List;

/**
 * @author zhs@outlook.com
 */
public interface AddressService extends IService<Address> {

    List<AddressVO> queryDetailList(User user);
    AddressVO queryDetail(Integer id);
}
