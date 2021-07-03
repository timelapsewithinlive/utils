package function;

import dsp.DspConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DspConfigSourceFunction extends RichSourceFunction<DspConfig> {
    Connection connection = null;
    PreparedStatement prepareStatement = null;
    ResultSet rs = null;
    private volatile boolean isRunning = true;

    @Override
    public void open(Configuration parameters) throws Exception {
          /*  // 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 获取连接
            String url = "jdbc:mysql://127.0.0.1:3306/test";
            String user = "root";
            String password = "asplover";
            connection = DriverManager.getConnection(url, user, password);*/
    }

    @Override
    public void run(SourceContext sourceContext) throws Exception {
        try{
            while (isRunning){
              /*  // 获取statement，preparedStatement
                String sql = "select * from tb_user where id=?";
                prepareStatement = connection.prepareStatement(sql);
                // 设置参数
                prepareStatement.setLong(1, 1);
                // 执行查询
                rs = prepareStatement.executeQuery();
                // 处理结果集
                while (rs.next()) {
                    System.out.println("用户名："+rs.getString("userName"));
                    System.out.println("密码为："+rs.getString("password"));

                }*/
                // 返回数据
                sourceContext.collect(new DspConfig());
                //每隔一个小时执行一次查询
                Thread.sleep(1000);
            }
        }catch (Exception e){
            System.out.println("Mysql data update error.."+e.getMessage());
        }finally {

        }
    }

    @Override
    public void cancel() {
        // 关闭连接，释放资源
        try{
            if (rs != null) {
                rs.close();
            }
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }catch (Exception e){

        }

        isRunning = false;
    }


}
