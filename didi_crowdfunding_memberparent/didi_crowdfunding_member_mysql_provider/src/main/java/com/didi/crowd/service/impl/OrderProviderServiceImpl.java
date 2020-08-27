package com.didi.crowd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.didi.crowd.entrty.PO.TAddress;
import com.didi.crowd.entrty.PO.TOrder;
import com.didi.crowd.entrty.PO.TOrderProject;
import com.didi.crowd.entrty.VO.AddressVO;
import com.didi.crowd.entrty.VO.OrderProjectVO;
import com.didi.crowd.entrty.VO.OrderVO;
import com.didi.crowd.mapper.TAddressMapper;
import com.didi.crowd.mapper.TOrderMapper;
import com.didi.crowd.mapper.TOrderProjectMapper;
import com.didi.crowd.service.IOrderProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderProviderServiceImpl implements IOrderProviderService {
    @Autowired
    private TOrderProjectMapper orderProjectMapper;
    @Autowired
    private TAddressMapper addressMapper;
    @Autowired
    private TOrderMapper orderMapper;

    private Logger logger = LoggerFactory.getLogger(OrderProviderServiceImpl.class);
    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {
        return orderProjectMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressVORemote(Integer memberId) {
        QueryWrapper<TAddress> wrapper = new QueryWrapper<>();
        wrapper.eq(true,"member_id",memberId);
        List<TAddress> addressList = addressMapper.selectList(wrapper);
        List<AddressVO> addressVOList = new ArrayList<>();
        if (addressList!=null){
            for (TAddress addressPO:addressList) {
                AddressVO addressVO = new AddressVO();
                BeanUtils.copyProperties(addressPO,addressVO);
                addressVOList.add(addressVO);
            }
        }
        return addressVOList;
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveAddressRemote(AddressVO addressVO) {
        logger.info("addressVO"+addressVO.toString());
        TAddress addressPO =new TAddress();
        BeanUtils.copyProperties(addressVO,addressPO);
        addressMapper.insert(addressPO);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveOrderRemote(OrderVO orderVO) {
        TOrder orderPO = new TOrder();
        TOrderProject orderProjectPO = new TOrderProject();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(),orderProjectPO);
        BeanUtils.copyProperties(orderVO,orderPO);
        //保存订单信息
        logger.info("orderVO"+orderVO.toString());
        orderMapper.insert(orderPO);

        //保存订单详细信息
        Integer orderPOId = orderPO.getId();
        orderProjectPO.setOrderId(orderPOId);
        logger.info("orderProjectPO"+orderProjectPO.toString());
        orderProjectMapper.insert(orderProjectPO);
        //保存支付人与订单
        Integer memberId = orderVO.getMemberId();
        orderMapper.saveOderMember(orderPOId,memberId);
    }
}
