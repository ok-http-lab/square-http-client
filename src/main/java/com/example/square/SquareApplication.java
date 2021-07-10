package com.example.square;


import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.square.retrofit.EnableRetrofitClients;
import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import org.springframework.context.annotation.Bean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@SpringBootApplication
@EnableRetrofitClients
public class SquareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquareApplication.class, args);
	}


	@Bean
	ApplicationRunner runner(GreetingsClient client) {
		return event -> {
			for (int i = 0; i < 3; i++) {
				var call = client.greet("Shubham");
				var bodyOfResult = call.execute().body();
				System.out.println("bodyOfResult = " + bodyOfResult);
			}
		};
	}

	@Bean
	@LoadBalanced
	OkHttpClient.Builder okHttpClientBuilder() {
		return new Builder();
	}

}

@RetrofitClient("greeting")
interface GreetingsClient {

	@GET("/hello/{name}")
	Call<String> greet(@Path("name") String name);

}
