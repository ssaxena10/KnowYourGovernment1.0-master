package com.example.sharul.knowyourgovernment10;

import java.io.Serializable;

/**
 * Created by Sharul on 30-03-2017.
 */

public class Official implements Serializable {

    private String office;
    private String name;
    private String party;
    private String OfficeAdd;
    private String phone;
    private String email;
    private String website;
    private String phurl;
    private String channel;


    public Official()
    {

    }
    public Official(String o, String n, String p, String a, String ph, String e, String w, String purl, String ch) {

        office = o;
        name = n;
        party = p;
        OfficeAdd = a;
        phone = ph;
        email = e;
        website = w;
        phurl = purl;
        channel = ch;
    }
    public String getPhurl() {
        return phurl;
    }

    public void setPhurl(String phurl) {
        this.phurl = phurl;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getOfficeAdd() {
        return OfficeAdd;
    }

    public void setOfficeAdd(String officeAdd) {
        OfficeAdd = officeAdd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
