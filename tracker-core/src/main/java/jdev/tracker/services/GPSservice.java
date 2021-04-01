package jdev.tracker.services;
import com.maxmind.geoip.LookupService;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jdev.tracker.jpa.JpaApplication;

/**
 * Created by user on 04.02.2021.
 */
@Service
@Component
public class GPSservice {
    private int putCount=1;
    @Autowired
    private Msgqueueservice msqueuservice;
    private static final int Delay=1;
    private String las="1";
    private String los="1";
    private String Adr="127.0.0.1";
    private String City="Z";
    private Float la,lo;
    private Integer rand1,rand2,rand3;
    private static final Logger log = LoggerFactory.getLogger(GPSservice.class);
    private String Head=GPSservice.class.getSimpleName();
    @PostConstruct
    @Scheduled(cron= "${cron.prop}")
    public void job() throws InterruptedException {
        MDC.put("Head", Head);
        try {
            Random objGenerator = new Random();
            LookupService cl = new LookupService("tracker-core\\src\\main\\resources\\GeoLiteCity.dat",
                    LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);

                rand1 = objGenerator.nextInt(255);         // генерируем ip
                rand2 = objGenerator.nextInt(255);
                rand3 = objGenerator.nextInt(255);
                Adr=rand1.toString()+".123.13."+rand3.toString();
                la=cl.getLocation(Adr).latitude;
                lo=cl.getLocation(Adr).longitude;
                las=la.toString();
                los=lo.toString();
                if (cl.getLocation(Adr).city.isEmpty())
                    City=" Suburbs";
                else
                City=cl.getLocation(Adr).city;
            int i = putCount++;
            msqueuservice.queue.put(i + ","+ las +"," + los+","+cl.getLocation(Adr).countryName.replace(",", "")+","+City.replace(",", ""));
        //    JpaApplication.setLine(i,la,lo,cl.getLocation(Adr).countryName,cl.getLocation(Adr).city);
            cl.close();
        } catch (IOException e1) {
            log.info("IO Exception");
        }
     catch (java.lang.InterruptedException j)
    {
        log.info("Interrupted Exception");
    }
    catch (java.lang.NullPointerException n)
    {
       // log.info("Нет связи с IP....");
        try {
            msqueuservice.queue.put(0 + ","+ 99f +"," + 99f+","+"ip not found"+","+"ip not found");
        }
        catch (NullPointerException n2) {
            log.info("service not found");
        }
    }
    }
    }

