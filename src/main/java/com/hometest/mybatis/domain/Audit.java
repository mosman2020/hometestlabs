/**
 * 
 */
package com.hometest.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author moosman
 *
 */

@JsonInclude(value=JsonInclude.Include.NON_EMPTY, content=JsonInclude.Include.NON_NULL)
@Data
public class Audit implements Serializable{

	private static final long serialVersionUID = 8767251955035456549L;
	private Date createdDate;
	private Long createdBy 	;
	private Long updatedBy 	;
	private Date updatedDate;
	private boolean deleted;
	private Date deletedDate;
	
	
	public Audit() {
	}
	
}
