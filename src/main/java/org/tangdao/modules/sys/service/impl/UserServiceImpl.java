package org.tangdao.modules.sys.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tangdao.common.service.impl.CrudServiceImpl;
import org.tangdao.modules.paas.config.PaasRedisConstant;
import org.tangdao.modules.sys.mapper.UserMapper;
import org.tangdao.modules.sys.model.domain.User;
import org.tangdao.modules.sys.service.IUserService;
import org.tangdao.modules.sys.utils.UserUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ruyang
 * @since 2019-07-02
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {
	
	@Resource
    private StringRedisTemplate      stringRedisTemplate;
	
	private Logger                   logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	private PasswordEncoderService passwordEncoderService;
	
	private String getKey(String userCode) {
        return String.format("%s:%s", PaasRedisConstant.RED_USER_LIST, userCode);
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = UserUtils.getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("账号不存在");
		}
		return user;
	}

	public boolean checkUsernameExists(String oldUsername, String username) {
		if (username != null && username.equals(oldUsername)) {
			return true;
		} else if (username != null && this.count(Wrappers.<User>lambdaQuery().eq(User::getUsername, username)) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void registerUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserinfo(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLoginUserinfo(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePassword(String userCode, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public User getUserByUsername(String username) {
		return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
	}
	
	@Override
	public User getByUsercode(String userCode) {
		 try {
            Object obj = stringRedisTemplate.opsForValue().get(getKey(userCode));
            if (obj != null) {
                return JSON.parseObject(obj.toString(), User.class);
            }
        } catch (Exception e) {
            logger.warn("REDIS 加载失败，将于DB加载", e);
        }
		return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserCode, userCode));
	}
	
}
