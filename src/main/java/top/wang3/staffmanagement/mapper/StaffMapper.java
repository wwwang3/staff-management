package top.wang3.staffmanagement.mapper;

import top.wang3.staffmanagement.model.Staff;

import java.util.ArrayList;
import java.util.List;

public interface StaffMapper {

    List<Staff> selectAllStaff();

    void deleteByIds(ArrayList<Integer> ids);

    int updateStaffById(Staff staff);

    int insertStaff(Staff staff);
}
