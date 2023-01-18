//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import dtos.FavoritesDTO;
//import entities.Favorites;
//import errorhandling.API_Exception;
//import facades.UserFacade;
//import utils.EMF_Creator;
//
//import javax.persistence.EntityManagerFactory;
//import java.io.IOException;
//
//public class SmallTest {
//    public static void main(String[] args) throws IOException, API_Exception {
//        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
//        UserFacade FACADE = UserFacade.getUserFacade(EMF);
//        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
//        Favorites favorites = new Favorites (2,"electionscience");
//        FavoritesDTO theFan = FACADE.addFavorite(favorites);
//    }
//}
