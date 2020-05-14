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
import com.thilaka.moviecatalogservice.models.CatalogItem;
import com.thilaka.moviecatalogservice.models.Movie;
import com.thilaka.moviecatalogservice.models.Rating;
import com.thilaka.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//RestTemplate restTemplate = new RestTemplate();
		//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);
		
		//WebClient.Builder builder = WebClient.builder();
		
		
		/*List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678",3)
		); */
		
		//UserRating userRating =  restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);
		UserRating userRating =  restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
				
		return userRating.getUserRating().stream().map(rating -> {
			//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			
			
			/*
			 Movie movie = webClientBuilder.build()
				.get()
				.uri("http://localhost:8082/movies/" + rating.getMovieId())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();
			*/
			
			
			return new CatalogItem(movie.getName(),"Desc",rating.getRating());
			
		})
		.collect(Collectors.toList());
		
		/*return Collections.singletonList(
				new CatalogItem("Transformers","Test",4 )
		);*/
		
	}

}
