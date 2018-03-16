package org.rg.drip.data.model;

/**
 * 用户
 *
 * Created by TankGq
 * on 2018/3/16.
 */
public class User {

	public Integer id;              // 用户 id
	public String username;         // 用户名
	public String password;         // 密码
	public String email;            // 邮箱
	public Boolean emailVerified;   // 邮箱是否验证

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
}
