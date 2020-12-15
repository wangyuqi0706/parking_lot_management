package org.group3.parking.controller;

import org.group3.parking.ParkingApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParkingApplication.class)
@WebAppConfiguration
@Transactional
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void enter() throws Exception {
        enterRequest("川ATEST1", "2020-12-09T10:20", "ok");
        enterRequest("川ATEST2", "2020-12-09T10:20", "ok");
        //同一辆车试图进入两次
        enterRequest("川ATEST2", "2020-12-09T10:30", "error");


    }

    @Test
    public void leave() throws Exception {
        enterRequest("川ATEST1", "2020-12-09T10:20", "ok");
        enterRequest("川ATEST2", "2020-12-09T10:20", "ok");
        leaveRequest("川ATEST1", "2020-12-09T17:16");
        leaveRequest("川ATEST2", "2020-12-10T10:30");
        //未进入的车辆试图离开
        leaveRequest("川ATEST3", "2020-12-10T10:30");

        //TEST1再次进入
        enterRequest("川ATEST1", "2020-12-11T10:20", "ok");
        leaveRequest("川ATEST1", "2020-12-14T10:20");


    }


    @Test
    public void pay() {
    }

    private void enterRequest(String plateNumber, String enterTime, String expected) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/user/enter")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("plateNumber", plateNumber)
                .param("enterTime", enterTime))
                .andExpect(MockMvcResultMatchers.content().string(expected))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

    }

    private void leaveRequest(String plateNumber, String leaveTime) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/user/leave")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("plateNumber", plateNumber)
                .param("leaveTime", leaveTime))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}