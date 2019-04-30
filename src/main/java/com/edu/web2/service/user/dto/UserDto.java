package com.edu.web2.service.user.dto;

import lingshi.valid.ObjectValid;

/**
 * 用户信息
 * 
 * @author caich
 *
 */
public class UserDto {

	/**
	 * 主键：id
	 */
	private String id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 用户类型（-1管理员 1普通用户）
	 */
	private Integer type;

	@Override
	public boolean equals(Object obj) {
		if (ObjectValid.isNotNull(obj)) {
			if (obj instanceof UserDto) {
				return ((UserDto) obj).getId().equals(this.id);
			}
		}
		return false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
