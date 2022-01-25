package com.example.springtokendemo;

import com.example.springtokendemo.service.AddDefaultUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringTokenDemoApplication extends SpringBootServletInitializer implements CommandLineRunner
{


    @Autowired
    private AddDefaultUser addDefaultUser;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(SpringTokenDemoApplication.class);
    }


    public static void main(String[] args)
    {
        SpringApplication.run(SpringTokenDemoApplication.class, args);
    }


    @Override public void run(String... args) throws Exception
    {
        System.out.println("************Creating admin user in the DB **************");
        addDefaultUser.addAdmin();
        System.out.println("************Created admin user in the DB successfully**************");
    }
}
