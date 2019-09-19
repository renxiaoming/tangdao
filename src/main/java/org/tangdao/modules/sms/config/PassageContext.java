package org.tangdao.modules.sms.config;

public class PassageContext {

    /**
     * 异常通道ID
     */
    public static final String EXCEPTION_PASSAGE_ID = "0";

    /**
     * 异常（根据ID找不到通道等情况）显示的通道名称
     */
    public static final String EXCEPTION_PASSAGE_NAME = "N/A";


    /**
     * TODO 录用类型
     *
     * @version V1.0.0
     * @date 2016年10月7日 下午8:48:03
     */
    public enum RouteType {
        DEFAULT(0, "默认路由"), VALIDATE_CODE(1, "验证码路由"), QUIK_NOTIFICATION(2, "即时通知路由"), BATCH_NOTIFICATION(3, "批量通知路由"), HIGH_DANGER(4, "高风险投诉路由");

        private int    value;

        private String name;

        RouteType(int value, String name) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static RouteType parse(int value) {
            for (RouteType type : RouteType.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            return RouteType.DEFAULT;
        }
    }

    /**
     * TODO 通道状态
     *
     * @version V1.0.0
     * @date 2016年10月7日 下午10:28:04
     */
    public enum PassageStatus {
        ACTIVE(0, "有效"), HANGUP(2, "暂停使用"), SERVER_EXCEPTION(9, "服务器异常停用");

        private int    value;
        private String title;

        PassageStatus(int value, String title) {
            this.title = title;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }

        public static PassageStatus getByValue(int value) {
            for (PassageStatus type : PassageStatus.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            return PassageStatus.ACTIVE;
        }
    }

    /**
     * TODO 返回数字定义
     *
     * @version V1.0.0
     * @date 2016年10月7日 下午8:48:03
     */
    public enum ResultCode {
        ZERO("0", "失败"), ONE("1", "成功"), TWO("2", "重复"), THREE("3", "记录重复并且状态为停用");

        private String value;

        private String title;

        ResultCode(String value, String title) {
            this.title = title;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }
    }

    /**
     * TODO 回执状态
     *
     * @version V1.0.0
     * @date 2016年11月30日 下午10:50:32
     */
    public enum DeliverStatus {
        SUCCESS("0", "成功"), FAILED("1", "失败");

        private String value;
        private String title;

        DeliverStatus(String value, String title) {
            this.title = title;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }

    }

    /**
     * TODO 推送状态
     *
     * @version V1.0
     * @date 2016年12月1日 下午6:54:24
     */
    public enum PushStatus {
        SUCCESS("0", "成功"), FAILED("1", "失败"),;

        private String value;
        private String title;

        PushStatus(String value, String title) {
            this.title = title;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }
    }

    /**
     * TODO 通道签名模式
     *
     * @version V1.0.0
     * @date 2017年6月10日 下午6:49:03
     */
    public enum PassageSignMode {
        IGNORED(0, "不处理"), SIGNATURE_AUTO_PREPOSITION(1, "签名自动前置"), SIGNATURE_AUTO_POSTPOSITION(2, "签名自动后置"), REMOVE_SIGNATURE(3, "自动去掉签名");

        private int    value;
        private String title;

        PassageSignMode(int value, String title) {
            this.title = title;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }
    }

    /**
     * TODO 通道短信模板状态
     *
     * @version V1.0
     * @date 2017年8月30日 下午1:51:37
     */
    public enum PassageMessageTemplateStatus {
        AVAIABLE(0, "使用中"), DISABLED(1, "停用");

        private int    value;
        private String title;

        PassageMessageTemplateStatus(int value, String title) {
            this.title = title;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }

    }

    /**
     * TODO 通道是否需要通道方强制携带规定参数
     *
     * @version V1.0
     * @date 2017年9月19日 上午9:57:08
     */
    public enum PassageSmsTemplateParam {
        NO(0, "不需要"), YES(1, "需要");

        private int    value;
        private String title;

        PassageSmsTemplateParam(int value, String title) {
            this.title = title;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }

    }
}
