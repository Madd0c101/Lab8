package jdev.server.jpa;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.joran.spi.JoranException;
import jdev.server.dao.TrackBase;
import jdev.server.dao.repo.TrackBaseRepository;
import jdev.server.services.Msgpost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.*;
import java.net.URL;
import java.util.*;

import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories("jdev.server.dao")
@EntityScan(basePackageClasses = jdev.server.dao.TrackBase.class)
public class JpaApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JpaApplication.class);
    private String Head=JpaApplication.class.getSimpleName();
    private static List<TrackBase> all;
    private static List<TrackBase> lines;
    private String TrackLine="";
    private static Float lat=1f;
    private static Float lon=1f;
    private static String country="def";
    private static String city="NA";
    private static int num=0;
    private static int check=0;
    private static Integer j=0;
    private static String position;
    private static String line;
    public static String out;
    public static TrackBase[] arr;
    public static String[] out2;
    public static Integer inc=0;
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }
   // public static String path="tracker-core\\src\\main\\resources\\log_file.log"; //RUN PATH
   // public static String path="..\\tracker-core\\src\\main\\resources\\log_file.log"; //TEST PATH
  // public static String path="${'fpath'}"; //RUN PATH

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    TrackBaseRepository trackBaseRepository;

    @Value("${fpath}")
    private String path;

    @Override
    public void run(String... args) throws Exception {
        //read();
        MDC.put("Head", Head);
        log.info("===============HERE============ "+path);
        lines= new ArrayList<TrackBase>();
        Integer col0=0;
        Float col1=0f;
        Float col2=0f;
        String col3="";
        String col4="";
        try {
            RandomAccessFile fr = new RandomAccessFile(path,"rw");
            //   BufferedReader reader = new BufferedReader(fr);
            while ((line = fr.readLine()) != null) {
                // if (line.contains("null")) {
                //     long position =fr.getFilePointer();
                //     fr.seek(position-line.length());
                //     fr.write(("0,99.0,99.0,ip not found,ip not found ").getBytes());
                // }
                StringTokenizer tokenizer = new StringTokenizer(fr.readLine(), ",");
                while(tokenizer.hasMoreTokens())
                {
                    col0= Integer.valueOf(tokenizer.nextToken().replace("\"", ""));
                    col1= Float.valueOf(tokenizer.nextToken().replace("\"", ""));
                    col2= Float.valueOf(tokenizer.nextToken().replace("\"", ""));
                    col3= tokenizer.nextToken().replace("\"", "");
                    col4= tokenizer.nextToken().replace("\"", "");
                }
                if (col0>0) {
                    try {
                    lines.add(j, create(col1, col2, col3, col4));
                    }
                    catch (NullPointerException n)
                    {
                        n.printStackTrace();
                    }
                    j++;
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            // log.info("HERE 1", position);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        //  log.info("HERE 2", position);
        catch (NullPointerException e3){
            e3.printStackTrace();
        }
        log.info("=========== after create");
        try {
            read();
        }
        catch (NullPointerException n)
        {
            n.printStackTrace();
        }

       // lines.stream().forEach(base -> out=base.toString());
        if (!lines.isEmpty()) {
            arr = lines.toArray(new TrackBase[lines.size()]);
            arr=lines.toArray(arr);
            out2 = new String[lines.size()];
        }
 try {
     for (int i = 0; i < arr.length; i++) {
         out2[i] = arr[i].toString();
     }
 }
 catch (NullPointerException e)
 {
     e.printStackTrace();
 }
        lines.stream().forEach(base -> update(base, "Rome"));
        log.info("=========== after update");
        read();
try {
    trackBaseRepository.deleteAll();
}
catch (NullPointerException n)
{
    n.printStackTrace();
}
        log.info("=========== after delete");
        read();

    }

    private void delete(TrackBase base) {
        trackBaseRepository.delete(base);
    }

    private void update(TrackBase base, String city) {
        base.setCity(city);
        trackBaseRepository.save(base);
    }

    private void read() {
        try {
            all = (List<TrackBase>) trackBaseRepository.findAll();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

        if (all.size() == 0) {
            log.info("NO RECORDS",log);
        }

        all.stream().forEach(base -> log.info(base.toString()));

    }

    private TrackBase create(Float lat, Float lon, String country, String city) {
        TrackBase base = new TrackBase();
        base.setLattitude(lat);
        base.setLongtitude(lon);
        base.setCountry(country);
        base.setCity(city);
        return trackBaseRepository.save(base);
    }

   // @Autowired
  //  private Msgpost post;

   // @PostConstruct
  //  @Scheduled(cron= "${cron.prop}")
  //  private void init() throws InterruptedException,Exception {
   //     Msgpost.send();
  //  }
   // public static void setLine (int i, Float la, Float lo, String co, String ci){
   //     lat=la;
   //     lon=lo;
   //     country=co;
   //     city=ci;
  //      num=i;
  //  }
}
