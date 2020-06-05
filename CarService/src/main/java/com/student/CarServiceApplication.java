package com.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import com.student.data.dal.CarImageDbModel;
import com.student.data.repo.CarImageRepo;
import com.student.data.repo.CarRepo;

@SpringBootApplication
@EnableEurekaClient
public class CarServiceApplication {

	private CarRepo carRepo;
	private CarImageRepo imageRepo;

	public CarServiceApplication(CarRepo carRepo, CarImageRepo imageRepo) {
		super();
		this.carRepo = carRepo;
		this.imageRepo = imageRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			Map<String, Integer> carImages = new HashMap<>();
			carImages.put("honda_01_01", 1);
			carImages.put("honda_01_02", 1);
			carImages.put("honda_01_03", 1);
			carImages.put("honda_01_04", 1);
			carImages.put("honda_02_01", 2);
			carImages.put("honda_02_02", 2);
			carImages.put("honda_02_03", 2);
			carImages.put("honda_03_01", 3);
			carImages.put("honda_03_02", 3);
			carImages.put("honda_03_03", 3);
			carImages.put("honda_04_01", 4);
			carImages.put("honda_04_02", 4);
			carImages.put("honda_04_03", 4);

			carImages.forEach((imageName, carId) -> {
				File file;
				try {
					file = ResourceUtils.getFile("classpath:images/" + imageName + ".jpg");
					InputStream in = new FileInputStream(file);
					byte[] media;
					media = IOUtils.toByteArray(in);

					CarImageDbModel image = new CarImageDbModel();
					image.setCar(carRepo.findById(carId).get());
					image.setImage(media);
					imageRepo.save(image);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		};
	}
}
