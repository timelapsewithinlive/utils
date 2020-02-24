package mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface Nacos {

  Map<String,Object> selectById(@Param("id") Long id);

}
