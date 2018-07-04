package com.whoiszxl.entity;

public class Sku {
    private Integer id;

    private Integer productId;

    private String productSkuTitle;

    private String productSkuContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductSkuTitle() {
        return productSkuTitle;
    }

    public void setProductSkuTitle(String productSkuTitle) {
        this.productSkuTitle = productSkuTitle == null ? null : productSkuTitle.trim();
    }

    public String getProductSkuContent() {
        return productSkuContent;
    }

    public void setProductSkuContent(String productSkuContent) {
        this.productSkuContent = productSkuContent == null ? null : productSkuContent.trim();
    }
}