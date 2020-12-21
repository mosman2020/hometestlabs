package sample.mybatis.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.hometest.mybatis.domain.User;
import com.hometest.mybatis.mapper.UserMapper;

@MybatisTest
class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	void selectByUserNameTest() {
		User user = userMapper.getUserByUsername("moh.elgamal@hotmail.com");
		assertThat(user.getPassword()).isEqualTo("password");

	}

}
