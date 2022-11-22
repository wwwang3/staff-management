package top.wang3.staffmanagement.model;

import top.wang3.staffmanagement.annotation.Column;

import java.util.Date;

/**
 * Table t_staff 对应的实体信息
 */
public class Staff {

    private Integer id;
    private String name;
    private String sex;
    private String phone;
    private String department;
    private Date birth;
    @Column("hire_time")
    private Date hireTime;
    private Double salary;

    public Staff() {

    }

    public Staff(Integer id, String name, String sex, String phone,
                 String department,
                 Date birth,
                 Date hireTime,
                 Double salary) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
        this.birth = birth;
        this.hireTime = hireTime;
        this.salary = salary;
    }

    public Staff(String name, String sex, String phone, String department,
                 Date birth, Date hireTime, Double salary) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
        this.birth = birth;
        this.hireTime = hireTime;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getHireTime() {
        return hireTime;
    }

    public void setHireTime(Date hireTime) {
        this.hireTime = hireTime;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                ", birth=" + birth +
                ", hireTime=" + hireTime +
                ", salary=" + salary +
                '}';
    }
}
