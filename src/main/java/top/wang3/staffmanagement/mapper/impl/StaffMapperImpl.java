package top.wang3.staffmanagement.mapper.impl;

import top.wang3.staffmanagement.mapper.BaseMapper;
import top.wang3.staffmanagement.mapper.StaffMapper;
import top.wang3.staffmanagement.model.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffMapperImpl extends BaseMapper<Staff> implements StaffMapper {

    @Override
    public List<Staff> selectAllStaff() {
        String sql = "select * from t_staff";
        return super.getAll(sql);
    }

    @Override
    public void deleteByIds(ArrayList<Integer> ids) {
        StringBuilder sql = new StringBuilder("delete from t_staff where id in ");
        sql.append("(");
        int size = ids.size();
        for (int i = 0; i < size - 1; i++) {
            sql.append(ids.get(i)).append(",");
        }
        sql.append(ids.get(size - 1))
                .append(");");
        super.update(sql.toString());
    }

    @Override
    public int updateStaffById(Staff staff) {
        String sql = "update t_staff set name = ?, sex = ?, " +
                "phone = ?, department = ?, birth = ?, hire_time = ?, salary = ? where id = ?;";
        return update(sql, staff.getName(), staff.getSex(),
                staff.getPhone(), staff.getDepartment(),
                staff.getBirth(), staff.getHireTime(), staff.getSalary(),
                staff.getId());
    }

    @Override
    public int insertStaff(Staff staff) {
        String sql = "insert into t_staff(name, sex, phone, department, birth, hire_time, salary) " +
                "values(?, ?, ?, ?, ?, ?, ?);";
        return update(sql, staff.getName(), staff.getSex(),
                staff.getPhone(), staff.getDepartment(),
                staff.getBirth(), staff.getHireTime(), staff.getSalary());
    }
}
