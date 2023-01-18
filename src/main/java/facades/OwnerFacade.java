package facades;


import dtos.OwnerDto;
import entities.Owner;
import errorhandling.API_Exception;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class OwnerFacade {
    private static EntityManagerFactory emf;
    private static OwnerFacade instance;

    private OwnerFacade() {
    }

    public static OwnerFacade getOwnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OwnerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Owner createOwner(Owner owner) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return owner;
    }

//    public Owner createOwner(Owner owner, Harbor harbor) {
//        EntityManager em = getEntityManager();
//
//        if(harbor==null){
//            Harbor defaultHarbor = new Harbor();
//            owner.addRole(defaultRole);
//        }
//        else{
//            owner.addRole(role);
//        }
//        try {
//            em.getTransaction().begin();
//            em.persist(owner);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//        return owner;
//    }


    public List<OwnerDto> getAllOwners() throws NotFoundException {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT u FROM Owner u", Owner.class);
            if (query == null) {
                throw new NotFoundException("Can't find any owners");
            }
            List<Owner> owners = query.getResultList();
            return OwnerDto.getOwnerDtos(owners);
        } finally {
            em.close();
        }
    }

    public List<OwnerDto> getAllOwnersOfBoatID(int boatId) throws NotFoundException {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Owner> query = em.createQuery("SELECT DISTINCT ob.ownerID FROM OwnerBoat ob WHERE ob.id.boatID = :boatId", Owner.class);
            query.setParameter("boatId", boatId);
            if (query == null) {
                throw new NotFoundException("Can't find any owners");
            }
            List<Owner> owners = query.getResultList();
            return OwnerDto.getOwnerDtos(owners);
        } finally {
            em.close();
        }
    }

    public OwnerDto getOwnerById(int id) throws API_Exception {
        EntityManager em = getEntityManager();

        Owner owner = em.find(Owner.class, id);
        if (owner == null)
            throw new API_Exception("There's no owner with that id", 404);
        em.close();
        return new OwnerDto(owner);


    }

    public OwnerDto deleteOwner(int id) throws API_Exception {
        EntityManager em = getEntityManager();
        Owner owner;
        try {
            owner = em.find(Owner.class, id);
            if (owner == null) {
                throw new API_Exception("Can't find a owner with the ownername: " + id);
            }
            em.getTransaction().begin();
            em.remove(owner);
            em.getTransaction().commit();
            return new OwnerDto(owner);
        } finally {
            em.close();
        }
    }


    public OwnerDto updateOwner(int id, Owner ownerUpdate) {
        EntityManager em = getEntityManager();
        Owner owner;
        try {
            owner = em.find(Owner.class, id);
            em.getTransaction().begin();
            owner.setOwnerName(ownerUpdate.getOwnerName());
            owner.setOwnerAdress(ownerUpdate.getOwnerAdress());
            owner.setOwnerPhonenum(ownerUpdate.getOwnerPhonenum());
            em.getTransaction().commit();
            return new OwnerDto(owner);
        } finally {
            em.close();
        }

    }

//    public OwnerDto addOwner(Boat boat) throws API_Exception {
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

