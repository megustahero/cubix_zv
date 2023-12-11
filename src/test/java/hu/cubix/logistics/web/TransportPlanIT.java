package hu.cubix.logistics.web;

import hu.cubix.logistics.dto.DelayDTO;
import hu.cubix.logistics.dto.LoginDTO;
import hu.cubix.logistics.dto.TransportPlanDTO;
import hu.cubix.logistics.mapper.TransportPlanMapper;
import hu.cubix.logistics.model.Address;
import hu.cubix.logistics.model.Milestone;
import hu.cubix.logistics.model.Section;
import hu.cubix.logistics.model.TransportPlan;
import hu.cubix.logistics.repository.AddressRepository;
import hu.cubix.logistics.repository.MilestoneRepository;
import hu.cubix.logistics.repository.SectionRepository;
import hu.cubix.logistics.repository.TransportPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureWebTestClient
public class TransportPlanIT {

    private static final String LOGIN = "/api/login";
    private static final String BASE_URI = "/api/transportPlans/";
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MilestoneRepository milestoneRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    TransportPlanRepository planRepository;
    @Autowired
    TransportPlanMapper planMapper;

    @BeforeEach
    public void initDb() {

        // Truncate data
        sectionRepository.deleteAll();
        planRepository.deleteAll();
        milestoneRepository.deleteAll();
        addressRepository.deleteAll();

    }


    @Test
    void notAuthorized() {

        DelayDTO delay = new DelayDTO(1L, 5);
        String token = "";

        webTestClient
                .post()
                .uri(BASE_URI + "20" + "/delay")
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(delay)
                .exchange()
                .expectStatus()
                .isForbidden();

    }


