package com.hellomistri.hellomistriprovidern.Model;

import java.io.Serializable;

public class LeadsCancel  implements Serializable {
    private String id;
    private String uid;
    private String cid;
    private String odate;
    private String p_method_id;
    private String address;
    private String city;
    private String landmark;
    private String pincode;
    private String o_total;
    private String subtotal;
    private String trans_id = null;
    private String o_status;
    private String a_status;
    private String rid;
    private String comment_reject = null;
    private String add_on;
    private String add_per_price;
    private String add_total = null;
    private String time;
    private String date;
    private String r_credit;
    private String lats;
    private String longs;
    private String wal_amt;
    private String pcommission;
    private String htype;
    private String add_desc;
    private String add_price;
    private String p_id;
    private String t_id;
    private String services_cat;
    private String name;

    private String mobile;

    public LeadsCancel(String id, String address, String odate, String time, String o_status,String services_cat,String add_desc,String uid) {

        this.id=id;
        this.address = address;
        this.odate = odate;
        this.time = time;
        this.o_status = o_status;
        this.services_cat = services_cat;
        this.add_desc = add_desc;
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }








    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


// Getter Methods

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getCid() {
        return cid;
    }

    public String getOdate() {
        return odate;
    }

    public String getP_method_id() {
        return p_method_id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public String getO_total() {
        return o_total;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public String getO_status() {
        return o_status;
    }

    public String getA_status() {
        return a_status;
    }

    public String getRid() {
        return rid;
    }

    public String getComment_reject() {
        return comment_reject;
    }

    public String getAdd_on() {
        return add_on;
    }

    public String getAdd_per_price() {
        return add_per_price;
    }

    public String getAdd_total() {
        return add_total;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getR_credit() {
        return r_credit;
    }

    public String getLats() {
        return lats;
    }

    public String getLongs() {
        return longs;
    }

    public String getWal_amt() {
        return wal_amt;
    }

    public String getPcommission() {
        return pcommission;
    }

    public String getHtype() {
        return htype;
    }

    public String getAdd_desc() {
        return add_desc;
    }

    public String getAdd_price() {
        return add_price;
    }

    public String getP_id() {
        return p_id;
    }

    public String getT_id() {
        return t_id;
    }

    public String getServices_cat() {
        return services_cat;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public void setP_method_id(String p_method_id) {
        this.p_method_id = p_method_id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setO_total(String o_total) {
        this.o_total = o_total;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public void setO_status(String o_status) {
        this.o_status = o_status;
    }

    public void setA_status(String a_status) {
        this.a_status = a_status;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setComment_reject(String comment_reject) {
        this.comment_reject = comment_reject;
    }

    public void setAdd_on(String add_on) {
        this.add_on = add_on;
    }

    public void setAdd_per_price(String add_per_price) {
        this.add_per_price = add_per_price;
    }

    public void setAdd_total(String add_total) {
        this.add_total = add_total;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setR_credit(String r_credit) {
        this.r_credit = r_credit;
    }

    public void setLats(String lats) {
        this.lats = lats;
    }

    public void setLongs(String longs) {
        this.longs = longs;
    }

    public void setWal_amt(String wal_amt) {
        this.wal_amt = wal_amt;
    }

    public void setPcommission(String pcommission) {
        this.pcommission = pcommission;
    }

    public void setHtype(String htype) {
        this.htype = htype;
    }

    public void setAdd_desc(String add_desc) {
        this.add_desc = add_desc;
    }

    public void setAdd_price(String add_price) {
        this.add_price = add_price;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public void setServices_cat(String services_cat) {
        this.services_cat = services_cat;
    }
}