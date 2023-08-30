package com.logicea.cards.helper.dto;

import java.util.List;

public class QuantityNotificationDTO {
    private String branchName;
    private String merchantName;
    private List<String> branchNotificationEmails;
    private List<String> merchantNotificationEmails;
    private List<ProductQtyNotificationDetails> productQtyNotificationDetailsList;

    public String getBranchName() {
        return branchName;
    }

    public QuantityNotificationDTO setBranchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public QuantityNotificationDTO setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public List<String> getBranchNotificationEmails() {
        return branchNotificationEmails;
    }

    public QuantityNotificationDTO setBranchNotificationEmails(List<String> branchNotificationEmails) {
        this.branchNotificationEmails = branchNotificationEmails;
        return this;
    }

    public List<String> getMerchantNotificationEmails() {
        return merchantNotificationEmails;
    }

    public QuantityNotificationDTO setMerchantNotificationEmails(List<String> merchantNotificationEmails) {
        this.merchantNotificationEmails = merchantNotificationEmails;
        return this;
    }

    public List<ProductQtyNotificationDetails> getProductQtyNotificationDetailsList() {
        return productQtyNotificationDetailsList;
    }

    public QuantityNotificationDTO setProductQtyNotificationDetailsList(List<ProductQtyNotificationDetails> productQtyNotificationDetailsList) {
        this.productQtyNotificationDetailsList = productQtyNotificationDetailsList;
        return this;
    }

    public static class ProductQtyNotificationDetails {
        private String productName;
        private Double threshold;
        private Double availableQty;
        private String unit;

        public ProductQtyNotificationDetails() {
        }

        public String getProductName() {
            return productName;
        }

        public ProductQtyNotificationDetails setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Double getThreshold() {
            return threshold;
        }

        public ProductQtyNotificationDetails setThreshold(Double threshold) {
            this.threshold = threshold;
            return this;
        }

        public Double getAvailableQty() {
            return availableQty;
        }

        public ProductQtyNotificationDetails setAvailableQty(Double availableQty) {
            this.availableQty = availableQty;
            return this;
        }

        public String getUnit() {
            return unit;
        }

        public ProductQtyNotificationDetails setUnit(String unit) {
            this.unit = unit;
            return this;
        }
    }
}
