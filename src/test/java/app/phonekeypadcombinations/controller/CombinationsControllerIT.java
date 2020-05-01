package app.phonekeypadcombinations.controller;

import app.phonekeypadcombinations.model.Result;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class CombinationsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    public String url, countUrl;
    public List<String> expected;

    @BeforeEach
    public void before() {

        url = "/phoneNum/1234567/combinations?page=21&size=6";
        countUrl = "/phoneNum/1234567/combinations/count";
        expected = List.of("123GKMP","123GKMQ","123GKMR","123GKMS","123GKN7","123GKNP");

    }

    @Test
    public void contextLoads() throws JSONException {

        Map<String, String> params = new HashMap<>();
        params.put("page", "3");
        params.put("size", "2");

        ResponseEntity<Result> entity = this.restTemplate.getForEntity(url, Result.class);
        Result response = entity.getBody();

        assertEquals("1234567", response.getPhoneNum());
        MatcherAssert.assertThat(response.getCombinations(), Matchers.is(expected));
        assertEquals(21, response.getPageNum());
        assertEquals(6, response.getPageSize());

    }

    @Test
    public void contextLoads_count() throws JSONException {

        ResponseEntity<Result> entity = this.restTemplate.getForEntity(countUrl, Result.class);
        Result response = entity.getBody();

        assertEquals("1234567", response.getPhoneNum());
        assertEquals(5120, response.getResultCount());
        assertEquals(0, response.getPageNum());
        assertEquals(0, response.getPageSize());

    }

}