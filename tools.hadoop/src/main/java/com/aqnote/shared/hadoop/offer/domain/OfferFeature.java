/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer.domain;

public class OfferFeature {

    private Integer fid;
    private String  name;
    private String  value;
    private String  fieldFlag;
    private String  type;
    private String  unit;
    private boolean isNeed;
    private String  sign;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFieldFlag() {
        return fieldFlag;
    }

    public void setFieldFlag(String fieldFlag) {
        this.fieldFlag = fieldFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean getIsNeed() {
        return isNeed;
    }

    public void setIsNeed(boolean isNeed) {
        this.isNeed = isNeed;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String toString() {
        return "fid:" + this.fid + ",fieldFlag:" + this.fieldFlag + ",name:" + this.name + ",type:" + this.type
               + ",unit:" + this.unit + ",value:" + this.value + ",isNeed:" + this.isNeed;
    }

    public static final class FIELD_FLAG {

        public static final String BEGINAMOUNT  = "beginamount";

        public static final String PRICE        = "price";

        public static final String PROVIDERTYPE = "providertype";

        public static final String SENDLIMIT    = "sendlimit";

        public static final String AMOUNT       = "amount";

        public static final String UNIT         = "unit";

        public static final String PRICERANGE   = "pricerange";

        public static final String RETAILPRICE  = "retailprice";

        public static final String PAYMENT      = "payment";

        public static final String SUPPLYMODE   = "supplymode";

        public static final String TAX          = "tax";

        public static final String DYFLAG       = "dyFlag";

        public static final String DYMINAMT     = "dyMinAmt";

        public static final String DYEXPENSE    = "dyExpense";

        public static final String MIXBEGIN     = "mixbegin";

    }
}
