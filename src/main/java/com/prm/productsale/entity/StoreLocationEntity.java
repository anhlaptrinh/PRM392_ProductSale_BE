package com.prm.productsale.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity(name = "StoreLocations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class StoreLocationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "LocationID")
  Integer locationId;

  @Column(name = "Latitude", nullable = false)
  Double latitude;

  @Column(name = "Longitude", nullable = false)
  Double longitude;

  @Column(name = "Address", length = 255, nullable = false)
  String address;

}
