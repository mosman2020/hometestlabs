/**
 * 
 */
package com.hometest.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hometest
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenData {
	private String token;
	private long userid;
	
}
