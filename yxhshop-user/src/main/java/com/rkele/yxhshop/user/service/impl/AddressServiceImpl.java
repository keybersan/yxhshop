package com.rkele.yxhshop.user.service.impl;

import com.rkele.yxhshop.user.po.Address;
import com.rkele.yxhshop.user.po.User;
import com.rkele.yxhshop.user.service.AddressService;
import com.rkele.yxhshop.user.service.RegionService;
import com.rkele.yxhshop.user.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.rkele.yxhshop.common.service.BaseService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhs@outlook.com
 */
@RestController
public class AddressServiceImpl extends BaseService<Address> implements AddressService {
    @Autowired
    RegionService regionService;

    @Override
    public List<AddressVO> queryDetailList(User user) {
        List<Address> addressList = this.queryList(new Address().setUserId(user.getId()));
        LinkedList<AddressVO> addressDTOList = new LinkedList<>();
        for (Address address : addressList) {
            AddressVO addressDTO = new AddressVO(address)
                .setProvinceName(
                    regionService.queryNameById(address.getProvinceId())
                )
                .setCityName(
                    regionService.queryNameById(address.getCityId())
                )
                .setDistrictName(
                    regionService.queryNameById(address.getDistrictId())
                );
            addressDTO.setFullRegion(
                addressDTO.getProvinceName() + addressDTO.getCityName() + addressDTO.getDistrictName()
            );
            addressDTOList.add(addressDTO);
        }
        return addressDTOList;
    }

    @Override
    public AddressVO queryDetail(Integer id) {
        Address address = this.queryById(id);
        AddressVO addressDTO = null;
        if (address != null) {
            addressDTO = new AddressVO(address)
                .setProvinceName(
                    regionService.queryNameById(address.getProvinceId())
                )
                .setCityName(
                    regionService.queryNameById(address.getCityId())
                )
                .setDistrictName(
                    regionService.queryNameById(address.getDistrictId())
                );

            addressDTO.setFullRegion(
                addressDTO.getProvinceName() + addressDTO.getCityName() + addressDTO.getDistrictName()
            );
        }
        return addressDTO;
    }
}
