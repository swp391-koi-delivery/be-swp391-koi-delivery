package com.SWP391.KoiXpress.Entity.Enum;



public enum Role {
    MANAGER("MGR"),
    SALESSTAFF("SLE"),
    DELIVERINGSTAFF("DLV"),
    CUSTOMER("CTS");

    private final String code;

    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Role fromUsername(String username) {
        for (Role role : Role.values()) {
            if (username.startsWith(role.getCode())) {
                return role;
            }
        }
        throw new IllegalArgumentException("No matching role for username: " + username);
    }
}
