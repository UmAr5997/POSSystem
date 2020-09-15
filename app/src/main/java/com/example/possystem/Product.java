package com.example.possystem;

public class Product {
    private String pname;
    private String semail;
    private String pprice;
    private String qty;
    private String cotonqty;
    private String cotonprice;
    private String category;
    private String brand;
    private String imageid;

    public Product(){
        //this constructor is required
    }

    public Product(String imageid,String category, String brand,String pname, String semail, String pprice,String qty,String cotonqty,String cotonprice) {
        this.imageid=imageid;
        this.category = category;
        this.brand = brand;
        this.pname = pname;
        this.semail = semail;
        this.pprice = pprice;
        this.qty=qty;
        this.cotonqty=cotonqty;
        this.cotonprice=cotonprice;
    }

    public String getPname() {
        return pname;
    }

    public String getSemail() {
        return semail;
    }

    public String getPprice() {
        return pprice;
    }
    public String getQty() {
        return qty;
    }

    public String getCotonqty() {
        return cotonqty;
    }

    public String getCotonprice() {
        return cotonprice;
    }

    public void setCotonprice(String cotonprice) {
        this.cotonprice = cotonprice;
    }

    public String getCategory() {
        return category;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setCotonqty(String cotonqty) {
        this.cotonqty = cotonqty;
    }
}

