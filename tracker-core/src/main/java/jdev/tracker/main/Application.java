package jdev.tracker.main;
//import jdev.tracker.controllers.TrackController;
import jdev.domain.ReqCreate;
import jdev.tracker.controllers.InjectionContext;
import jdev.tracker.dao.repo.TrackBaseRepository;
import jdev.tracker.jpa.JpaApplication;
import jdev.tracker.services.GPSservice;
import jdev.tracker.services.Msgqueueservice;
import jdev.tracker.services.Msgsendservice;
import jdev.tracker.services.ResponseTick;
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
@EnableJpaRepositories("jdev.tracker.dao")
@EntityScan(basePackageClasses = jdev.tracker.dao.TrackBase.class)
@ComponentScan({"jdev.tracker","jdev.tracker.services"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
        //ApplicationContext context = new AnnotationConfigApplicationContext(InjectionContext.class);
       // SpringApplication.run(jdev.server.main.Application.class, args);
    }
    @Bean
    @Primary
    public GPSservice gpservice() {return new GPSservice();}

    @Bean
    @Primary
    public Msgqueueservice queueService() { return new Msgqueueservice();} //перед параметры

    @Bean
    @Primary
    public Msgsendservice sendService() {return new Msgsendservice();}

    @Bean
    @Primary
    public ResponseTick resp() {return new ResponseTick();}

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    TrackBaseRepository TrackBaseRepository;
}
