package configurations;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ServletConfig {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherServletRegistration() {
        ServletRegistrationBean<DispatcherServlet> registrationBean =
                new ServletRegistrationBean<>(dispatcherServlet(), "/");
        registrationBean.setName("dispatcherServlet");
        return registrationBean;
    }
}
