package application.repositories;


import com.erikalves.application.model.User;
import com.erikalves.application.repositories.UserRepository;
import com.erikalves.application.utils.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired(required = true)
    UserRepository repository;
    User createdUser;
    Long existingUserId =1l;

    @Before
    public void begin() {

        createdUser = new User();
        createdUser.setUserName("Erik Alves");
        Util.interestCalculation(createdUser);
    }

    @Test
    public void shouldCreateUpdateDeleteUser() {

        shouldCreateUser();
        shouldUpdateUser();
        shouldDeleteUser();
    }

    public void shouldCreateUser() {

        User localUser = repository.save(createdUser);
        LOGGER.debug("saved entity ID {}", localUser);
        Assert.assertNotNull(localUser.getUserId());
        LOGGER.debug(" *** CREATE RESULT *** {}", localUser);
    }

    public void shouldDeleteUser() {

        Long id = createdUser.getUserId();
        repository.delete(id);
        repository.flush();
        User deletedUser = repository.findOne(id);
        Assert.assertEquals(null, deletedUser);
        LOGGER.debug(" *** DELETE RESULT *** {}", deletedUser);
    }

    public void shouldUpdateUser() {


        createdUser.setUserName("Name updated by JUNIT - John Doe is my name now");
        User updatedUser = repository.save(createdUser);
        Assert.assertTrue(null != updatedUser);
        Assert.assertTrue("Name updated by JUNIT - John Doe is my name now".equals(updatedUser.getUserName()));
        LOGGER.debug(" *** UPDATE RESULT *** {}", updatedUser.getUserName());
    }

    @Test
    public void shouldFindSpecificUser() {

        User findUser = repository.getOne(existingUserId);
        Assert.assertTrue(null != findUser);
        LOGGER.debug(" *** RESULT *** {}", findUser.toString());

    }

    @Test
    public void shouldFindAllUsers()  {

        List<User> list = repository.findAll();
        LOGGER.debug(" *** LIST *** {}", list);
        Assert.assertTrue(null != list);
        for(User user: list){
            Assert.assertTrue(null != user);
            Assert.assertTrue(null != user.getUserId());
            LOGGER.debug(" *** RESULT *** {}", user.toString());
        }
    }

}