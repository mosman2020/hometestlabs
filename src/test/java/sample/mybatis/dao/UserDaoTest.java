/**
 * 
 */
package sample.mybatis.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.hometest.mybatis.dao.UserDao;
import com.hometest.mybatis.domain.User;

/**
 * @author moosman
 *
 */

@MybatisTest
@Import(UserDao.class)
class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Test
	void selectCityByIdTest() {
		User user = userDao.getUserByUsername("moh.elgamal@hotmail.com");
		assertThat(user.getUserId()).isEqualTo(1);
//    assertThat(user.getUserName()).isEqualTo("mosman");
		assertThat(user.getPassword()).isEqualTo("password");

	}

}