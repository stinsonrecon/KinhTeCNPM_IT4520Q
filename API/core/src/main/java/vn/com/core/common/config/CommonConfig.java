package vn.com.core.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import vn.com.core.common.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class CommonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.setDateFormat(new SimpleDateFormat(DateUtil.FORMAT_DATE_TIME4));
        objMapper.setTimeZone(TimeZone.getTimeZone(DateUtil.TIME_ZONE));
        return objMapper;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean(name = "asyncTask")
    TaskExecutor asyncTask() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);//Số thread cho phép chạy cùng lúc
        taskExecutor.setQueueCapacity(500);//Số lệnh có thể chờ trong hàng đợi. Nếu =0 thì tất cả sẽ chạy cùng lúc không phụ thuộc CorePoolSize
        taskExecutor.setThreadNamePrefix("AsyncTask");
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
