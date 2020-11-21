package com.project.ecommerce.form;

import java.io.Serializable;
import java.util.List;

public class CartPageForm implements Serializable {
    private static final long serialVersionUID = -5801132642875541123L;
    private int productNum;
    private long totalListPrice;
    private long totalBill;
    private List<VendorProductForm> productList;
}
