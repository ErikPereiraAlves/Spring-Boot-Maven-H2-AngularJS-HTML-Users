package application.controllers;

import com.erikalves.application.model.User;
import com.erikalves.application.service.UserService;
import com.erikalves.application.utils.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestApiControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestApiControllerTest.class);
    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    @Autowired
    @Qualifier(value = "UserService")
    UserService service;
    String json;
    User savedUser;
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/app"+ uri;
    }

    @Before
    public void init() {
        shouldCreateInitialTestUser();
    }

    private void shouldCreateInitialTestUser() {

        //given
        User user = new User();
        user.setUserName("Jose Maria DBC");
        Util.interestCalculation(user);
        savedUser = service.save(user);
        json = Util.getGson().toJson(savedUser);
        LOGGER.debug("Json representation of a the created entity {} ", json);

        //when
        User findUser = service.get(savedUser.getUserId());

        //then
        assertTrue(findUser.getUserId()==(savedUser.getUserId()));
    }


    @Test
    public void shouldFindAllUsers() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/users"),
                HttpMethod.GET,entity,String.class);
        LOGGER.debug("Response results {}",response.getBody());
        Assert.assertFalse(response.getBody().contains("Internal Server Error"));
        Assert.assertTrue(response.getBody().contains("[{\"userId\":"));
    }

    @Test
    public void shouldFindSpecificUser() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/users/1"),
                HttpMethod.GET,entity,String.class);
        LOGGER.debug("Response results {}",response.getBody());
        Assert.assertFalse(response.getBody().contains("Internal Server Error"));
        Assert.assertTrue(response.getBody().contains("{\"userId\":1,\"userName\":\"USER A\",\"userLimitCredit\":1000.00,\"userRisk\":\"A\",\"userInterest\":10.25}"));
    }

    @Test
    public void shouldDeleteUser() {

        restTemplate.delete(createURLWithPort("/api/v1/users/3"));
        User deletedUser = service.get(Util.LongfyId("3"));
        LOGGER.debug("Response results {}",deletedUser);
        Assert.assertNull(deletedUser);

    }

    @Test
    public void shouldCreateUser() {

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(createURLWithPort("/api/v1/users") , savedUser, String.class);
        LOGGER.debug("Response results {}",responseEntity.getBody().toString());
        Assert.assertNotNull(responseEntity);

    }

    @Test
    public void shouldUpdateUser() {

        Long userId = savedUser.getUserId();
        savedUser.setUserName("UPDATED BY JUNIT");
        restTemplate.put(createURLWithPort("/api/v1/users") , savedUser, String.class);
        User updatedUser = service.get(userId);
        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(updatedUser.getUserId() , userId);
        LOGGER.debug("Response results {}",updatedUser.toString());
    }
}