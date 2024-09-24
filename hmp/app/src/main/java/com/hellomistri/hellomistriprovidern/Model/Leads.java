package com.hellomistri.hellomistriprovidern.Model;

import java.io.Serializable;

    public class Leads  implements Serializable {
    private   String id;
    private     String customer_id;
     private    String provider_id;
     private    String c_atname;
     private    String sub_cat_name;
     private    String service_name;
     private    String city_name;
      private   String message;
      private   String or_date;
      private String o_status;
      private   String or_address;

      private  String name;
      private  String pame;
      private  String cat_name;
      private  String cname;

        private String  lattitude;

        private String  longitude;


        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }


        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }



        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        private  String mobile;
      private  String subtitle;
      private  String childtitle;

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getChildtitle() {
            return childtitle;
        }

        public void setChildtitle(String childtitle) {
            this.childtitle = childtitle;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPame() {
            return pame;
        }

        public void setPame(String pame) {
            this.pame = pame;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public Leads(String id, String customer_id, String provider_id, String c_atname, String sub_cat_name, String service_name, String city_name, String message, String or_date, String o_status, String or_address,String lattitude,String longitude) {
            this.id = id;
            this.customer_id = customer_id;
            this.provider_id = provider_id;
            this.c_atname = c_atname;
            this.sub_cat_name = sub_cat_name;
            this.service_name = service_name;
            this.city_name = city_name;
            this.message = message;
            this.or_date = or_date;
            this.o_status = o_status;
            this.or_address = or_address;
            this.lattitude = lattitude;
            this.longitude = longitude;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getProvider_id() {
            return provider_id;
        }

        public void setProvider_id(String provider_id) {
            this.provider_id = provider_id;
        }

        public String getC_atname() {
            return c_atname;
        }

        public void setC_atname(String c_atname) {
            this.c_atname = c_atname;
        }

        public String getSub_cat_name() {
            return sub_cat_name;
        }

        public void setSub_cat_name(String sub_cat_name) {
            this.sub_cat_name = sub_cat_name;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getOr_date() {
            return or_date;
        }

        public void setOr_date(String or_date) {
            this.or_date = or_date;
        }

        public String getO_status() {
            return o_status;
        }

        public void setO_status(String o_status) {
            this.o_status = o_status;
        }

        public String getOr_address() {
            return or_address;
        }

        public void setOr_address(String or_address) {
            this.or_address = or_address;
        }
    }