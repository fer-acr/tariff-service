package com.itx.tariffservice.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.itx.tariffservice.domain.model.Tariff;
import com.itx.tariffservice.domain.model.exception.ManyTariffsAvailableException;
import com.itx.tariffservice.domain.model.exception.TariffNotFoundException;
import com.itx.tariffservice.domain.repository.TariffRepository;
import java.time.Instant;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TariffServiceTest {

  private final int productId = 35455;
  private final int brandId = 1;
  private TariffService tariffService;
  @Mock private TariffRepository tariffRepository;

  @BeforeEach
  void setUp() {
    tariffService = new TariffService(tariffRepository);
  }

  @Test
  void emptyTariffResultThrowsNotFoundException() {
    when(tariffRepository.findByProductIdBrandIdInDateSortedByPriorityDesc(
            anyInt(), anyInt(), any()))
        .thenReturn(List.of());

    assertThrows(
        TariffNotFoundException.class, () -> tariffService.findActiveTariff(1, 1, Instant.now()));
  }

  @Test
  void samePriorityTariffsResultThrowsManyTariffsAvailableException() {
    when(tariffRepository.findByProductIdBrandIdInDateSortedByPriorityDesc(
            anyInt(), anyInt(), any()))
        .thenReturn(createSamePriorityTariffs());

    assertThrows(
        ManyTariffsAvailableException.class,
        () -> tariffService.findActiveTariff(1, 1, Instant.now()));
  }

  @Test
  void validTariffResult() {
    Instant firstStart =
        OffsetDateTime.of(2020, Month.JUNE.getValue(), 14, 0, 0, 0, 0, ZoneOffset.of("+02:00"))
            .toInstant();
    Instant firstEnd =
        OffsetDateTime.of(
                2020, Month.DECEMBER.getValue(), 31, 23, 59, 59, 0, ZoneOffset.of("+01:00"))
            .toInstant();
    Instant secondStart =
        OffsetDateTime.of(2020, Month.JUNE.getValue(), 14, 15, 0, 0, 0, ZoneOffset.of("+02:00"))
            .toInstant();
    Instant secondEnd =
        OffsetDateTime.of(2020, Month.JUNE.getValue(), 14, 18, 30, 0, 0, ZoneOffset.of("+02:00"))
            .toInstant();

    Float firstPrice = 35.50f;
    Float secondPrice = 25.45f;

    when(tariffRepository.findByProductIdBrandIdInDateSortedByPriorityDesc(
            anyInt(), anyInt(), any()))
        .thenReturn(
            Stream.of(
                    createTariff(0, 1, firstPrice, firstStart, firstEnd),
                    createTariff(1, 2, secondPrice, secondStart, secondEnd))
                .sorted(Comparator.comparing(Tariff::getPriority).reversed())
                .toList());

    Tariff tariff =
        tariffService.findActiveTariff(
            productId, brandId, firstStart.plus(15, ChronoUnit.HOURS).plusSeconds(1));

    assertEquals(secondPrice, tariff.getPrice());
    assertEquals(2, tariff.getPriceList());
  }

  private Tariff createTariff(
      Integer priority, Integer priceList, Float price, Instant startDate, Instant endDate) {
    Tariff tariff = new Tariff();
    tariff.setBrandId(brandId);
    tariff.setProductId(productId);
    tariff.setPriority(priority);
    tariff.setPriceList(priceList);
    tariff.setPrice(price);
    tariff.setStartDate(startDate);
    tariff.setEndDate(endDate);
    return tariff;
  }

  private List<Tariff> createSamePriorityTariffs() {
    Tariff tariff = new Tariff();
    tariff.setBrandId(1);
    tariff.setProductId(2);
    tariff.setPriority(0);
    tariff.setPriceList(1);
    tariff.setPrice(1.1f);
    tariff.setStartDate(Instant.now().minus(1, ChronoUnit.DAYS));
    tariff.setEndDate(Instant.now().plus(1, ChronoUnit.DAYS));

    Tariff tariff2 = new Tariff();
    tariff2.setBrandId(1);
    tariff2.setProductId(2);
    tariff2.setPriority(0);
    tariff2.setPriceList(2);
    tariff2.setPrice(3.3f);
    tariff2.setStartDate(Instant.now().minus(1, ChronoUnit.DAYS));
    tariff2.setEndDate(Instant.now().plus(1, ChronoUnit.DAYS));

    return Stream.of(tariff, tariff2).sorted(Comparator.comparing(Tariff::getPriority)).toList();
  }
}
