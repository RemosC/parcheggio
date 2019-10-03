package org.remo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="customers")
public class Customer extends BaseModel {

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, length = 16, nullable = false)
    private String ssn;

    @Column(unique = true)
    private String email;

    @Column(name="is_obsolete")
    private boolean isObsolete;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Address> addressList;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean obsolete) {
        isObsolete = obsolete;
    }
}
