package com.SWP391.KoiXpress.Entity.Enum;


public enum OrderStatus {

    //sau khi dat don
    Pending,

    //role sale staff
    Accepted,
    Rejected,

    //don can duoc thanh toan
    Awaiting_Payment,
    Paid,

    //huy don khi co su co
    Canceled,

    //role delivery
    Shipping,
    Delivered
}
