package com.didi.crowd.service;

import com.didi.crowd.entrty.VO.AddressVO;
import com.didi.crowd.entrty.VO.OrderProjectVO;
import com.didi.crowd.entrty.VO.OrderVO;

import java.util.List;

public interface IOrderProviderService {
    OrderProjectVO getOrderProjectVO(Integer projectId);

    List<AddressVO> getAddressVORemote(Integer memberId);

    void saveAddressRemote(AddressVO addressVO);

    void saveOrderRemote(OrderVO orderVO);
}
