package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Harbor;
import facades.HarborFacade;
import javassist.NotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("harbor")
public class HarborResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HarborFacade FACADE =  HarborFacade.getHarborFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Path("/allHarbor")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() throws NotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllHarbors())).build();
    }

//    @GET
//    @Path("/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getHarbor(@PathParam("id") int id) throws API_Exception {
//        return Response.ok().entity(GSON.toJson(FACADE.getHarborById(id))).build();
//    }

    @GET
    @Path("/harbour/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBoatByHarborID(@PathParam("id") int id) throws NotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllBoatsByHarbourId(id))).build();
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String harbor) {
        Harbor harborTwo =GSON.fromJson(harbor, Harbor.class);
        Harbor burner = new Harbor(harborTwo.getName(), harborTwo.getAdress(), harborTwo.getCapasity());
        Harbor newHarbor = FACADE.createHarbor(burner);
        return Response.ok().entity(GSON.toJson(newHarbor)).build();

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
