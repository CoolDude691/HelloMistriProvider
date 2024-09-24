package com.hellomistri.hellomistriprovidern.Model;

public class RechargeModel {
    private String id;
    private String credit_amt;
    private String bonus;
    private String amt;
    private String status;




    public RechargeModel(String id, String credit_amt) {
        this.id = id;
        this.credit_amt = credit_amt;
    }


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getCredit_amt() {
        return credit_amt;
    }

    public String getBonus() {
        return bonus;
    }

    public String getAmt() {
        return amt;
    }

    public String getStatus() {
        return status;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setCredit_amt(String credit_amt) {
        this.credit_amt = credit_amt;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}