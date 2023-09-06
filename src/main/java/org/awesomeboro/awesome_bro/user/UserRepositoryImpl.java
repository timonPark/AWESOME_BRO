package org.awesomeboro.awesome_bro.user;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager em;

    public Long save(User user){
        em.persist(user);
        return user.getId();
    }
    public User findOne(Long id){
        return em.find(User.class,id);
    }

    public Optional<User> findByEmail(String email) {
       return em.find(User.class, email);
    }

}
