package ro.iugori.ks2sb.domain;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"), name = "Member")
public class MemberEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 12)
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

