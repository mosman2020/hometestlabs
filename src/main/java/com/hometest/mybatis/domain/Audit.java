/**
 * 
 */
package com.hometest.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author moosman
 *
 */

@Data
public class Audit implements Serializable{

	private static final long serialVersionUID = 8767251955035456549L;
	
	private Date createdDate;
	private Long createdBy 	;
	private Long updatedBy 	;
	private Date updatedDate;
	@JsonIgnore
	private boolean deleted;
	@JsonIgnore
	private Date deletedDate;
		
	public Audit() {
	}
	
}
