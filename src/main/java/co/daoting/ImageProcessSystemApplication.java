package co.daoting;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "co.daoting.**", exclude = DruidDataSourceAutoConfigure.class)
@MapperScan(basePackages = "co.daoting.mapper")
public class ImageProcessSystemApplication extends SpringApplication{

    public static void main(String[] args) {
        SpringApplication.run(ImageProcessSystemApplication.class, args);
    }

}
