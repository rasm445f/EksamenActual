package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Owner;
import errorhandling.API_Exception;
import facades.OwnerFacade;
import javassist.NotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("owner")
public class OwnerResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final OwnerFacade FACADE =  OwnerFacade.getOwnerFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Path("/allOwners")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() throws NotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllOwners())).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOwner(@PathParam("id") int id) throws API_Exception {
        return Response.ok().entity(GSON.toJson(FACADE.getOwnerById(id))).build();
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String owner) {
        Owner ownerTwo =GSON.fromJson(owner, Owner.class);
        Owner burner = new Owner(ownerTwo.getOwnerName(), ownerTwo.getOwnerAdress(), ownerTwo.getOwnerPhonenum());
        Owner newOwner = FACADE.createOwner(burner);
        return Response.ok().entity(GSON.toJson(newOwner)).build();
    }

    @GET
    @Path("/boat/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllOwnersOfBoatID(@PathParam("id") int id) throws NotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllOwnersOfBoatID(id))).build();
    }



//    @PUT
//    @Path("/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response update(@PathParam("id") int id, String content) throws EntityNotFoundException {
//        UserDTO userJson = GSON.fromJson(content, UserDTO.class);
//        User user = userJson.toUser();
//        UserDTO updated = FACADE.updateUser(id,user);
//        return Response.ok().entity(GSON.toJson(updated)).build();
//    }
//    @DELETE
//    @Path("/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response delete(@PathParam("id") int id) throws EntityNotFoundException, API_Exception {
//        UserDTO deleted = FACADE.deleteUser(id);
//        return Response.ok().entity(GSON.toJson(deleted)).build();
//    }

//    @GET
//    @Path("/owner")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAll() throws NotFoundException {
//        return Response.ok().entity(GSON.toJson(FACADE.getAllUsers())).build();
//    }


//    @POST
//    @Path("/favorite")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response favorite(String favoriteInfo) throws API_Exception {
//        Favorites favorites = GSON.fromJson(favoriteInfo,Favorites.class);
//        FavoritesDTO theFan = FACADE.addFavorite(favorites);
//        return Response.ok().entity(GSON.toJson(theFan)).build();
//    }

//   @GET
//    @Path("/{id}+favorite")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAllFavorites(@PathParam("id")int id) throws API_Exception, IOException {
//        List<HarborDto> harborList = FACADE.getAllFavoritesFromID(id);
//        List<CharityDTO> getThese = new ArrayList<>();
//       AllCategories allCategories = new AllCategories();
//       for (FavoritesDTO f : favoritesList) {
//           boolean accepted = false;
//           for (String s: allCategories.getList()) {
//               if(accepted == true){break;}
//               String nonprofit = HttpUtils.fetchData("https://partners.every.org/v0.2/search/" + s + "?apiKey=2b719ff3063ef1714c32edbfdd7af870&take=50");
//               NonProfitDTO nonProfitDTO = GSON.fromJson(nonprofit, NonProfitDTO.class);
//               for (CharityDTO c:nonProfitDTO.getNonprofits()) {
//                   if(accepted == true){break;}
//                   if (c.getSlug().equals(f.getSlug())){
//                       getThese.add(c);
//                       accepted = true;
//                   }
//               }
//           }
//       }
//       String nonprofit = HttpUtils.fetchData("https://partners.every.org/v0.2/search/pets?apiKey=2b719ff3063ef1714c32edbfdd7af870&take=50");
//       NonProfitDTO nonProfitDTO =GSON.fromJson(nonprofit, NonProfitDTO.class);
//       nonProfitDTO.setNonprofits(getThese);
//        return  Response.ok().entity(GSON.toJson(nonProfitDTO)).build();
//    }
}
