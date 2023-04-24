package com.bm.fquser.crtl;


import com.bm.fqcore.comment.PageWrap;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.model.security.AppUserDetails;
import com.bm.fqcore.model.security.JeeUserDetails;
import com.bm.fquser.model.dto.*;
import com.bm.fquser.model.vo.ProductVO;
import com.bm.fquser.service.MobileMallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Api(tags = "首页相关接口")
@RestController
@RequestMapping("/takeoutVisit")
@Slf4j
public class MallController extends CommonCtrl {

    @Resource
    MobileMallService mallService;

    @ApiOperation("商城主页")
    @PostMapping("/home")
    public ApiRes<HomeDTO> home(@RequestBody PageWrap<String> pageWrap) {
        return mallService.home(pageWrap);
    }

    @ApiOperation("商品详情")
    @GetMapping("/details")
    public ApiRes<ProductVO> details(@RequestParam(value = "prodId") String prodId) {
        return mallService.details(prodId);
    }



}
