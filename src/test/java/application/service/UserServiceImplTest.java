package application.service;

import com.erikalves.application.model.User;
import com.erikalves.application.repositories.UserRepository;
import com.erikalves.application.service.UserService;
import com.erikalves.application.utils.Util;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTest.class);
    @Autowired
    @Qualifier(value="UserService")
    UserService service;
    @Autowired (required=true)
    UserRepository repository;
    User user;
    User savedUser;

    @Before
    public void  begin(){

        user = new User();
        user.setUserName("Erik Alves");

        savedUser = service.save(user);
        LOGGER.debug("saved user ID {}",savedUser.toString());
        Assert.assertNotNull(savedUser.getUserId());

    }

    @Test
    public void shouldFindSpecificUser() {

        Long userId = service.getId(savedUser);
        Assert.assertNotNull(userId);
    }

    @Test
    public void getRepository() {

        CrudRepository<User, Long> crudRepository = service.getRepository();
        Assert.assertNotNull(crudRepository);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(repository,crudRepository));
    }

    @Test
    public void shouldFindAllUsers() {

        List<User> list = Util.iterableToCollection(service.findAll());
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() >0);

    }
}