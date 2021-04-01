package jdev.server.main;
import jdev.domain.RestRequest;
import jdev.server.jpa.JpaApplication;
import jdev.server.main.controllers.CounterController_SRV;
import jdev.server.services.Msgpost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import jdev.server.jpa.JpaApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import jdev.domain.ReqCreate;
import jdev.server.dao.repo.TrackBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaRepositories("jdev.server.dao")
@EntityScan(basePackageClasses = jdev.server.dao.TrackBase.class)
@ComponentScan({"jdev.server", "jdev.server.services", "jdev.domain"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
      // SpringApplication.run(Application.class, args);


    }

   @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
       ReqCreate.create();
        return builder.build();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    TrackBaseRepository TrackBaseRepository;
}
