package org.rg.drip.data.model.cache;

import org.rg.drip.data.contract.ModelContract;
import org.rg.drip.data.model.remote.UserR;
import org.rg.drip.data.model.local.UserL;

/**
 * 用户
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class User implements ModelContract.Local<UserL>, ModelContract.Remote<UserR> {

	private Integer id;             // 用户 id
	private String username;        // 账号
	private String password;        // 密码
	private String name;            // 用户名
	private String email;           // 邮箱
	private Boolean emailVerified;  // 邮箱是否验证
	private String objectId;        // Bmob 数据的唯一标识符

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public UserL convertToLocal() {
		UserL user = new UserL();
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setName(this.getName());
		user.setEmail(this.getEmail());
		user.setEmailVerified(this.getEmailVerified());
		user.setObjectId(this.getObjectId());
		return user;
	}

	@Override
	public UserR convertToRemote() {
		UserR user = new UserR();
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setName(this.getName());
		user.setEmail(this.getEmail());
		user.setEmailVerified(this.getEmailVerified());
		user.setObjectId(this.getObjectId());
		return user;
	}
}
