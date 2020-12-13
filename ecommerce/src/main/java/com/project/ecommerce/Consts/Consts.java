package com.project.ecommerce.Consts;

public class Consts {
    public static final String EMPTY = "";
    public static final Integer DEFAULT_VALUE_0 = 0;
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_VENDOR = "ROLE_VENDOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ORDER_STATUS_PENDING = "PENDING";
    public static final String ORDER_STATUS_PROGRESSING = "PROGRESSING";
    public static final String ORDER_STATUS_DELIVERING = "DELIVERING";
    public static final String ORDER_STATUS_SUCCESS = "SUCCESS";
    public static final String ORDER_STATUS_CANCELED = "CANCELED";

    public static final Integer IMG_ORDER_1 = 1;
    public static final Integer IMG_ORDER_2 = 2;
    public static final Integer IMG_ORDER_3 = 3;
//-----------------------------------------------------------------------------------------------
    public static final String MSG_01_E = "MSG_01_E"; //You can not buy more than {0} products.
    public static final String MSG_02_E = "MSG_02_E"; //DB error!
    public static final String MSG_03_E = "MSG_03_E"; //Set default address fail
    public static final String MSG_04_E = "MSG_04_E"; //Create order fail
    public static final String MSG_05_E = "MSG_05_E"; //Some products have changed quantity


//-----------------------------------------------------------------------------------------------
    public static final String MSG_01_I = "MSG_01_I"; //The product has been added to cart!
    public static final String MSG_02_I = "MSG_02_I"; //Address has been deleted successful!
    public static final String MSG_03_I = "MSG_03_I"; //Product has been removed successful!
    public static final String MSG_04_I = "MSG_04_I"; //Set default address successful!
    public static final String MSG_05_I = "MSG_05_I"; //Order {0} has been created successful!
}
