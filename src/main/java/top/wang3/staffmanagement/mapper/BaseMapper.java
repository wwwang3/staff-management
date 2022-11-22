package top.wang3.staffmanagement.mapper;


import top.wang3.staffmanagement.annotation.Column;
import top.wang3.staffmanagement.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体类和数据库表映射简单实现，并提供见得curd方法
 * @param <T> 实体类泛型
 */
public abstract class BaseMapper<T> {
    private Class<T> clazz;
    private Connection conn = null;
    {
        //获取当前BaseDAO的子类继承的父类中的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;

        Type[] typeArguments = paramType.getActualTypeArguments();//获取了父类的泛型参数
        clazz = (Class<T>) typeArguments[0];//泛型的第一个参数
    }

    protected BaseMapper() {

    }

    // 通用的增删改操作
    public int update(String sql, Object... args) {// sql中占位符的个数与可变形参的长度相同！
        PreparedStatement ps = null;
        try {
            // 1.预编译sql语句，返回PreparedStatement的实例
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 2.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);// 小心参数声明错误！！
            }
            // 3.执行
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4.资源的关闭
            JDBCUtils.closeResource(conn, ps);
        }
        return 0;
    }

    private ResultSet getResultSet(String sql, Object... args) throws Exception {
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 通用的查询操作，用于返回数据表中的一条记录
    private T getInstance(ResultSet rs) {
        if(rs == null){
            return null;
        }
        try {
            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            T t = clazz.getDeclaredConstructor().newInstance();
            // 处理结果集一行数据中的每一个列
            for (int i = 0; i < columnCount; i++) {
                // 获取列值
                Object columnValue = rs.getObject(i + 1);
                // 获取每个列的列名
                // String columnName = rsmd.getColumnName(i + 1);
                String columnLabel = rsmd.getColumnLabel(i + 1);
                //通过@Column注解获取映射的字段
                Field field = getMappedField(clazz, columnLabel);
                if (field != null) {
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据Column注解获取columnLabel映射的字段
     * @param clazz 实体类Class对象
     * @param columnLabel 列的别名
     * @return 如果找到返回此Field对象，否则返回null
     */
    private Field getMappedField(Class<T> clazz, String columnLabel) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                //没有使用@Column注解，使用普通映射
                if (field.getName().equalsIgnoreCase(columnLabel)) {
                    //字段名等于列的别名
                    return field;
                }
            } else {
                //获取映射的columnLabel
                String mappedValue = column.value();
                if (mappedValue.equalsIgnoreCase(columnLabel)) {
                    return field;
                }
            }
        }
        return null;
    }
    /**
     *
     * @param sql sql语句
     * @param args 条件
     * @return 返回一个bean实例
     */
    public T  getOne(String sql, Object...args) {
        ResultSet rs = null;
        try {
            rs = getResultSet(sql, args);
            assert rs != null;
            if(rs.next()) {
                return getInstance(rs);
            }
            return null;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, rs);
        }
        return null;
    }

    // 通用的查询操作，用于返回数据表中的多条记录构成的集合（version 2.0：考虑上事务）
    public List<T> getAll(String sql, Object... args) {
        ResultSet rs = null;
        try {
            rs = getResultSet(sql, args);
            if(rs == null) return null;
            // 创建集合对象
            ArrayList<T> list = new ArrayList<>();
            T instance;
            while (rs.next()) {
                if ((instance = getInstance(rs)) != null) {
                    list.add(instance);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, rs);
        }
        return null;
    }
    //用于查询特殊值的通用的方法
    public <E> E getValue(String sql, Object...args) throws Exception {
        ResultSet rs = null;
        try {
            rs = getResultSet(sql, args);
            if(rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null, rs);
        }
        return null;
    }

    //查询某个字段的多条数据
    public <E> List<E> getValues(String sql, Object...args){
        ResultSet rs = null;
        try {
            rs = getResultSet(sql, args);
            ArrayList<E> list = new ArrayList<>();
            while(rs.next()) {
                list.add((E) rs.getObject(1));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null, rs);
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
