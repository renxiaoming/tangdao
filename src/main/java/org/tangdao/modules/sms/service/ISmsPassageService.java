package org.tangdao.modules.sms.service;

import java.util.List;
import java.util.Map;

import org.tangdao.common.service.ICrudService;
import org.tangdao.modules.sms.model.domain.SmsPassage;
import org.tangdao.modules.sms.model.domain.SmsPassageProvince;

/**
 * 通道管理Service
 * @author ruyang
 * @version 2019-09-06
 */
public interface ISmsPassageService extends ICrudService<SmsPassage> {
	
	/**
	 * 添加通道
	 * 
	 * @param passage
	 * @param provinceCodes
	 * @return
	 */
	Map<String, Object> create(SmsPassage passage, String provinceCodes);

	/**
	 * 修改通道
	 * 
	 * @param passage
	 * @param provinceCodes
	 * @return
	 */
	Map<String, Object> update(SmsPassage passage, String provinceCodes);

	/**
	 * 删除通道
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteById(String id);

	/**
	 * 
	   * TODO 根据通道ID获取通道详细信息
	   * 
	   * @param id
	   * @return
	 */
	SmsPassage findById(String id);

	/**
	 * TODO 根据通道组ID获取所有通道信息
	 *
	 * @param groupId
	 * @return
	 */
	List<SmsPassage> findByGroupId(String groupId);

	/**
	 * TODO 获取最好的可用通道信息，结合优先级，通道状态等因素
	 *
	 * @param groupId
	 * @return
	 */
	SmsPassage getBestAvaiable(String groupId);

	/**
	 * 
	 * TODO 禁用或激活通道
	 * 
	 * @param id
	 * 		通道ID
	 * @param status
	 * 		状态标识
	 * @return
	 */
	boolean disabledOrActive(String id, String status);
	
	/**
	 * 
	 * @return
	 */
	public List<SmsPassage> findAll();
	
	/**
	 * 
	   * TODO 通道断开连接（针对直连，HTTP等其他协议直接忽略）
	   * @param id
	   * @return
	 */
	boolean kill(String id);

	/**
	 * 
	 * TODO 根据运营商查询通道信息
	 * 
	 * @param cmcp
	 * @return
	 */
	List<SmsPassage> getByCmcp(String cmcp);

	/**
	 * 根据运营商、路由类型、状态查询全部可用通道组下面的通道
	 * 
	 * @param groupId
	 *            通道组id
	 * @param cmcp
	 *            运营商
	 * @param routeType
	 *            路由类型
	 * @return
	 */
	List<SmsPassage> findAccessPassages(String groupId, String cmcp, int routeType);

	List<SmsPassage> findByCmcpOrAll(String cmcp);

	/**
	 * 
	 * TODO 加载到REDIS
	 * 
	 * @return
	 */
	boolean reloadToRedis();

	/**
	 * 根据通道ID获取省份通道信息
	 * 
	 * @param passageId
	 * @return
	 */
	List<SmsPassageProvince> getPassageProvinceById(String passageId);

	/**
	 * 根据省份代码和运营商获取通道信息
	 * 
	 * @param provinceCode
	 * @param cmcp
	 * @return
	 */
	List<SmsPassage> getByProvinceAndCmcp(Integer provinceCode, String cmcp);

	/**
	 * 测试通道
	 * 
	 * @param passageId
	 *            当前测试通道ID
	 * @param mobile
	 *            测试手机号码（支持多个）
	 * @param content
	 *            短信内容
	 * @return
	 */
	boolean doTestPassage(Integer passageId, String mobile, String content);

	/**
	 * 
	 * TODO 查询所有有效的通道代码信息
	 * 
	 * @return
	 */
	List<String> findPassageCodes();

	/**
	 * 
	   * TODO 监控告警短信(专指发送配置系统配置中的告警手机号码，内部使用)
	   * 
	   * @param mobile
	   * @param content
	   * @return
	 */
	boolean doMonitorSmsSend(String mobile, String content);
	
	/**
	 * 
	   * TODO 获取通道消息队列消费者数量
	   * 
	   * @param protocol
	   * 		协议类型
	   * @param passageCode
	   * 		通道代码
	   * @return
	 */
	boolean isPassageBelongtoDirect(String protocol, String passageCode);
}