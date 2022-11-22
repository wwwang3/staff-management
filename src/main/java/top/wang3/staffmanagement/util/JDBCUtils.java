package top.wang3.staffmanagement.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties properties = new Properties();
            //加载配置
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("mysql.properties");
            properties.load(is);
            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.user");
            password = properties.getProperty("jdbc.password");
            String driver = properties.getProperty("jdbc.driverClass");
            //注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Connection对象
     * @throws Exception Exception
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }


    /**
     * 关闭连接
     * @param conn Connection对象
     * @param ps Statement对象
     */
    public static void closeResource(Connection conn, Statement ps){
        try {
            //先关闭Statement
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param conn Connection对象
     * @param statement Statement对象
     * @param resultSet ResultSet对象
     */
    public static void closeResource(Connection conn, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取实体类对象
     * @param resultSet ResultSet对象
     * @param clazz 实体类的Class对象
     * @param <T> 泛型
     * @return 实体对象实例
     * @throws Exception Exception
     */
    public static <T> T get(ResultSet resultSet, Class<T> clazz) throws Exception {
        if (resultSet == null || clazz == null) return null;
        T entity = null;
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        if (resultSet.next()) {
            entity = clazz.getDeclaredConstructor().newInstance();
            for (int i = 0; i < columnCount; i++) {
                //获取列的别名，若执行查询时没有指定列的别名，则和列名一致
                //列名或者列的别名必须和实体类的字段名一致，否则会出现错误
                //注意这里列的索引从1开始
                String columnLabel = rsmd.getColumnLabel(i + 1);
                Object columnVal = resultSet.getObject(columnLabel);
                //通过反射获取实体类的字段对象
                Field field = clazz.getDeclaredField(columnLabel);
                //如果字段是私有的，将之设置为可访问
                field.setAccessible(true);
                field.set(entity, columnVal);
            }
        }
        return entity;
    }

}
