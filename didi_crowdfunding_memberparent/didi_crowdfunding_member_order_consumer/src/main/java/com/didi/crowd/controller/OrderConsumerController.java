package com.didi.crowd.controller;

import com.didi.crowd.CrowdConstant;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.api.MySqlRemoteService;
import com.didi.crowd.entrty.VO.AddressVO;
import com.didi.crowd.entrty.VO.LoginMemberVO;
import com.didi.crowd.entrty.VO.OrderProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class OrderConsumerController {
    @Autowired
    private MySqlRemoteService mySqlRemoteService;
    private Logger logger = LoggerFactory.getLogger(OrderConsumerController.class);
    @RequestMapping("/confirm/return/info")
    public String confirmReturnInfo(@RequestParam("returnId") Integer returnId,
                                    HttpSession session){
        ResultEntity<OrderProjectVO> resultEntity = mySqlRemoteService.getOrderProjectVORemote(returnId);
        String result = resultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
            OrderProjectVO orderProjectVO = resultEntity.getData();
            logger.info("orderProjectVO"+orderProjectVO.toString());
            //存入session
            session.setAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECT,orderProjectVO);
            return "confirm_return";
        }
        return "confirm_return";
    }
    @RequestMapping("/confirm/order")
    public String confirmOrder(@RequestParam("returnCount")String returnCount,
                               HttpSession session){
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECT);
        orderProjectVO.setReturnContent(returnCount);
        session.setAttribute(CrowdConstant.ATTR_NAME_ORDER_PROJECT,orderProjectVO);
        LoginMemberVO loginMember = (LoginMemberVO) session.getAttribute(CrowdConstant.MEMBER);
        Integer memberId = loginMember.getId();
       ResultEntity<List<AddressVO>> addressVORemote=mySqlRemoteService.getAddressVORemote(memberId);
        String result = addressVORemote.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
            List<AddressVO> data = addressVORemote.getData();
            session.setAttribute(CrowdConstant.ATTR_NAME_MEMBER_ADDRESS_LIST,data);
            return "confirm_order";
        }
        return "confirm_order";
    }

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO,
                                            HttpSession session){
        logger.info("addressVO"+addressVO.toString());
       session.setAttribute("address",addressVO);
        Integer memberId = addressVO.getMemberId();
        addressVO.setMemberId(memberId);
        ResultEntity<String> resultEntity = mySqlRemoteService.saveAddressRemote(addressVO);
        String result = resultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)){
            return "confirm_order";
        }
        ResultEntity<List<AddressVO>> addressVORemote = mySqlRemoteService.getAddressVORemote(memberId);
        if (addressVORemote.getResult().equals(ResultEntity.FAILED)){
            return "confirm_order";
        }
        List<AddressVO> data = addressVORemote.getData();
        session.setAttribute(CrowdConstant.ATTR_NAME_MEMBER_ADDRESS_LIST,data);
        return "confirm_order";
    }
}
