package ch.uzh.ifi.seal.soprafs15.controller;

import java.net.URL;
import java.util.List;

import ch.uzh.ifi.seal.soprafs15.Application;

//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import ch.uzh.ifi.seal.soprafs15.controller.beans.JsonUriWrapper;
import ch.uzh.ifi.seal.soprafs15.controller.beans.user.UserRequestBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.user.UserResponseBean;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class UserServiceControllerIT {

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        this.template = new TestRestTemplate();
    }
	
	@Test
	@SuppressWarnings("unchecked")
	public void testCreateUserSuccess() {		
		List<UserResponseBean> usersBefore = template.getForObject(base + "/user", List.class);
		Assert.assertEquals(0, usersBefore.size());
		
		UserRequestBean request = new UserRequestBean();
		request.setName("Mike Meyers");
		request.setUsername("mm");

		HttpEntity<UserRequestBean> httpEntity = new HttpEntity<UserRequestBean>(request);
		
		ResponseEntity<JsonUriWrapper> response = template.exchange(base + "/user/", HttpMethod.PUT, httpEntity, JsonUriWrapper.class);
		Assert.assertEquals("/user/1", response.getBody().getUri());
		
	    List<UserResponseBean> usersAfter = template.getForObject(base + "/user", List.class);
		Assert.assertEquals(1, usersAfter.size());		
		
		ResponseEntity<UserResponseBean> userResponseEntity = template.getForEntity(base + response.getBody().getUri(), UserResponseBean.class);
		UserResponseBean userResponse = userResponseEntity.getBody(); 
		Assert.assertEquals(request.getName(), userResponse.getName());
		Assert.assertEquals(request.getUsername(), userResponse.getUsername());
	}

}
