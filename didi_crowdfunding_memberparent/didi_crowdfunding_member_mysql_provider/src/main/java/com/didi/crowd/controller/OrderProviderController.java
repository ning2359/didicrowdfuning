package com.didi.crowd.controller;

import com.didi.crowd.ResultEntity;
import com.didi.crowd.entrty.VO.AddressVO;
import com.didi.crowd.entrty.VO.OrderProjectVO;
import com.didi.crowd.entrty.VO.OrderVO;
import com.didi.crowd.service.impl.OrderProviderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderProviderController {
    @Autowired
    private OrderProviderServiceImpl orderProviderService;
    private Logger logger = LoggerFactory.getLogger(OrderComparator.class);

    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO){
        try {
            orderProviderService.saveOrderRemote(orderVO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("生成订单错误"+e.getMessage());
        }
    }

    @RequestMapping("/get/order/projectVO/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId){
        try {
            OrderProjectVO orderProjectVO= orderProviderService.getOrderProjectVO(returnId);
            return ResultEntity.successWithData(orderProjectVO);
        }catch (Exception e){
            return ResultEntity.failed("生成订单错误，请再试一次  错误信息"+e.getMessage());
        }
    }
    @RequestMapping("/get/addressvo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId){
        try {
            List<AddressVO> addressVOList = orderProviderService.getAddressVORemote(memberId);
            return ResultEntity.successWithData(addressVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("查询地址错误，错误信息"+e.getMessage());
        }
    }
    @RequestMapping("save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO){
        try {
            orderProviderService.saveAddressRemote(addressVO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            return ResultEntity.failed("保存失败 错误信息"+e.getMessage());
        }
    }

}
