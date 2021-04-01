package jdev.tracker.jpa;

import jdev.tracker.dao.TrackBase;
import jdev.tracker.dao.repo.TrackBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

import jdev.tracker.services.GPSservice;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories("jdev.tracker.dao")
@EntityScan(basePackageClasses = jdev.tracker.dao.TrackBase.class)
public class JpaApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JpaApplication.class);
    private String Head="Tracker_"+JpaApplication.class.getSimpleName();
    private List<TrackBase> all;
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
    public static String[] out2;
    public static TrackBase[] arr;
   // public static String path="tracker-core\\src\\main\\resources\\log_file.log"; //RUN PATH
     public static String path="..\\tracker-core\\src\\main\\resources\\log_file.log"; //TEST PATH
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    TrackBaseRepository trackBaseRepository;

    @Override
    public void run(String... args) throws Exception {
        //read();
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
                    lines.add(j, create(col1, col2, col3, col4));
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
        MDC.put("Head", Head);
            log.info("=========== after create");
            try {
                read();
            }
            catch (NullPointerException n)
            {
                n.printStackTrace();
            }

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
            try {
                read();
            }
            catch (NullPointerException n) {
                n.printStackTrace();
            }
try {
    trackBaseRepository.deleteAll();
    log.info("=========== after delete");
    read();
}
catch (NullPointerException n)
{
    n.printStackTrace();
}

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
        }
        catch (NullPointerException n)
        {
            n.printStackTrace();
        }

         if (all.size() == 0) {
             log.info("NO RECORDS");
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

    public static void setLine (int i, Float la, Float lo, String co, String ci){
        lat=la;
        lon=lo;
        country=co;
        city=ci;
        num=i;
    }
}
