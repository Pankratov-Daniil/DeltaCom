package com.deltacom.app.main;

import com.deltacom.app.entities.AccessLevel;
import com.deltacom.app.repository.implementation.AccessLevelSqlRepository;
import com.deltacom.app.repository.implementation.SqlRepository;
import com.deltacom.app.repository.specifications.implementation.AccessLevelByIdSpecification;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class Application {

    public static void main(String[] args) {

        SqlRepository aLRepo = new AccessLevelSqlRepository();

        AccessLevel ac = new AccessLevel();
        ac.setName("USRD");

        aLRepo.add(ac);
        //List<AccessLevel> alL = aLRepo.query(new AccessLevelByIdSpecification(1));
        List<AccessLevel> alL = aLRepo.getAll();

        System.out.println(((AccessLevel)(alL.get(1))).getName());

        aLRepo.remove(ac);

        //alL = aLRepo.query(new AccessLevelByIdSpecification(1));
        alL = aLRepo.getAll();

        System.out.println(((AccessLevel)(alL.get(0))).getName());
    }
}
