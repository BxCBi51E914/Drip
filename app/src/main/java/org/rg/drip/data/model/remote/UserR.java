package org.rg.drip.data.model.remote;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by eee
 * on 2018/3/16.
 */
public class UserR extends BmobUser implements ModelContract.Cache<User> {
	
	private Integer id;         // 用户 id, 从1开始自增
	private String nickname;    // 昵称
	private String config;      // 用户设置

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getConfig() {
		return config;
	}
	
	public void setConfig(String config) {
		this.config = config;
	}
	
	@Override
	public User convertToCache() {
		User user = new User();
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setNickname(this.getNickname());
		user.setEmail(this.getEmail());
		user.setEmailVerified(this.getEmailVerified());
		user.setObjectId(this.getObjectId());
		user.setConfig(this.getConfig());
		return user;
	}
}
