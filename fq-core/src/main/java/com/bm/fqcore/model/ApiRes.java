/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bm.fqcore.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bm.fqcore.constants.ApiCodeEnum;
import com.bm.fqcore.utils.JeepayKit;
import com.bm.fqcore.utils.JsonKit;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
* 接口返回对象
*
* @author terrfly
* @site https://www.jeequan.com
* @date 2021/6/8 16:35
*/
@Data
@AllArgsConstructor
@ApiModel(value = "ReQueryResult对象", description = "公共返回对象ReQueryResult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRes<T> {

    /** 业务响应码 **/
    @ApiModelProperty(value = "业务响应码", required = true)
    private Integer code;

    /** 业务响应信息 **/
    @ApiModelProperty(value = "业务响应信息", required = true)
    private String msg;

    /** 数据对象 **/
    @ApiModelProperty(value = "数据对象", required = true)
    private T data;

    /** 签名值 **/
    @ApiModelProperty(value = "签名值", required = true)
    private String sign;

    /** 输出json格式字符串 **/
    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    /** 业务处理成功 **/
    public static ApiRes ok(){
        return ok(null);
    }

    /** 业务处理成功 **/
    public static ApiRes ok(Object data){
        return new ApiRes(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data, null);
    }

    /** 业务处理成功, 自动签名 **/
    public static ApiRes okWithSign(Object data, String mchKey){

        if(data == null){
            return new ApiRes(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), null, null);
        }

        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(data);
        String sign = JeepayKit.getSign(jsonObject, mchKey);
        return new ApiRes(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data, sign);
    }

    /** 业务处理成功, 返回简单json格式 **/
    public static ApiRes ok4newJson(String key, Object val){
        return ok(JsonKit.newJson(key, val));
    }

    /** 业务处理成功， 封装分页数据， 仅返回必要参数 **/
    public static ApiRes page(IPage iPage){

        JSONObject result = new JSONObject();
        result.put("records", iPage.getRecords());  //记录明细
        result.put("total", iPage.getTotal());  //总条数
        result.put("current", iPage.getCurrent());  //当前页码
        result.put("hasNext", iPage.getPages() > iPage.getCurrent() );  //是否有下一页
        return ok(result);
    }

    /** 业务处理失败 **/
    public static ApiRes fail(ApiCodeEnum apiCodeEnum, String... params){

        if(params == null || params.length <= 0){
            return new ApiRes(apiCodeEnum.getCode(), apiCodeEnum.getMsg(), null, null);
        }
        return new ApiRes(apiCodeEnum.getCode(), String.format(apiCodeEnum.getMsg(), params), null, null);
    }

    /** 自定义错误信息, 原封不用的返回输入的错误信息 **/
    public static ApiRes customFail(String customMsg){
        return new ApiRes(ApiCodeEnum.CUSTOM_FAIL.getCode(), customMsg, null, null);
    }

}
