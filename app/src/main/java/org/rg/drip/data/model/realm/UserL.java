package org.rg.drip.data.model.realm;

import org.rg.drip.data.ModelContract;
import org.rg.drip.data.model.User;

import io.realm.RealmObject;

/**
 * Created by TankGq
 * on 2018/3/22.
 */
public class UserL extends RealmObject implements ModelContract<User> {
	
	private Integer id;              // 用户 id
	private String username;         // 用户名
	private String password;         // 密码
	private String email;            // 邮箱
	private Boolean emailVerified;   // 邮箱是否验证
	
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
	
	@Override
	public User convertToNormal() {
		User user = new User();
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setPassword(this.getPassword());
		user.setEmail(this.getEmail());
		user.setEmailVerified(this.getEmailVerified());
		return user;
	}
}
