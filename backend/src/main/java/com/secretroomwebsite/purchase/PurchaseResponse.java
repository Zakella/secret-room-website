package com.secretroomwebsite.purchase;

import lombok.Data;

public record PurchaseResponse(String orderTrackingNumber, String orderSummaryHtml) { }
