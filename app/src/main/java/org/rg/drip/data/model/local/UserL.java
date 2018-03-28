package org.rg.drip.data.model.local;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.cache.User;

import io.realm.RealmObject;

/**
 * Created by TankGq
 * on 2018/3/22.
 */
public class UserL extends RealmObject implements ModelContract.Cache<User> {

	private Integer id;              // 用户 id
	private String username;         // 账号
	private String nickname;         // 昵称
	private String email;            // 邮箱
	private Boolean emailVerified;   // 邮箱是否验证
	private String objectId;         // Bmob 数据的唯一标识符

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
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
