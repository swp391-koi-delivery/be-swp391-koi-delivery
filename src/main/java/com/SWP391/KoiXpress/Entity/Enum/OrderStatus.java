package com.SWP391.KoiXpress.Entity.Enum;


public enum OrderStatus {

    //sau khi dat don
    PENDING,

    //user can not update
    AWAITING_RESPONSE,

    //role sale staff
    ACCEPTED,
    REJECTED,

    //don can duoc thanh toan
    AWAITING_PAYMENT,
    PAID,

    //huy don khi co su co
    CANCEL,

    //role delivery
    SHIPPING,
    DELIVERED
}
