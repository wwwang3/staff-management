package top.wang3.staffmanagement.mapper.impl;

import top.wang3.staffmanagement.mapper.AdminMapper;
import top.wang3.staffmanagement.mapper.BaseMapper;
import top.wang3.staffmanagement.model.Admin;

public class AdminMapperImpl extends BaseMapper<Admin> implements AdminMapper {

    @Override
    public Admin selectAdminByName(String name) {
        String sql = "select * from t_admin where admin_name = ?;";
        return getOne(sql, name);
    }
}
