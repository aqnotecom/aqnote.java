/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.hadoop.offer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqnote.shared.hadoop.offer.domain.OfferFeature;

/**
 * 类MdpParseUtil.java的实现描述：TODO 类实现描述 
 * @author madding.lip Jun 28, 2012 1:22:32 PM
 */
public class MdpParseUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(MdpParseUtil.class);
    
    public static Double[] getPricerange(String mdp) throws Exception {

        List<OfferFeature> ofl = parse(mdp);
        
        if(ofl == null) {
            return null;
        }
        
        Double[] price = new Double[3];
        boolean hasPricerange = false; 
        for(OfferFeature of : ofl) {
            if(of == null) {
                continue;
            }
            if(OfferFeature.FIELD_FLAG.PRICERANGE.equals(of.getFieldFlag())) {
               
               String pricerange = of.getValue();
               if(pricerange == null) {
                   break;
               }
               
               hasPricerange = true;
               
               String[] priceStrArray = pricerange.split(";");
               
               if(priceStrArray.length > 4) {
                   break;
               }
               
               for(int i=0; i< priceStrArray.length; i++) {
                   price[i] = parseDobuleNE(priceStrArray[i]);
               }
               
               if(priceStrArray.length==0) {
                   return null;
               } else if(priceStrArray.length==1) {
                   price[1]=price[0];
               } else if(priceStrArray.length==2) {
                   double max = price[0] > price[1]?price[0]:price[1];
                   double min = price[0] < price[1]?price[0]:price[1];
                   price[0] = min;
                   price[1] = max;
               } else  {               
                   double max = price[0] > price[1] ? price[0] : price[1];
                   max = max > price[2] ? max : price[2];
                   
                   double min = price[0] < price[1] ? price[0] : price[1];
                   min = min < price[2] ? min : price[2];
                   
                   price[0] = min;
                   price[1] = max;
               }
            }
        }
        
        if(hasPricerange) {
            return price;
        } else {
            return null;
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public static List<OfferFeature> parse(String mdp) throws Exception {
        if (mdp == null || mdp.isEmpty()) {
            return null;
        }

        String offerProp = mdp.trim();
        offerProp = offerProp.replace(String.valueOf((char)6), "");
        
        try {
            List<OfferFeature> offerFeatureList = new ArrayList<OfferFeature>();
            Document doc = DocumentHelper.parseText(offerProp);
            Element root = doc.getRootElement();
            for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
                OfferFeature offerFeature = new OfferFeature();

                Element element = i.next();

                Attribute fid = element.attribute("fid");
                Attribute name = element.attribute("name");
                Attribute value = element.attribute("value");
                Attribute unit = element.attribute("unit");
                Attribute fieldflag = element.attribute("fieldflag");
                Attribute fieldType = element.attribute("type");
                Attribute isneeded = element.attribute("isneeded");

                if (fid != null) {
                    offerFeature.setFid(parseIntNE(fid.getValue()));
                }
                if (name != null) {
                    offerFeature.setName(name.getValue());
                }

                if (value != null) {
                    offerFeature.setValue(value.getValue());
                }
                
                if (unit != null) {
                    offerFeature.setUnit(unit.getValue());
                }
                
                if (fieldflag != null) {
                    offerFeature.setFieldFlag(fieldflag.getValue());
                }
                
                if (fieldType != null) {
                    offerFeature.setType(fieldType.getValue());
                }
                
                if (isneeded != null) {
                    offerFeature.setIsNeed("Y".equals(isneeded.getValue()));
                }
                
                String pricerangeValue = "";
                if (OfferFeature.FIELD_FLAG.PRICERANGE.equals(offerFeature.getFieldFlag())) {
//                    String begin = element.attributeValue("b");
//                    String end = element.attributeValue("e");
//                    if (begin != null) {
//                        pricerangeValue = begin + "," ;
//                    }
//                    if (end != null) {
//                        pricerangeValue += end + ",";
//                    }
                    pricerangeValue += offerFeature.getValue() + ";";
                    
                    List<Element> evs = element.selectNodes("v");
                    for (int k = 0; evs != null && k < evs.size(); k++) {
                        Element e = evs.get(k);
//                        String ibegin = e.attributeValue("b");
//                        String iend = e.attributeValue("e");
//                        if (ibegin != null) {
//                            pricerangeValue += ibegin + ",";
//                        }
//                        if (iend != null) {
//                            pricerangeValue += iend + ",";
//                        }
                        pricerangeValue += e.getText() + ";";
                    }
                    offerFeature.setValue(pricerangeValue);
                }
                offerFeatureList.add(offerFeature);
            }
            return offerFeatureList;
        } catch (Exception e) {
            logger.error("mdp error: ", e);
            throw e;
        }
    }
    
    public static int parseIntNE(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
        }
        return 0;
    }
    
    public static double parseDobuleNE(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
        }
        return 0;
    }
}
