package top.wang3.staffmanagement.model;

import top.wang3.staffmanagement.annotation.Column;

public class Admin {
    Integer id;
    @Column("admin_name")
    String adminName;
    String password;

    public Admin() {
    }

    public Admin(Integer id, String adminName, String password) {
        this.id = id;
        this.adminName = adminName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
