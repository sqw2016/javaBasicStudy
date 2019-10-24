import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

/**
 * 数据库：
 *      概念：
 *          数据库系统是由数据库、数据库管理系统和应用系统、数据库管理员构成的。
 *          数据库是一种存储结构，允许使用各种格式输入、处理和检索数据。
 *          DBMS：数据库管理系统，包括数据库定义、数据查询、数据维护等。
 *          JDBC：连接数据库与应用的技术。
 *      1、数据库特点：
 *          1、数据共享：用户可以同时存取数据库中的数据，也可以用各种方式通过接口使用数据；
 *          2、减少数据冗余度：与文件系统相比，避免了用户各自建立应用文件，减少了大量重复数据；
 *          3、独立性：数据库的逻辑结构和应用程序相互独立；
 *          4、集中控制：对数据进行集中控制和管理，并通过数据模型表示各种数据的组织以及数据间的联系；
 *          5、数据的一致性和可维护性，确保数据的安全性和可靠性：
 *              1、安全性控制，防止数据丢失、错误更新和越权使用；
 *              2、完整性控制，保证数据的正确性、有效性和相容性；
 *              3、并发控制，在同一时间周期内，即允许对数据实现多路存取，又能防止用户之间的不正常交互作用
 *              4、故障的发现和恢复。
 *      2、数据库的基本结构：
 *          1、物理数据层：数据库的最内层，是物理存储设备上实际存储的数据集合，由内部模式描述的指令操作处理的
 *              字符和字组成；
 *          2、概念数据层：数据库的中间一层，是数据库的整体逻辑表示，指出了每个数据的逻辑定义及数据间的逻辑联系，
 *              是存储记录的集合，所涉及的是数据库所有对象的逻辑关系；
 *          3、逻辑数据层：用户看到和使用的数据库，是一个或一些特定用户使用的数据集合，即逻辑记录的集合。
 *      3、数据库的种类及功能：
 *          1、层次型数据库：类似树形结构，是一组通过连接而相互联系在一起的记录。特点是记录之间的联系通过指针实现。
 *              层次模型层次顺序严格而且复杂，因此对数据的各项操作都很困难；
 *          2、网状数据库：使用网络结构表示实体类型、实体间联系。特点是容易实现多对多的联系，但在编写引用程序时必须
 *              熟悉数据库的逻辑结构；
 *          3、面向对象型数据库：建立在面向对象模型基础上；
 *          4、关系型数据库：基于关系模型建立的数据库，关系模型是由一系列表格组成的。是目前最流行的数据库。
 *              1、MySql：目前最流行的数据库，具有功能强，使用简便，管理方便，运行速度快，安全可靠性强等优点。是完全
 *                  网络化的跨平台关系型数据库系统。
 *                  1、Derby：java自带的DBMS，位于JDK安装目录下的db目录下。
 *      4、SQL语言：
 *          1、组成部分：
 *              1、数据定义语言（Data Definition Language， DDL）：如create、alter、drop等；
 *              2、数据操作语言（Data Manipulation Language, DML）：如select、insert、update、delete等；
 *              3、数据控制语言（Data Control Language， DCL）：如grant、revoke等；
 *              4、事务控制语言（Transaction Control Language）：如commit、rollback等。
 *      5、JDBC：是一种用于执行SQL语句的Java API，用于连接数据库和java应用程序
 *          1、JDBC-ODBC桥：一个JDBC的驱动程序，完成从JDBC到ODBC操作之间的转换工作。现已不被广泛使用。
 *          2、JDBC：Java DataBase Connectivity，是一套面向对象的应用程序接口，指定了统一的访问各种关系型数据库的
 *              标准接口。JDBC是一种底层API，在访问数据库时需要在业务逻辑层中嵌入SQL语句。SQL语句是关系的，依赖
 *              关系模型，因此通过JDBC技术访问数据库也是面向关系的。JDBC并不能直接访问数据库，需要依赖数据库厂商提供
 *              的JDBC驱动程序。
 *                  1、JDBC总体结构：应用程序、驱动程序管理器、驱动程序和数据源
 *                  2、JDBC主要任务：
 *                      1、与数据库建立一个连接；
 *                      2、向数据库发送SQL语句；
 *                      3、处理从数据库返回结果。
 *                  3、JDBC驱动程序：
 *                      1、JDBC-ODBC桥：依靠ODBC驱动器和数据库通信。必须将ODBC二进制代码加载到使用驱动的客户机上，
 *                          适合于企业网或者用java编写的三层结构的应用程序服务器代码；
 *                      2、本地API一部分用java编写的驱动程序：这类驱动将客户机的API上的JDBC调用转换为Oracle、
 *                          DB2、Sybase或其他DBMS的调用。也需要将某些二进制代码加载到客户机上；
 *                      3、JDBC网路驱动：将JDBC转换为与DBMS无关的网络协议，又被某个服务器转换为一种DBMS协议，是
 *                          一种利用java编写的JDBC驱动程序，也是最灵活的JDBC驱动程序。适合于企业内部互联网用的产品，
 *                          需要处理Web提出的安全性、通过防火墙的访问等额外的要求；
 *                      4、本地协议驱动：纯java的驱动程序。将JDBC调用直接转换为DBMS所使用的网络协议，允许从客户机
 *                          上直接调用DBMS服务器。
 *          3、JDBC中常用的类和接口：
 *              1、Connection接口：代表与特定的数据库的连接，在连接上下文中执行SQL语句并返回结果。
 *                  1、createStatement()：创建Statement对象
 *                  2、createStatement(int resultSetType, int resultSetConcurrency)：创建一个Statement对象，改对象
 *                      将生成具有给定类型、并发性和可保存性的ResultSet对象
 *                  3、preparedStatement()：创建预处理对象preparedStatement
 *                  4、isReadOnly()：查看当前Connection对象是否为只读形式
 *                  5、setReadOnly()：将当前Connection对象设置为只读模式
 *                  6、commit()：是所有上一次提交/回滚后进行的更改成为持久更改，并释放此Connection对象当前持有的所有
 *                      数据库锁
 *                  7、rollback()：取消当前事务汇总进行的所有更改，并释放此Connection对象当前持有的所有数据库锁
 *                  8、close()：立即释放此Connection对象的数据库和JDBC资源。
 *              2、Statement接口：用于在已经建立连接的基础上向数据库发送SQL语句。
 *                  1、JDBC中有3种Statement对象：
 *                      1、Statement：用于执行不带参数的简单SQL语句；
 *                      2、PreparedStatement：继承了Statement，用于执行动态的SQL语句；
 *                      3、CallableStatement：继承了PreparedStatement，用于执行对数据库的存储过程的调用。
 *                  2、常用方法：
 *                      1、execute(String sql)：执行静态的select语句，可能返回多个结果集；
 *                      2、executeQuery(String sql)：执行给定的SQL语句，返回单个ResultSet对象；
 *                      3、clearBatch()：清空此Statement对象的当前SQL命令列表；
 *                      4、executeBatch()：将一批命令提交给数据库来执行，如果命令全部执行成功，则返回
 *                          结果数组，数组元素的排列顺序与SQL的添加顺序对应；
 *                      5、addBatch(String sql)：将给定的sql语句添加在Statement对象的命令列表中，如果驱动程序不支持
 *                          批量处理，将抛出异常；
 *                      6、close()：释放Statement实例占用的数据库和JDBC资源。
 *              3、PreparedStatement接口：通过PreparedStatement实例执行的动态SQL语句，将被预编译并保存到
 *                  PreparedStatement实例中，从而可以反复地执行该SQL语句
 *                  1、常用方法：
 *                      1、setInt(int index, int k)：将index位置上的参数设置为int类型的k；
 *                      2、setFloat(int index, float k)：将index位置上的参数设置为float类型的k；
 *                      3、setLong(int index, long k)：将index位置上的参数设置为long类型的k；
 *                      4、setDouble(int index, double k)：将index位置上的参数设置为double类型的k；
 *                      5、setBoolean(int index, boolean k)：将index位置上的参数设置为boolean类型的k；
 *                      6、setDate(int index, date k)：将index位置上的参数设置为date类型的k；
 *                      7、setString(int index, String k)：将index位置上的参数设置为String类型的k；
 *                      8、setNull(int index, int sqlType)：将index位置上的参数设置为NULL；
 *                      9、executeQuery()：执行SQL查询，并返回查询生成的ResultSet对象；
 *                      10、executeUpdate()：执行前面包含参数的动态insert、update或delete语句；
 *                      11、clearParameters()：清除当前所有参数的值。
 *              4、DriverManager类：用来管理数据库中的所有驱动程序。作用于用户和驱动程序之间，跟踪可用的驱动程序，
 *                  并在数据库的驱动程序之间建立连接。如果通过getConnection方法可以建立连接，则经连接返回，否则抛出
 *                  SQLException异常。
 *                  1、常用方法：
 *                      1、getConnection(String url, String user, String password)：获取数据库的连接；
 *                      2、setLoginTimeout()：设置登录数据库时的超时时间，单位为秒；
 *                      3、println(String message)：在JDBC日志中打印一条信息。
 *              5、ResultSet接口：用来暂时存放数据库查询操作所获得的结果集，类似一个临时表。ResultSet实例具有指向当前
 *                  数据行的指针，指针开始的位置在第一条记录的前面，通过next()方法可将指针向下移。
 *                  1、常用方法：
 *                      1、getInt()：以int形式获取此ResultSet对象的当前行的指定列值，如果列值是NULL，则返回值是0
 *                      2、getFloat()：以float形式获取此ResultSet对象的当前行的指定列值，如果列值是NULL，则返回值是0
 *                      3、getDate()：以date形式获取此ResultSet对象的当前行的指定列值，如果列值是NULL，则返回值是null
 *                      4、getBoolean()：以boolean形式获取此ResultSet对象的当前行的指定列值，如果列值是NULL，则返回值是null
 *                      5、getString()：以String形式获取此ResultSet对象的当前行的指定列值，如果列值是NULL，则返回值是null
 *                      6、getObject()：以Object形式获取此ResultSet对象的当前行的指定列值，如果列值是NULL，则返回值是null
 *                      7、first()：将指针移到当前记录的第一行
 *                      8、last()：将指针移到当前记录的最后一行
 *                      9、next()：将指针移到下一行
 *                      10、beforeFirst()：将指针移到集合开头
 *                      11、afterLast()：将指针移到集合结尾
 *                      12、absolute(int index)：将指针移到指定行
 *                      13、isFirst()：判断指针是否在集合第一行
 *                      14、isLast()：判断指针是否在集合最后一行
 *                      15、updateInt()：用int值更新集合指定列，不会同步修改数据库中的记录
 *                      16、updateFloat()：用float值更新集合指定列，不会同步修改数据库中的记录
 *                      17、updateLong()：用long值更新集合指定列，不会同步修改数据库中的记录
 *                      18、updateString()：用String值更新集合指定列，不会同步修改数据库中的记录
 *                      19、updateObject()：用Object值更新集合指定列，不会同步修改数据库中的记录
 *                      20、updateNull()：用NULL值更新集合指定列，不会同步修改数据库中的记录
 *                      21、updateDate()：用date值更新集合指定列，不会同步修改数据库中的记录
 *                      22、updateDouble()：用double值更新集合指定列，不会同步修改数据库中的记录
 *                      23、getRow()：查看当前行的索引号
 *                      24、insertRow()：将插入行的内容插入到数据库中
 *                      25、updateRow()：将当前行的内容同步更新倒数据库中
 *                      26、deleteRow()：删除当前行，但不同步到数据库中，在执行close()方法后同步到数据库中
 *                  2、连接数据库步骤：
 *                      1、DriverManage.getConnection("jdbc:mysql://ip:port/database","user","pwd")建立数据库连接con
 *                      执行sql：
 *                          2、con.createStatement()实例化Statement对象sql;
 *                          3、sql.executeQuery(sql)执行sql获取ResultSet结果
 *                          4、对结果进行处理
 *                      执行预编译sql：
 *                          2、con.prepareStatement(sql)实例化preparedStatement对象sql;
 *                          3、sql.setInt(paramIndex, value)设置动态sql的参数
 *                          4、sql.executeQuery()执行sql获取ResultSet结果集
 *                          5、处理结果
 *
 *
 *
 *
 *
 *
 *
 */

