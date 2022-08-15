package io.javabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.MovieInfo;
import io.javabrains.moviecatalogservice.services.RatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    RatingInfo ratingInfo;

    @RequestMapping("/{userId}")
    //@HystrixCommand(fallbackMethod = "getFallBackCatalog")
    public List<CatalogItem> getCatalogItem(@PathVariable("userId") String userId) {
        UserRating ratings = ratingInfo.getUserRating(userId);
        return ratings.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());
    }

    /*@HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
    private CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class, UserRating.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }*/

    /*@HystrixCommand(fallbackMethod = "getFallBackUserRating")
    private UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    private UserRating getFallBackUserRating(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRating(Arrays.asList(
                new Rating("0", 0)));
        return userRating;
    }*/

    /*private CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("Movie Name not Found", " ", rating.getRating());
    }*/

    /*public List<CatalogItem> getFallBackCatalog(@PathVariable("userId") String userId){
        return Arrays.asList(new CatalogItem("No movie"," ",0));
    }*/
}
//RestTemplate restTemplate = new RestTemplate();
//WebClient.Builder builder = WebClient.builder();

        /*List<Rating> ratings =Arrays.asList(
                new Rating("1234", 4),
                new Rating("2356",3)
        );*/
/*return Collections.singletonList(
                new CatalogItem("Transformers",  "test", 4)
        );*/
//WebClientAPI Calling
                    /*Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();*/