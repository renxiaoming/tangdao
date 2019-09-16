package org.tangdao.modules.exchanger.resolver.sms.http;

import java.util.List;

import org.tangdao.modules.exchanger.model.response.ProviderSendResponse;
import org.tangdao.modules.exchanger.model.vo.TParameter;
import org.tangdao.modules.sms.model.domain.SmsMoMessageReceive;
import org.tangdao.modules.sms.model.domain.SmsMtMessageDeliver;
import org.tangdao.modules.sms.model.domain.SmsPassageParameter;

public interface SmsHttpPassageResolver {

    /**
     * TODO 发送短信（提交至通道商）
     *
     * @param parameter 通道参数
     * @param mobile 手机号码
     * @param content 短信内容
     * @param extNumber 用户扩展号码
     * @return
     */
    List<ProviderSendResponse> send(SmsPassageParameter parameter, String mobile, String content, String extNumber);

    /**
     * TODO 下行状态报告回执(推送)
     *
     * @param report
     * @return
     */
    List<SmsMtMessageDeliver> mtDeliver(String report, String successCode);

    /**
     * TODO 下行状态报告回执（自取）
     *
     * @param tparameter
     * @param url
     * @param successCode
     * @return
     */
    List<SmsMtMessageDeliver> mtDeliver(TParameter tparameter, String url, String successCode);

    /**
     * TODO 上行短信状态回执
     *
     * @param report
     * @return
     */
    List<SmsMoMessageReceive> moReceive(String report, String passageId);

    /**
     * TODO 上行短信状态回执
     *
     * @param tparameter
     * @param url
     * @param passageId
     * @return
     */
    List<SmsMoMessageReceive> moReceive(TParameter tparameter, String url, String passageId);

    /**
     * TODO 用户余额查询
     *
     * @param param
     * @return
     */
    Double balance(TParameter tparameter, String url, String passageId);
}