package com.hellomistri.hellomistriprovidern.Model;



public class ModelWallet {
    private String id;
    private String p_id;
    private String o_id;
    private String p_mode;
    private String tid;
    private String status;
    private String amount;
    private String acredit;
    private String date;

    public ModelWallet(String id, String p_id, String p_mode, String status, String tid, String amount, String acredit, String date) {

        this.id= id;
        this.p_id = p_id;
        this.p_mode = p_mode;
        this.status = status;
        this.tid = tid;
        this.amount = amount;
        this.acredit = acredit;
        this.date = date;

    }


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getP_id() {
        return p_id;
    }

    public String getO_id() {
        return o_id;
    }

    public String getP_mode() {
        return p_mode;
    }

    public String getTid() {
        return tid;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public String getAcredit() {
        return acredit;
    }

    public String getDate() {
        return date;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public void setP_mode(String p_mode) {
        this.p_mode = p_mode;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAcredit(String acredit) {
        this.acredit = acredit;
    }

    public void setDate(String date) {
        this.date = date;
    }
}