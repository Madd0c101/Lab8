package jdev.server.services;
//import jdev.domain.Jform;
import jdev.domain.Response;
import jdev.domain.RestRequest;
import jdev.server.jpa.JpaApplication;
import jdev.server.main.controllers.CounterController_SRV;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

//import jdev.tracker.services.ResponseTick.JForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static jdev.server.services.Msgpost.request;
import static jdev.server.main.controllers.CounterController_SRV.restTemplate;

/**
 * Created by jdev on 05.05.2017.
 */
@Service
public class getcoord {

    private static final Logger log = LoggerFactory.getLogger(getcoord.class);
    private static String Head=getcoord.class.getSimpleName();
    private static Integer i = 0;
    public static String position;
    public static Response result;
    public static String push() throws Exception {
      //  ObjectMapper mapper = new ObjectMapper();
      //  String jsonString = "[\"1000 Current coordinate: track# 2 lattitude=37.376205 longtitude=-122.1826 United States Palo Alto success\",\"989 Current coordinate: track# 14 lattitude=30.050003 longtitude=31.25 Egypt Misr success\"]";
        // reader=mapper.reader(String.class);
       // List list=new ArrayList<>();
     //   mapper.readValue(jsonString,List.class);
      //  request.getCoord();
     //    ObjectMapper mapper = new ObjectMapper();
       //    String json = mapper.writeValueAsString(request.getCoord()+" "+ CounterController_SRV.showStatus().getStatus());
       //     log.info("POST result2: {}", json);
       //     log.info("2");
       // return ReqCreate.request.getCoord();
        try {
            FileReader fr = new FileReader("tracker-core\\src\\main\\resources\\log_file.log");
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                position =line;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           // log.info("HERE 1", position);
        } catch (IOException e) {
            e.printStackTrace();
          //  log.info("HERE 2", position);
        }
       // log.info("SRV reading: {}", position);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request.getCoord());
        MDC.put("Head", Head);
       log.info("POST by tracker: {}", json);
        JpaApplication.inc++;
        return json;
    }

    }

