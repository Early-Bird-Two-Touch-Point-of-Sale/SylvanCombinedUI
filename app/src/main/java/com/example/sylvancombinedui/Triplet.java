package com.example.sylvancombinedui;

public class Triplet {
    private String string1, string3;
    private Double double2;

    public Triplet(String s1, Double d2, String s3){
        string1 = s1;
        double2 = d2;
        string3 = s3;
    }

    public String first(){
        return string1;
    }

    public Double second(){
        return double2;
    }

    public String third(){
        return string3;
    }
}
