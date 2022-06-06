package com.example.bigfamilyv20.Entities;

import java.util.List;

public class wholesale_bulk {
    private static List<Double> Wprice;
    private static List<Integer>wbulk_amounts;
    private static List<String>BdocumentIds,Bnames,Bdescription,Bprice;
    private static List<Long>BprodId;
    private static List<Integer>Bamount;

    public wholesale_bulk() {
    }

    public static List<String> getBdocumentIds() {
        return BdocumentIds;
    }

    public static void setBdocumentIds(List<String> bdocumentIds) {
        BdocumentIds = bdocumentIds;
    }

    public static List<String> getBnames() {
        return Bnames;
    }

    public static void setBnames(List<String> bnames) {
        Bnames = bnames;
    }

    public static List<String> getBdescription() {
        return Bdescription;
    }

    public static void setBdescription(List<String> bdescription) {
        Bdescription = bdescription;
    }

    public static List<String> getBprice() {
        return Bprice;
    }

    public static void setBprice(List<String> bprice) {
        Bprice = bprice;
    }

    public static List<Long> getBprodId() {
        return BprodId;
    }

    public static void setBprodId(List<Long> bprodId) {
        BprodId = bprodId;
    }

    public static List<Integer> getBamount() {
        return Bamount;
    }

    public static void setBamount(List<Integer> bamount) {
        Bamount = bamount;
    }

    public static List<Double> getWprice() {
        return Wprice;
    }

    public static void setWprice(List<Double> wprice) {
        Wprice = wprice;
    }

    public static List<Integer> getWbulk_amounts() {
        return wbulk_amounts;
    }

    public static void setWbulk_amounts(List<Integer> wbulk_amounts) {
        wholesale_bulk.wbulk_amounts = wbulk_amounts;
    }
}