    @Test
    void notExistingPlan() {

        DelayDTO delay = new DelayDTO(1L, 5);
        String token = getJwtToken();

        webTestClient
                .post()
                .uri(BASE_URI + "20" + "/delay")
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(delay)
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    void notExistingMilestone() {

        // Init tables with data
        TransportPlan tp1 = planRepository.save(new TransportPlan(1L, null, 10000));
        TransportPlan tp2 = planRepository.save(new TransportPlan(2L, null, 1));

        Address add1 = addressRepository.save(new Address(1L, "HU", "Budapest", "Derkovits Gyula utca", "1183", "10",47.448874, 19.189753));
        Address add2 = addressRepository.save(new Address(2L, "HU", "Békéscsaba", "Bánát utca", "5600", "2",46.672164, 21.095056));
        Address add3 = addressRepository.save(new Address(3L, "HU", "Tápiószentmárton", "Kossuth Lajos út", "2711", "18",47.341096, 19.746389));

        Milestone m1 = milestoneRepository.save(new Milestone(1L, LocalDateTime.parse("2024-01-24T14:00:00"), add1));
        Milestone m2 = milestoneRepository.save(new Milestone(2L, LocalDateTime.parse("2024-01-25T14:00:00"), add2));
        Milestone m3 = milestoneRepository.save(new Milestone(3L, LocalDateTime.parse("2024-01-28T14:00:00"), add3));
        Milestone m4 = milestoneRepository.save(new Milestone(4L, LocalDateTime.parse("2024-02-24T15:00:00"), add3));
        Milestone m5 = milestoneRepository.save(new Milestone(5L, LocalDateTime.parse("2024-02-24T15:00:00"), add3));

        Section s1 = sectionRepository.save(new Section(1L, tp1, m1, m2, 1));
        Section s2 = sectionRepository.save(new Section(2L, tp1, m2, m3, 2));

        Section s3 = sectionRepository.save(new Section(3L, tp2, m4, m5, 1));


        DelayDTO delay = new DelayDTO(m4.getId(), 5);
        String token = getJwtToken();

        webTestClient
                .post()
                .uri(BASE_URI + tp1.getId() + "/delay")
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(delay)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


    @Test
    void addDelayToStartingMilestone() {

        // Init tables with data
        TransportPlan tp1 = planRepository.save(new TransportPlan(1L, null, 10000));
        TransportPlan tp2 = planRepository.save(new TransportPlan(2L, null, 1));

        Address add1 = addressRepository.save(new Address(1L, "HU", "Budapest", "Derkovits Gyula utca", "1183", "10",47.448874, 19.189753));
        Address add2 = addressRepository.save(new Address(2L, "HU", "Békéscsaba", "Bánát utca", "5600", "2",46.672164, 21.095056));
        Address add3 = addressRepository.save(new Address(3L, "HU", "Tápiószentmárton", "Kossuth Lajos út", "2711", "18",47.341096, 19.746389));

        Milestone m1 = milestoneRepository.save(new Milestone(1L, LocalDateTime.parse("2024-01-24T14:00:00"), add1));
        Milestone m2 = milestoneRepository.save(new Milestone(2L, LocalDateTime.parse("2024-01-25T14:00:00"), add2));
        Milestone m3 = milestoneRepository.save(new Milestone(3L, LocalDateTime.parse("2024-01-28T14:00:00"), add3));
        Milestone m4 = milestoneRepository.save(new Milestone(4L, LocalDateTime.parse("2024-02-24T15:00:00"), add3));
        Milestone m5 = milestoneRepository.save(new Milestone(5L, LocalDateTime.parse("2024-02-24T15:00:00"), add3));

        Section s1 = sectionRepository.save(new Section(1L, tp1, m1, m2, 1));
        Section s2 = sectionRepository.save(new Section(2L, tp1, m2, m3, 2));

        Section s3 = sectionRepository.save(new Section(3L, tp2, m4, m5, 1));


        TransportPlan plan = planMapper.dtoToModel(addDelay(tp1.getId(), m1.getId()));
        List<Section> sections = plan.getSections();

        assertThat(sections.get(0).getFromMilestone().getPlannedTime().equals(LocalDateTime.parse("2024-02-24T14:05:00")));
        assertThat(sections.get(0).getToMilestone().getPlannedTime().equals(LocalDateTime.parse("2024-02-25T14:05:00")));
    }


    @Test
    void addDelayToEndMilestone() {

        // Init tables with data
        TransportPlan tp1 = planRepository.save(new TransportPlan(1L, null, 10000));
        TransportPlan tp2 = planRepository.save(new TransportPlan(2L, null, 1));

        Address add1 = addressRepository.save(new Address(1L, "HU", "Budapest", "Derkovits Gyula utca", "1183", "10",47.448874, 19.189753));
        Address add2 = addressRepository.save(new Address(2L, "HU", "Békéscsaba", "Bánát utca", "5600", "2",46.672164, 21.095056));
        Address add3 = addressRepository.save(new Address(3L, "HU", "Tápiószentmárton", "Kossuth Lajos út", "2711", "18",47.341096, 19.746389));

        Milestone m1 = milestoneRepository.save(new Milestone(1L, LocalDateTime.parse("2024-01-24T14:00:00"), add1));
        Milestone m2 = milestoneRepository.save(new Milestone(2L, LocalDateTime.parse("2024-01-25T14:00:00"), add2));
        Milestone m3 = milestoneRepository.save(new Milestone(3L, LocalDateTime.parse("2024-01-28T14:00:00"), add3));
        Milestone m4 = milestoneRepository.save(new Milestone(4L, LocalDateTime.parse("2024-02-24T15:00:00"), add3));
        Milestone m5 = milestoneRepository.save(new Milestone(5L, LocalDateTime.parse("2024-02-24T15:00:00"), add3));

        Section s1 = sectionRepository.save(new Section(1L, tp1, m1, m2, 1));
        Section s2 = sectionRepository.save(new Section(2L, tp1, m2, m3, 2));

        Section s3 = sectionRepository.save(new Section(3L, tp2, m4, m5, 1));


        TransportPlan plan = planMapper.dtoToModel(addDelay(tp1.getId(), m2.getId()));
        List<Section> sections = plan.getSections();

        assertThat(sections.get(0).getToMilestone().getPlannedTime().equals(LocalDateTime.parse("2024-02-25T14:05:00")));
        assertThat(sections.get(1).getFromMilestone().getPlannedTime().equals(LocalDateTime.parse("2024-02-28T14:05:00")));

    }


    TransportPlanDTO addDelay(Long planId, Long milestoneId) {

        DelayDTO delay = new DelayDTO(milestoneId, 5);
        String token = getJwtToken();

        return webTestClient
                .post()
                .uri(BASE_URI + planId + "/delay")
                .headers(headers -> headers.setBearerAuth(token))
                .bodyValue(delay)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TransportPlanDTO.class)
                .returnResult()
                .getResponseBody();

    }


    String getJwtToken() {

        LoginDTO login = new LoginDTO("transportManager", "password");

        return webTestClient
                .post()
                .uri(LOGIN)
                .bodyValue(login)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

    }

}
