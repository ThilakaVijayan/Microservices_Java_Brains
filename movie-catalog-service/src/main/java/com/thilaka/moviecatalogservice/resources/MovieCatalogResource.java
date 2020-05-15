package com.thilaka.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thilaka.moviecatalogservice.models.CatalogItem;
import com.thilaka.moviecatalogservice.models.Movie;
import com.thilaka.moviecatalogservice.models.Rating;
import com.thilaka.moviecatalogservice.models.UserRating;
import com.thilaka.moviecatalogservice.services.MovieInfo;
import com.thilaka.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	//@Autowired
	//private DiscoveryClient discoveryClient;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private MovieInfo movieInfo;
	
	@Autowired
	private UserRatingInfo userRatingInfo;
	
	@RequestMapping("/{userId}")
	//@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//RestTemplate restTemplate = new RestTemplate();
		//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);
		
		//WebClient.Builder builder = WebClient.builder();
		
		
		/*
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678",3)
		); */
		
		//UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);
		//UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
		//UserRating userRating =  getUserRating(userId);
		UserRating userRating =  userRatingInfo.getUserRating(userId);
				
		//return userRating.getUserRating().stream().map(rating -> {
			//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
	
			/*
			 Movie movie = webClientBuilder.build()
				.get()
				.uri("http://localhost:8082/movies/" + rating.getMovieId())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();
			*/
			
			/*
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(),"Desc",rating.getRating());*/
			
			//return getCatalogItem(rating);
			
		//})
		//.collect(Collectors.toList());
		
		/*
		return userRating.getUserRating().stream().map(rating -> getCatalogItem(rating))
		.collect(Collectors.toList());
		 */
		
		return userRating.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating))
		.collect(Collectors.toList());
		
		/*
		return Collections.singletonList(
				new CatalogItem("Transformers","Test",4 )
		);*/
		
	}

	/*
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	private CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getName(),"Desc",rating.getRating());
	}
	
	private CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found","",rating.getRating());
	}*/

	/*
	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	private UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
	}
	
	private UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(
				new Rating("0",0)
				));
		return userRating;
	} */
	
	public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
		return Arrays.asList(new CatalogItem("No movie", "", 0));
	}

}
