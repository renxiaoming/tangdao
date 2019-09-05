package org.tangdao.modules.sms.config;

public class UserContext {

	/**
	 * 
	 * TODO 用户来源
	 *
	 * @version V1.0.0
	 * @date 2016年3月9日 下午11:58:59
	 */
	public enum Source {

		WEB_REGISTER("1", "页面注册"), BOSS_INPUT("2", "后台录入"), PARTNER_SEND("3",
				"第三方平台发送");

		private Source(String value, String title) {
			this.value = value;
			this.title = title;
		}

		private String value;
		private String title;

		public String getValue() {
			return value;
		}

		public String getTitle() {
			return title;
		}

	}

	/**
	 * 
	 * TODO 余额类型
	 * 
	 * @version V1.0
	 * @date 2016年9月18日 下午6:08:15
	 */
	public enum UserBalanceType {
		DEFAULT("0", "默认余额"), SMS("1", "短信余额"), FS("2", "流量余额"), VS("3", "语音余额"), MMS("4", "彩信余额");

		private String value;

		private String memo;

		private UserBalanceType(String value, String memo) {
			this.value = value;
			this.memo = memo;
		}

		public String getValue() {
			return value;
		}

		public String getMemo() {
			return memo;
		}
	}

	/**
	 * 
	  * TODO 用户账号
	  * 
	  * @version V1.0   
	  * @date 2016年9月22日 下午6:37:13
	 */
	public enum UserStatus {
		YES("0", "有效"), NO("2", "无效");

		private String value;

		private String memo;

		private UserStatus(String value, String memo) {
			this.value = value;
			this.memo = memo;
		}

		public String getValue() {
			return value;
		}

		public String getMemo() {
			return memo;
		}
	}
	
	/**
	 * 
	  * TODO 余额付费类型
	  *
	  * @version V1.0.0   
	  * @date 2017年2月21日 下午9:44:22
	 */
	public enum BalancePayType {
		PREPAY(1, "预付"), POSTPAY(2, "后付");

		private int	   value;
		private String title;

		private BalancePayType(int value, String title) {
			this.value = value;
			this.title = title;
		}

		public int getValue() {
			return value;
		}

		public String getTitle() {
			return title;
		}
		
	}
	
	/**
	 * 
	  * TODO 余额告警状态
	  * @version V1.0   
	  * @date 2017年8月16日 下午6:22:50
	 */
	public enum BalanceStatus {
		AVAIABLE("0", "可用"), WARNING("1", "告警中");

		private String value;
		private String title;

		private BalanceStatus(String value, String title) {
			this.value = value;
			this.title = title;
		}

		public String getValue() {
			return value;
		}

		public String getTitle() {
			return title;
		}
		
	}
}
