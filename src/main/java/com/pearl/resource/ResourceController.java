package com.pearl.resource;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ResourceController {
	
	@Autowired
	EurekaClient eurekaClient;
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	
	@Inject
	RestTemplate restTemplate;
	
	@GetMapping("/builder")
	public String getResource() {
		
		
		RestTemplate restTemplate = restTemplateBuilder.build();
		
		InstanceInfo info = eurekaClient.getNextServerFromEureka("service", false);
		String baseUrl = info.getHomePageUrl();
		
		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
		
		
		return response.getBody();
	}
	
	
	@GetMapping
	public String getHealth() {
		return restTemplate.getForEntity("http://simspro-service/actuator/health", String.class).getBody();
	}

}
