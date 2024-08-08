package com.example.onlineshopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Long deliveryAddressId;
    private String courierName;
    private String trackingNumber;
}
