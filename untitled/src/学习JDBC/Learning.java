package 学习JDBC;

import org.junit.Test;
import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

class customers { // customers表的数据对象
    private int id;
    private String name;
    private String email;
    private Date birth;

    public customers() {
    }

    public customers(int id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "customers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}

public class Learning {
    @Test
    public void test() throws Exception { // 学习修改数据
        Connection connection = JDBCUtils.getConnection();
        String sql = "update customers set name = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, "杨一脚");
        ps.setObject(2, 18);
        ps.execute();
        JDBCUtils.closeResource(connection, ps);
        // 上面操作用update函数完成
        update("update customers set name = ? where id = ?", "杨亦足", 18);
    }

    // 通用的增删改查操作.
    public void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            for (int i = 1; i <= args.length; i++) { // 填充占位符
                ps.setObject(i, args[i - 1]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, ps);
        }
    }

    @Test
    public void test2() throws Exception { // 学习查询数据
        Connection connection = JDBCUtils.getConnection();
        String sql = "select id,name,email,birth from customers where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, 18);
        ResultSet resultSet = ps.executeQuery(); // 获取结果集
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            Date birth = resultSet.getDate(4);
            customers customers = new customers(id, name, email, birth);
            System.out.println(customers); // customers{id=18, name='杨亦足', email='beidf@126.com', birth=2014-01-17}
        }
        JDBCUtils.closeResource(connection, ps, resultSet);
        customers select = select("select id,name,email,birth from customers where id = ?", 1);
        System.out.println(select);
    }

    // 通用的查询表的操作
    public customers select(String sql, Object... args) throws Exception {
        Connection connection = JDBCUtils.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i = 1; i <= args.length; i++) { // 填充占位符
            ps.setObject(i, args[i - 1]);
        }
        ResultSet resultSet = ps.executeQuery(); // 获取结果集
        ResultSetMetaData metaData = resultSet.getMetaData(); // 获取元数据
        int columnCount = metaData.getColumnCount(); // 获取一行数据的列数
        if (resultSet.next()) {
            customers customers = new customers();
            for (int i = 1; i <= columnCount; i++) {
                Object value = resultSet.getObject(i); // 获取第i列的值
                String name = metaData.getColumnName(i); // 获取这个列的名字
                Field field = customers.class.getDeclaredField(name);
                field.setAccessible(true);
                field.set(customers, value); // 将对象的该属性设置上value
            }
            return customers;
        }
        JDBCUtils.closeResource(connection, ps, resultSet);
        return null;
    }

}
