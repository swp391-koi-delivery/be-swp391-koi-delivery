package com.SWP391.KoiXpress.Entity;

import lombok.Data;


public enum OrderStatus {
    PendingConfirmation,
    OrderConfirmed,
    PackingInProgress,
    InTransit,
    Delivered
}
