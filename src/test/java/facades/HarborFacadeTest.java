package facades;

import dtos.HarborDto;
import entities.Boat;
import entities.Harbor;
import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HarborFacadeTest {

    private static EntityManagerFactory emf;
    private static HarborFacade facade;
    Harbor harbor1;
    Harbor harbor2;
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HarborFacade.getHarborFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Harbor.deleteAllRows").executeUpdate();
            harbor1 = new Harbor("city1",100,"Harbor1");
            harbor2 = new Harbor("city2", 111, "Harbor2");
            em.persist(harbor1);
            em.persist(harbor2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {

    }





    @Test
    void getAllHarbors() throws NotFoundException {
        List<HarborDto> actual = facade.getAllHarbors();
        int expected = 2;
        assertEquals(expected,actual.size());
    }

    @Test
    public void getAllBoatsByHarbourId(){
        EntityManager em = emf.createEntityManager();

        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b ", Boat.class);

        List<Boat> boats = query.getResultList();
        for (Boat b : boats) {
            System.out.println(b);
            System.out.println("i am here--------------------------------------------");
        }
    }

    @Test
    void getHarborById() throws NotFoundException {
        Harbor actual = facade.getHarborById(harbor1.getId());
        String expected = "Harbor1";
        assertEquals(expected,actual.getName());
    }



//    @Test
//    void getHarborById() throws API_Exception {
//        HarborDto actual = facade.getH(harbor1.getId()));
//        HarborDto expected = new HarborDto(harbor1);
//        assertEquals(expected.getId(),actual.getId());
//    }




}
