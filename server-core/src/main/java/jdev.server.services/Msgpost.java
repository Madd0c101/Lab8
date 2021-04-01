package jdev.server.services;

import jdev.domain.Jform;
import jdev.domain.ReqCreate;
import jdev.domain.RestRequest;
import jdev.server.main.controllers.CounterController_SRV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import jdev.server.jpa.JpaApplication;
import jdev.server.main.Application;


/**
 * Created by user on 04.02.2021.
 */

@Service
public class Msgpost {
    private static final Logger log = LoggerFactory.getLogger(Msgpost.class);
    private static String Head=Msgpost.class.getSimpleName();
    private long previous;
    public static RestRequest request = new RestRequest();

    public static void send() throws InterruptedException, Exception, IOException, NullPointerException {
        //      ReqCreate.create();
        //   request=ReqCreate.request;
        //  log.info("sending request");
        //    long current = System.currentTimeMillis();
        //  log.info((current - previous) + " Current coordinate: " + Jform.fromJson());
        try {
//            if (!JpaApplication.out.isEmpty()) {

if (JpaApplication.inc>=JpaApplication.out2.length) {
    JpaApplication.inc=0;
}
            request.setCoord(JpaApplication.out2[JpaApplication.inc]);
                CounterController_SRV.post(JpaApplication.out2[JpaApplication.inc], request);
            MDC.put("Head", Head);
                log.info("posted: " + JpaApplication.out2[JpaApplication.inc]+" "+JpaApplication.inc.toString());
       //     }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
      //  JpaApplication.inc++;
    }
}

