package top.wang3.staffmanagement.service;

import top.wang3.staffmanagement.model.Staff;

import java.util.ArrayList;
import java.util.List;

public interface StaffService {
    List<Staff> getAllStaff();

    void deleteStaffByIds(ArrayList<Integer> ids);

    int updateStaff(Staff staff);

    int addStaff(Staff staff);
}
