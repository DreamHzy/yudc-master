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
package com.bm.fqmerchant.web;

import com.bm.fqcore.utils.ApiResBodyAdviceKit;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 自定义springMVC返回数据格式
 *
 * @author terrfly
 * @modify zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@ControllerAdvice
public class ApiResBodyAdvice implements ResponseBodyAdvice {

    /**
     * 需要忽略的地址
     */
    private static String[] ignores = new String[]{
            //过滤swagger相关的请求的接口，不然swagger会提示base-url被拦截
            "/swagger-resources",
            "/v2/api-docs"
    };

    /**
     * 判断url是否需要拦截
     * @param uri
     * @return
     */
    private boolean ignoring(String uri) {
        for (String string : ignores) {
            if (uri.contains(string)) {
                return true;
            }
        }
        return false;
    }


    /** 判断哪些需要拦截 **/
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /** 拦截返回数据处理 */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        //判断url是否需要拦截
        if (this.ignoring(request.getURI().toString())) {
            return body;
        }

        //处理扩展字段
        return ApiResBodyAdviceKit.beforeBodyWrite(body);
    }

}
