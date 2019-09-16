package org.tangdao.modules.sms.model.domain;

import java.util.Date;

import org.tangdao.common.suports.DataEntity;
import org.tangdao.modules.sms.config.SmsTemplateContext.IgnoreBlacklist;
import org.tangdao.modules.sms.config.SmsTemplateContext.IgnoreForbiddenWords;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息模板Entity
 * @author ruyang
 * @version 2019-09-06
 */
@Getter
@Setter
@TableName("sms_message_template")
public class SmsMessageTemplate extends DataEntity<SmsMessageTemplate> {
	
	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	
	private String userCode;		// 归属用户，如果为0，则表示为系统模板（使用所有人）
	private String content;		// 模板内容
	private Integer appType;		// 操作方式，1:融合WEB平台,2:开发者平台,3:运营支撑系统
	private Date approveTime;		// 审批时间
	private String approveUser;		// 审批账号
	private Integer noticeMode;		// 审核后短信通知类型,0:不需要通知，1：需要通知
	private String mobile;		// 审核后通知的手机号码
	private String regexValue;		// 匹配正则表达式，后台自动生成
	private Integer submitInterval;		// 短信提交时间间隔（同一号码）
	private Integer limitTimes;		// 短信每天提交次数上限（同一号码）
	private String whiteWord;		// 敏感词白名单 |符号隔开
	private Integer routeType;		// 模板路由类型
	private Integer priority;		// 优先级（越大越优先）
	private String extNumber;		// 扩展号
	 /**
     * 忽略手机黑名单拦截 add by 2018-05-02
     */
    private Integer           ignoreBlacklist      = IgnoreBlacklist.NO.getValue();

    /**
     * 忽略手机敏感词拦截 add by 2018-08-18
     */
    private Integer           ignoreForbiddenWords = IgnoreForbiddenWords.NO.getValue();
    
    // 路由类型名称
    @TableField(exist = false)
    private String            routeTypeText;
    // 平台类型名称
    @TableField(exist = false)
    private String            apptypeText;
	
	public SmsMessageTemplate() {
		super();
	}

	public SmsMessageTemplate(String id){
		super(id);
	}
}