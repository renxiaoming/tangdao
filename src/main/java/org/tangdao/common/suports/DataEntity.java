package org.tangdao.common.suports;

import java.util.Date;

import org.tangdao.common.utils.StringUtils;
import org.tangdao.modules.sys.entity.User;
import org.tangdao.modules.sys.utils.UserUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DataEntity<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 基础字段
	protected String status; // 状态

	protected String remarks; // 备注

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createTime; // 新增时间

	protected String createBy; // 新增人

	@TableField(exist = false)
	protected String createByName; // 新增人姓名

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updateTime; // 更新时间

	protected String updateBy; // 更新人

	@TableField(exist = false)
	protected String updateByName; // 更新人姓名

	public DataEntity() {
		this(null);
	}

	public DataEntity(String key) {
		super(key);
	}

	/**
	 * 新增预处理
	 */
	public void preInsert() {
		this.isNewRecord = true;
		if (StringUtils.isBlank(this.getKey())) {
			this.setKey(IdWorker.getIdStr());
		}

		if (StringUtils.isBlank(getStatus())) {
			setStatus(STATUS_NORMAL);
		}

		User user = UserUtils.getUser();
		if (user != null) {
			this.setCurrentUser(user);
			this.createBy = user.getUserCode();
			this.updateBy = this.createBy;
		}
		this.updateTime = new Date();
		this.createTime = this.updateTime;
	}

	/**
	 * 更新预处理
	 */
	public void preUpdate() {
		User user = UserUtils.getUser();
		if (user != null) {
			this.setCurrentUser(user);
			this.updateBy = user.getUserCode();
		}
		this.updateTime = new Date();
	}

}
