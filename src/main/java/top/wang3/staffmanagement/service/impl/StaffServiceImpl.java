package top.wang3.staffmanagement.service.impl;


import top.wang3.staffmanagement.mapper.StaffMapper;
import top.wang3.staffmanagement.mapper.impl.StaffMapperImpl;
import top.wang3.staffmanagement.model.Staff;
import top.wang3.staffmanagement.service.StaffService;

import java.util.ArrayList;
import java.util.List;

public class StaffServiceImpl implements StaffService {

    private final StaffMapper staffMapper = new StaffMapperImpl();

    @Override
    public List<Staff> getAllStaff() {
        return staffMapper.selectAllStaff();
    }

    @Override
    public void deleteStaffByIds(ArrayList<Integer> ids) {
        staffMapper.deleteByIds(ids);
    }

    @Override
    public int updateStaff(Staff staff) {
        if (staff == null || staff.getId() == null) return 0;
        return staffMapper.updateStaffById(staff);
    }

    @Override
    public int addStaff(Staff staff) {
        if (staff == null) return 0;
        return staffMapper.insertStaff(staff);
    }
}
