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
package com.bm.fqcomponentsoss.ctrl;

import cn.hutool.core.lang.UUID;
import com.bm.fqcomponentsoss.config.AliyunOssYmlConfig;
import com.bm.fqcomponentsoss.model.OssFile;
import com.bm.fqcomponentsoss.model.OssFileConfig;
import com.bm.fqcomponentsoss.service.IOssService;
import com.bm.fqcore.constants.ApiCodeEnum;
import com.bm.fqcore.ctrls.AbstractCtrl;
import com.bm.fqcore.exception.BizException;
import com.bm.fqcore.model.ApiRes;
import com.bm.fqcore.utils.FileKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
 * 统一文件上传接口（ossFile）
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/8 17:07
 */
@RestController
@RequestMapping("/api/ossFiles")
public class OssFileController extends AbstractCtrl {

    @Autowired
    private IOssService ossService;
    @Autowired
    private AliyunOssYmlConfig aliyunOssYmlConfig;

    /**
     * 上传文件 （单文件上传）
     */
    @PostMapping("/file")
    public ApiRes singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("bizType") String bizType) {

        if (file == null) {
            return ApiRes.fail(ApiCodeEnum.SYSTEM_ERROR, "选择文件不存在");
        }
        try {

            OssFileConfig ossFileConfig = OssFileConfig.getOssFileConfigByBizType(bizType);

            //1. 判断bizType 是否可用
            if (ossFileConfig == null) {
                throw new BizException("类型有误");
            }

            // 2. 判断文件是否支持
            String fileSuffix = FileKit.getFileSuffix(file.getOriginalFilename(), false);
            if (!ossFileConfig.isAllowFileSuffix(fileSuffix)) {
                throw new BizException("上传文件格式不支持！");
            }

            // 3. 判断文件大小是否超限
            if (!ossFileConfig.isMaxSizeLimit(file.getSize())) {
                throw new BizException("上传大小请限制在[" + ossFileConfig.getMaxSize() / 1024 / 1024 + "M]以内！");
            }

            // 新文件地址 (xxx/xxx.jpg 格式)
            String saveDirAndFileName = bizType + "/" + UUID.fastUUID() + "." + fileSuffix;
            String url = ossService.upload2PreviewUrl(ossFileConfig.getOssSavePlaceEnum(), file, saveDirAndFileName);
            OssFile ossFile = new OssFile();
            ossFile.setFileUrl(url);
            ossFile.setName(file.getName());
            ossFile.setOssUrl(aliyunOssYmlConfig.getUrl());
            return ApiRes.ok(ossFile);

        } catch (BizException biz) {
            throw biz;
        } catch (Exception e) {
            logger.error("upload error, fileName = {}", file.getOriginalFilename(), e);
            throw new BizException(ApiCodeEnum.SYSTEM_ERROR);
        }
    }

}
