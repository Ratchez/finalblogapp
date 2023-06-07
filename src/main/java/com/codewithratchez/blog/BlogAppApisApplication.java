package com.codewithratchez.blog;

import com.codewithratchez.blog.config.AppConstants;
import com.codewithratchez.blog.entities.Role;
import com.codewithratchez.blog.repositories.RoleRepo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("xyz"));
		try {
			Role role = new Role();
			role.setRoleId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setRoleId(AppConstants.ROLE_EDITOR);
			role1.setName("ROLE_EDITOR");

			Role role2 = new Role();
			role2.setRoleId(AppConstants.ROLE_USER);
			role2.setName("ROLE_USER");

			List<Role> roles =  List.of(role, role1, role2);
			List<Role> result = roleRepo.saveAll(roles);

			result.forEach(r ->{
				System.out.println(r.getName());
			});

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
