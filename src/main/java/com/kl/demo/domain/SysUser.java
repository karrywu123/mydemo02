package com.kl.demo.domain;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 账户实体(在正式进行Spring Security中配置时候，该实体需要进一步设完善)
 */
@Entity
@Table(name = "sysuser")
public class SysUser {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    @Column(length = 32)
    private String uuid;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String useremail;

    @Column(length = 100)
    private String usermobile;

    @Column(length = 30)
    private String sysrolename;

    @Column(length = 32)
    private String sysroleid;

    //referencedColumnName对应的是关联表对应的列
    @OneToOne
    @JoinColumn(name="sysroleid",referencedColumnName="uuid",insertable = false, updatable = false)
    private SysRole sysRole;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getSysrolename() {
        return sysrolename;
    }

    public void setSysrolename(String sysrolename) {
        this.sysrolename = sysrolename;
    }

    public String getSysroleid() {
        return sysroleid;
    }

    public void setSysroleid(String sysroleid) {
        this.sysroleid = sysroleid;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }
}
