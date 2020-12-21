/**
 * 
 */
package com.hometest.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.hometest.mybatis.dao.UserDao;

/**
 * @author hometest
 *
 */
public class UnlockUsersJob implements Job{

	@Autowired
	UserDao userDao;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		userDao.unlockUsers();
	}

}
