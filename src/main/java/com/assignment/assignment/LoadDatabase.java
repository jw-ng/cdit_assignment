package com.assignment.assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
@Slf4j
class LoadDatabase {

    @Autowired
    private ResourceLoader resourceLoader;

	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		return args -> {
			Resource res = resourceLoader.getResource("classpath:users.csv"); 
			List<String> lines = Files.readAllLines(
				Paths.get(res.getURI()),
				StandardCharsets.UTF_8
			);
			List<String[]> records = new ArrayList<>();
			// Skipping first line: column names
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] values = line.split(",");
				records.add(values);
			}
			for (int i = 0; i < records.size(); i++) {
				try {
					log.info(
						"Preloading user: name={}, salary={}",
						records.get(i)[0],
						records.get(i)[1]
					);
					repository.save(new User(
						records.get(i)[0],
						Double.parseDouble(records.get(i)[1])
					));
				}
				catch (Exception exception) {
					log.error("Invalid line in CSV: {}", exception.getMessage());
				}
			}
		};
	}
}