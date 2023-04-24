package com.bm.fquser.crtl;

import com.bm.fqcore.comment.PageWrap;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqservice.model.BArea;
import com.bm.fqservice.service.IBAreaService;
import com.bm.fquser.model.dto.AddressDTO;
import com.bm.fquser.model.dto.MaintainDTO;
import com.bm.fquser.model.dto.QueryShippingDTO;
import com.bm.fquser.service.MobileMallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.geom.Area;
import java.util.List;

@Api(tags = "收货地址相关接口")
@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController extends CommonCtrl {

    @Resource
    MobileMallService mallService;
    @Resource
    IBAreaService ibAreaService;

    @ApiOperation("收货地址")
    @PostMapping("/shippingAddress")
    public ApiRes<AddressDTO> shippingAddress(@RequestBody PageWrap<String> pageWrap) {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return mallService.shippingAddress(pageWrap, jeeUserDetails);
    }

    @ApiOperation("查询收货地址")
    @GetMapping("/queryShippingAddress")
    public ApiRes<QueryShippingDTO> queryShippingAddress(@RequestParam(value = "addrId") Integer addrId) {
        return mallService.queryShippingAddress(addrId);
    }

    @ApiOperation("维护收货地址")
    @PostMapping("/maintainShippingAddress")
    public ApiRes maintainShippingAddress(@RequestBody MaintainDTO maintainDTO) {
        AppUserDetails jeeUserDetails = getCurrentUser();
        return mallService.maintainShippingAddress(maintainDTO, jeeUserDetails);
    }

    @ApiOperation("删除收货地址")
    @GetMapping("/deleteAddress")
    public ApiRes deleteAddress(@RequestParam(value = "addrId") long addrId) {
        return mallService.deleteAddress(addrId);
    }

    /**
     * 分页获取
     */
    @GetMapping("/listByPid")
    @ApiOperation(value = "获取省市区信息", notes = "根据省市区的pid获取地址信息")
    @ApiImplicitParam(name = "pid", value = "省市区的pid(pid为0获取所有省份)", required = true, dataType = "String")
    public ApiRes<List<BArea>> listByPid(Long pid) {
        List<BArea> list = ibAreaService.list(BArea.gw().eq(BArea::getParentId, pid));
        return ApiRes.ok(list);
    }
}