public class Database {
    public Connection getConnection() {
        Connection conn = null;
        try {
            /**
             * 加载数据库驱动，下载驱动包，点击File->Project Structure->Modules->dependencies 点击绿色加号，JARs or ...
             * 把下载的jar包导入即可。
             */
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // 打开数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8", "root", "w*Lzx2923");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void printResultSet(ResultSet res) throws SQLException {
        while(res.next()) {
            System.out.print("id:" + res.getInt("id"));
            System.out.print("  name:" + res.getString("name"));
            System.out.println();
        }
        System.out.println("--------------------------------------------------");
    }

    public void normalStatementTest(Connection conn) {
        try {
            Statement sql = conn.createStatement(); // 创建Statement对象
            ResultSet res = sql.executeQuery("SELECT * FROM USER WHERE id > 2"); // 执行sql取到返回的结果集
            printResultSet(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预处理语句，执行动态sql
     * @param conn
     */
    public void preparedStatement(Connection conn) {
        try {
            // 定义预编译sql
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM USER WHERE id = ?");
            // 设置sql语句参数
            sql.setInt(1, 2);
            // 执行sql
            ResultSet res = sql.executeQuery();
            printResultSet(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void dbAction(Connection conn) {
        try {
            // 插入
            PreparedStatement sql = conn.prepareStatement("INSERT INTO USER (NAME) VALUE(?)");
            sql.setString(1, "荒天帝");
            sql.executeUpdate();
            sql.setString(1, "叶天帝");
            sql.executeUpdate();
            sql.setString(1, "无始大帝");
            sql.executeUpdate();
            sql.setString(1, "狠人大帝");
            sql.executeUpdate();

            // 修改
            sql = conn.prepareStatement("UPDATE USER SET NAME = ? WHERE id = ?");
            sql.setString(1, "石昊");
            sql.setInt(2, 13);
            sql.executeUpdate();
            sql.setString(1, "叶凡");
            sql.setInt(2, 14);
            sql.executeUpdate();

            sql = conn.prepareStatement("DELETE FROM USER WHERE id = ?");
            sql.setInt(1, 3);
            sql.executeUpdate();
            sql.setInt(1, 4);
            sql.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        normalStatementTest(conn);
    }

    public static void main(String[] args){
        Database db = new Database();
        Connection conn = db.getConnection();
        db.normalStatementTest(conn);
        db.preparedStatement(conn);
        db.dbAction(conn);
    }
}
