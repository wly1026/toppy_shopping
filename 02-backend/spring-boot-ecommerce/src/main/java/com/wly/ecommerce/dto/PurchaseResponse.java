package com.wly.ecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

    // lombak will generate constructor for final field
    // another option: add @NotNull
    private final String orderTrackingNumber;

}
