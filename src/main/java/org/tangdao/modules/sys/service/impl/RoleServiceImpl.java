package org.tangdao.modules.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.common.utils.StringUtils;
import org.tangdao.modules.sys.mapper.RoleMapper;
import org.tangdao.modules.sys.model.domain.Role;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.IRoleService;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Service
public class RoleServiceImpl extends CrudServiceImpl<RoleMapper, Role> implements IRoleService {

	public List<Role> findByUserCode(User user) {
		return baseMapper.findByUserCode(user);
	}

	/**
	 * 检查角色名称
	 * 
	 * @param oldRoleName
	 * @param roleName
	 * @return
	 */
	public boolean checkRoleNameExists(String oldRoleName, String roleName) {
		if (roleName != null && roleName.equals(oldRoleName)) {
			return true;
		} else if (roleName != null && this.count(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, roleName)) == 0) {
			return true;
		}
		return false;
	}

	public void insertRoleMenu(Role role, String[] menuCodes) {
		// 删除原有角色的所有权限
		this.baseMapper.deleteRoleMenu(role.getRoleCode());
		// 新增角色授权
		if (menuCodes != null && StringUtils.isNotBlank(role.getRoleCode())) {
			this.baseMapper.insertRoleMenu(role.getRoleCode(), menuCodes);
		}
	}
}
