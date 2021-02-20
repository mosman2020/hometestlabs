package com.hometest.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @author hometest
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword extends Audit implements Serializable{
	private String oldPassword;
	private String newPassword;
	private String userName;
}