package com.didi.crowd.api;

import com.didi.crowd.ResultEntity;
import com.didi.crowd.entrty.PO.TCert;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.entrty.VO.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("didi-mysql-provider")
public interface MySqlRemoteService  {

    @RequestMapping("/get/project/detail/remote")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@RequestParam("projectId") Integer projectId);

    @RequestMapping("/get/portal/type/project/data/remote")
     ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote();

    @RequestMapping("provider/get/memberpo/by/loginacct/remote")
    public ResultEntity<TMember> getMemberByLoginacctRemote(@RequestParam("loginAcct")String loginAcct);

    @RequestMapping("provider/save/memberpo/remote")
    ResultEntity<String> authPOMemberRegister(@RequestBody TMember memberPO);

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId);

    @RequestMapping("/get/order/projectVO/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId);

    @RequestMapping("/get/addressvo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId);
    @RequestMapping("save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);

    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);

    @RequestMapping("query/cert/by/accttypt")
    ResultEntity<List<TCert>> queryCertsByAccttype(
            @RequestParam("accttype") Integer accttype);

    @RequestMapping("save/realname/remote")
    ResultEntity<String> saveRealnameRemote(@RequestBody RealNameVO realName);

    @RequestMapping("get/member/by/memberid/remote")
     ResultEntity<TMember> getMemberByMemberIdRemote(@RequestParam("memberId") Integer memberId);

    @RequestMapping("save/cert/pic")
    ResultEntity<String> saveQualificationCert(
            @RequestParam("memberId")Integer memberId,
            @RequestParam("certId")Integer certId,
            @RequestParam("quaPic")String quaPic);




    ;
}
