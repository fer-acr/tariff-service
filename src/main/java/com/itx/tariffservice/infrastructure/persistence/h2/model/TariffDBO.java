package com.itx.tariffservice.infrastructure.persistence.h2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.Instant;

@Entity
@Table(name = "tariffs")
public class TariffDBO {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "brand_id")
  private Integer brandId;
  @Column(name = "start_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Instant startDate;
  @Column(name = "end_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Instant endDate;
  @Column(name = "price_list")
  private Integer priceList;
  @Column(name = "product_id")
  private Integer productId;
  @Column(name = "priority")
  private Integer priority;
  @Column(name = "price")
  private Float price;
  @Column(name = "curr")
  private String currency;

}
