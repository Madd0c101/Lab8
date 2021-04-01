package jdev.tracker.jpa;

import jdev.tracker.dao.repo.TrackBaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;

//import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaApplication.class)
//@TestConfiguration
@EnableJpaRepositories("jdev.tracker.dao")
@EntityScan(basePackageClasses = jdev.tracker.dao.TrackBase.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(value = {
        "classpath:application.properties",
        "classpath:application-test.properties"
})
public class JpaApplicationTest {
    private static final Logger log = LoggerFactory.getLogger(JpaApplicationTest.class);
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrackBaseRepository repository;

    @Test
    public void run() throws Exception {
        JpaApplication j = new JpaApplication();
        j.run();
        boolean result=false;
        for (int i = 0; i < j.out2.length; i++) {
            result = j.out2[i].contains("Lattitude");

            if (result)
                log.info("=====JPA TEST PASSED SUCCESSFULLY=====:   "+j.out2[i]);
            break;

        }

        assertEquals(true, result);
    }
}