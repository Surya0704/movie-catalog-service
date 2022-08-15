package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private  RestTemplate restTemplate;

  /*  @HystrixCommand(fallbackMethod = "getFallBackCatalogItem",
            commandProperties =  {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            })*/
    @HystrixCommand(
          fallbackMethod = "getFallBackCatalogItem",
          threadPoolKey = "movieInfoPool",
          threadPoolProperties = {
                  @HystrixProperty(name = "coreSize", value = "20"),
                  @HystrixProperty(name = "maxQueueSize", value = "10")
          })
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class, UserRating.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    private CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("Movie Name not Found", " ", rating.getRating());
    }
    /*

    @HystrixCommand(fallbackMethod = "getFallBackUserRating")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }*/
}
