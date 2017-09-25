package com.deltacom.app.main;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.entities.Option;
import org.hibernate.SessionFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;


@Configuration
public class Application {
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
    }
}
