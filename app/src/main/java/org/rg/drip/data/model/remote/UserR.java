package org.rg.drip.data.model.remote;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by TankGq
 * on 2018/3/16.
 */
public class UserR extends BmobUser implements ModelContract.Cache<User> {
	
	private Integer id;         // 用户 id
	private String nickname;        // 昵称

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

	@Override
	public User convertToCache() {
		User user = new User();
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setNickname(this.getNickname());
		user.setEmail(this.getEmail());
		user.setEmailVerified(this.getEmailVerified());
		user.setObjectId(this.getObjectId());
		return user;
	}
}
