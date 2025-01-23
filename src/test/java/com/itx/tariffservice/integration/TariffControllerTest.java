package com.itx.tariffservice.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.itx.tariffservice.TariffServiceApplication;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = TariffServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-properties.yml")
class TariffControllerTest {

  private final Logger logger = LoggerFactory.getLogger(TariffControllerTest.class);

  @Autowired private MockMvc mockMvc;

  private static List<TestInput> generateInput() {
    return List.of(
        new TestInput("1", "35.5", "2020-06-14T08:00:00.000Z"),
        new TestInput("2", "25.45", "2020-06-14T14:00:00.000Z"),
        new TestInput("1", "35.5", "2020-06-14T19:00:00.000Z"),
        new TestInput("3", "30.5", "2020-06-15T08:00:00.000Z"),
        new TestInput("4", "38.95", "2020-06-16T19:00:00.000Z"));
  }

  @ParameterizedTest
  @MethodSource("generateInput")
  void findTariffs(TestInput input) throws Exception {
    MvcResult tariffRes =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/tariff")
                    .queryParam("productId", "35455")
                    .queryParam("brandId", "1")
                    .queryParam("applicationDatetime", input.datetimeStr)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    String content = tariffRes.getResponse().getContentAsString();
    assertTrue(content.contains("\"tariffId\":" + input.tariffId));
    assertTrue(content.contains(input.priceStr));
    logger.info(
        "Expected tariff found with tariffId: {}, price {}, in datetime {}",
        input.tariffId,
        input.priceStr,
        input.datetimeStr);
    logger.info("Content: {}", content);
  }
  
  @Test
  void findTariffsWithNoTariffFound() throws Exception {
    MvcResult tariffRes =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/tariff")
                    .queryParam("productId", "35455")
                    .queryParam("brandId", "1")
                    .queryParam("applicationDatetime", "2020-06-10T08:00:00.000Z")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    String content = tariffRes.getResponse().getContentAsString();
    assertTrue(content.contains("Tariff not found"));
    logger.info("Content: {}", content);
  }

  @Test
  void findTariffsWithManyTariffConflicting() throws Exception {
    MvcResult tariffRes =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/tariff")
                    .queryParam("productId", "35455")
                    .queryParam("brandId", "1")
                    .queryParam("applicationDatetime", "2021-11-20T00:00:00.000Z")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    String content = tariffRes.getResponse().getContentAsString();
    assertTrue(content.contains("Many tariffs available"));
    logger.info("Content: {}", content);
  }

  private record TestInput(String tariffId, String priceStr, String datetimeStr) {}
}
