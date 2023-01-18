package facades;


import dtos.BoatDto;
import entities.Boat;
import errorhandling.API_Exception;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class BoatFacade {
    private static EntityManagerFactory emf;
    private static BoatFacade instance;

    private BoatFacade() {
    }

    public static BoatFacade getBoatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Boat createBoat(Boat boat) {
        EntityManager em = getEntityManager();
        //Boat.addRole(defaultRole);
        try {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return boat;
    }

//    public Boat createBoat(Boat boat, Harbor harbor) {
//        EntityManager em = getEntityManager();
//
//        if(harbor==null){
//            Harbor defaultHarbor = new Harbor();
//            boat.addRole(defaultRole);
//        }
//        else{
//            boat.addRole(role);
//        }
//        try {
//            em.getTransaction().begin();
//            em.persist(boat);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return boat;
//    }


    public List<BoatDto> getAllBoats() throws NotFoundException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT u FROM Boat u", Boat.class);
            if (query == null) {
                throw new NotFoundException("Can't find any boats");
            }
            List<Boat> boats = query.getResultList();
            return BoatDto.getBoatDtos(boats);
        } finally {
            em.close();
        }
    }



    public BoatDto getBoatById(int id) throws API_Exception {
        EntityManager em = getEntityManager();

        Boat boat = em.find(Boat.class, id);
        if (boat == null)
            throw new API_Exception("There's no boat with that id", 404);
        em.close();
        return new BoatDto(boat);


    }

    public BoatDto deleteBoat(int id) throws API_Exception {
        EntityManager em = getEntityManager();
        Boat boat;
        try {
            boat = em.find(Boat.class, id);
            if (boat == null) {
                throw new API_Exception("Can't find a boat with the boatname: " + id);
            }
            em.getTransaction().begin();
            em.remove(boat);
            em.getTransaction().commit();
            return new BoatDto(boat);
        } finally {
            em.close();
        }
    }


    public BoatDto updateBoat(int id, Boat boatUpdate) {
        EntityManager em = getEntityManager();
        Boat boat;
        try {
            boat = em.find(Boat.class, id);
            em.getTransaction().begin();
            boat.setName(boatUpdate.getName());
            boat.setHarborID(boatUpdate.getHarborID());
            em.getTransaction().commit();
            return new BoatDto(boat);
        } finally {
            em.close();
        }

    }

//    public BoatDto addBoat(Boat boat) throws API_Exception {
//        EntityManager em = getEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(boat);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        BoatDto theFan = new BoatDto(boat);
//        return theFan;
//    }
//    public List<BoatDto> getAllBoatsFromID(int id){
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Boat> query = em.createQuery("SELECT f FROM Boat f WHERE f.userid = ?1", Boat.class)
//                    .setParameter(1,id);
//            List<Boat> favoritesList = query.getResultList();
//            return BoatDto.getFavoriteDTOs(favoritesList);
//        } finally {
//            em.close();
//        }
//    }


}
