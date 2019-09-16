package org.tangdao.modules.developer.validator.sms;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tangdao.common.utils.MobileGroupUtils;
import org.tangdao.modules.developer.exception.ValidateException;
import org.tangdao.modules.developer.request.AuthorizationRequest;
import org.tangdao.modules.developer.request.sms.SmsSendRequest;
import org.tangdao.modules.developer.validator.AuthorizationValidator;
import org.tangdao.modules.developer.validator.Validator;
import org.tangdao.modules.exchanger.config.CommonContext.PlatformType;
import org.tangdao.modules.exchanger.config.OpenApiCode.CommonApiCode;
import org.tangdao.modules.paas.service.IUserBalanceService;
import org.tangdao.modules.sms.config.UserBalanceConstant;

@Component
public class SmsValidator extends Validator {

    @Autowired
    private AuthorizationValidator passportValidator;
    
    @Autowired
    private IUserBalanceService    userBalanceService;

    /**
     * TODO 用户参数完整性校验
     * 
     * @param paramMap
     * @param ip
     * @return
     * @throws ValidateException
     */
    public SmsSendRequest validate(Map<String, String[]> paramMap, String ip) throws ValidateException {
        SmsSendRequest smsSendRequest = new SmsSendRequest();
        validateAndParseFields(smsSendRequest, paramMap);

        // 获取授权通行证实体
        AuthorizationRequest passportModel = passportValidator.validate(paramMap, ip, smsSendRequest.getMobile());

        smsSendRequest.setIp(ip);
        smsSendRequest.setUserCode(passportModel.getUserCode());

        // 校验用户短信余额是否满足
        checkBalanceAvaiable(smsSendRequest, passportModel);

        return smsSendRequest;
    }

    /**
     * TODO 验证签名（携带手机号码签名模式）
     * 
     * @param smsSendRequest
     * @param passportModel
     * @return
     * @throws ValidateException
     */
    private void checkBalanceAvaiable(SmsSendRequest smsSendRequest, AuthorizationRequest passportModel)
                                                                                                        throws ValidateException {
        // 获取本次短信内容计费条数
        int fee = userBalanceService.calculateSmsAmount(passportModel.getUserCode(), smsSendRequest.getContent());
        if (UserBalanceConstant.CONTENT_WORDS_EXCEPTION_COUNT_FEE == fee) {
            throw new ValidateException(CommonApiCode.COMMON_BALANCE_EXCEPTION);
        }
        
        // 总手机号码数量
        int mobiles = smsSendRequest.getMobile().split(MobileGroupUtils.DATA_SPLIT_CHARCATOR).length;
        
        // 暂时先不加
//        isBeyondMobileSize(mobiles);
        
        // 计费总条数
        int totalFee = fee * mobiles;

        // 此处需加入是否为后付款，如果为后付则不需判断余额
        // f.用户余额不足（通过计费微服务判断，结合4.1.6中的用户计费规则）
        boolean balanceEnough = userBalanceService.isBalanceEnough(passportModel.getUserCode(),
                                                                   PlatformType.SEND_MESSAGE_SERVICE, (double) totalFee);
        if (!balanceEnough) {
            throw new ValidateException(CommonApiCode.COMMON_BALANCE_NOT_ENOUGH);
        }

        smsSendRequest.setFee(fee);
        smsSendRequest.setTotalFee(totalFee);
    }

}