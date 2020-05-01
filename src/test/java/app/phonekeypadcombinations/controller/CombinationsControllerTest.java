package app.phonekeypadcombinations.controller;

import app.phonekeypadcombinations.business.CombinationsGeneratorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CombinationsController.class)
public class CombinationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CombinationsGeneratorService service;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    void getCombinations_negative_2digit() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/23/combinations")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"phoneNum\":\"23\",\"resultCount\":0,\"pageNum\":0,\"pageSize\":0,\"errors\":[\"Phone number length should be 7 or 10\"]}"))
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andReturn();

    }

    @Test
    void getCombinations_negative_2AlphaNumeric() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/23a4&9/combinations")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"phoneNum\":\"23a4&9\",\"resultCount\":0,\"pageNum\":0,\"pageSize\":0,\"combinations\":[],\"errors\":[\"Phone number must be numeric\",\"Phone number length should be 7 or 10\"]}"))
                .andExpect(jsonPath("$.errors.length()").value(2))
                .andReturn();

    }

    @Test
    void getCombinations_7digitPhone() throws Exception {

        List<String> resultList = IntStream.range(0, 5120).mapToObj((n) -> ""+n).collect(Collectors.toList());
        Mockito.when(service.getCombinations("1234567")).thenReturn(resultList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/1234567/combinations")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"phoneNum\":\"1234567\",\"resultCount\":5120,\"pageNum\":0,\"pageSize\":10}"))
                .andExpect(jsonPath("$.resultCount").value(5120))
                .andExpect(jsonPath("$.combinations").isArray())
                .andExpect(jsonPath("$.combinations.length()").value(10))
                .andExpect(jsonPath("$.combinations", Matchers.contains("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")))
                .andReturn();

    }

    @Test
    void getCombinations_10digitPhone() throws Exception {

        List<String> resultList = IntStream.range(0, 102400).mapToObj((n) -> ""+n).collect(Collectors.toList());
        Mockito.when(service.getCombinations("1234567890")).thenReturn(resultList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/1234567890/combinations")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"phoneNum\":\"1234567890\",\"resultCount\":102400,\"pageNum\":0,\"pageSize\":10}"))
                .andExpect(jsonPath("$.resultCount").value(102400))
                .andExpect(jsonPath("$.combinations").isArray())
                .andExpect(jsonPath("$.combinations.length()").value(10))
                .andExpect(jsonPath("$.combinations", Matchers.contains("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getCombinations_10digitPhone_pageSize2() throws Exception {

        List<String> resultList = IntStream.range(0, 5120).mapToObj((n) -> ""+n).collect(Collectors.toList());
        Mockito.when(service.getCombinations("1234567")).thenReturn(resultList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/1234567/combinations")
                .param("page","0")
                .param("size", "2")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"phoneNum\":\"1234567\",\"resultCount\":5120,\"pageNum\":0,\"pageSize\":2}"))
                .andExpect(jsonPath("$.phoneNum").value("1234567"))
                .andExpect(jsonPath("$.resultCount").value(5120))
                .andExpect(jsonPath("$.combinations").isArray())
                .andExpect(jsonPath("$.combinations.length()").value(2))
                .andExpect(jsonPath("$.combinations", Matchers.contains("0", "1")))
                .andExpect(jsonPath("$.pageSize").value(2))
                .andReturn();

    }

    @Test
    void getCombinations_10digitPhone_page40() throws Exception {
        List<String> resultList = IntStream.range(1, 5121).mapToObj((n) -> ""+n).collect(Collectors.toList());
        Mockito.when(service.getCombinations("1234567")).thenReturn(resultList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/1234567/combinations")
                .param("page","42")
                .param("size", "8")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"phoneNum\":\"1234567\",\"resultCount\":5120,\"pageNum\":42,\"pageSize\":8}"))
                .andExpect(jsonPath("$.phoneNum").value("1234567"))
                .andExpect(jsonPath("$.resultCount").value(5120))
                .andExpect(jsonPath("$.combinations").isArray())
                .andExpect(jsonPath("$.combinations.length()").value(8))
                .andExpect(jsonPath("$.combinations", Matchers.containsInAnyOrder("337", "338", "339", "340", "341", "342", "343", "344")))
                .andExpect(jsonPath("$.pageSize").value(8))
                .andReturn();

    }

    @Test
    void getCount() throws Exception{
        Mockito.when(service.countCombinations("1234567")).thenReturn(5120);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/1234567/combinations/count")
                .param("page","0")
                .param("size", "0")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"phoneNum\":\"1234567\",\"resultCount\":5120,\"pageNum\":0,\"pageSize\":0}"))
                .andExpect(jsonPath("$.phoneNum").value("1234567"))
                .andExpect(jsonPath("$.resultCount").value(5120));
    }

    @Test
    void getCount_badRequest() throws Exception{

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/1234&ab567/combinations/count")
                .param("page","0")
                .param("size", "0")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"phoneNum\":\"1234&ab567\",\"resultCount\":0,\"pageNum\":0,\"pageSize\":0}"))
                .andExpect(jsonPath("$.phoneNum").value("1234&ab567"))
                .andExpect(jsonPath("$.resultCount").value(0));
    }

    @Test
    void getCount_negative_2digit() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/23/combinations/count")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"phoneNum\":\"23\",\"resultCount\":0,\"pageNum\":0,\"pageSize\":0,\"errors\":[\"Phone number length should be 7 or 10\"]}"))
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andReturn();

    }

    @Test
    void getCount_negative_AlphaNumeric() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/phoneNum/23a4&9/combinations/count")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"phoneNum\":\"23a4&9\",\"resultCount\":0,\"pageNum\":0,\"pageSize\":0,\"combinations\":[],\"errors\":[\"Phone number must be numeric\",\"Phone number length should be 7 or 10\"]}"))
                .andExpect(jsonPath("$.errors.length()").value(2))
                .andReturn();

    }
}