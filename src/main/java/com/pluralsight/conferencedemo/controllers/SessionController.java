package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired // injecter la Session Repository dans le controlleur
    private SessionRepository sessionRepository;

    @GetMapping // Get pour recuperer la list des Sessions
    public List<Session> list(){
        return sessionRepository.findAll(); // creer par le Spring DATA JPA
    }

    @GetMapping // Get pour recuperer une Session
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getById(id);
    }

    @PostMapping // poster une session au repository pour la creer
    public Session create(@RequestBody Session session){
        return sessionRepository.saveAndFlush(session);
    }

    //pour faire le delete il faut appeler la methode Delete du request avec un parametre
    @RequestMapping(value="{id}" , method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        sessionRepository.deleteById(id);
    }
    //pour faire l'update il faut appeler la methode PUT du request avec un parametre
    @RequestMapping(value="{id}",method = RequestMethod.PUT)
    public Session update(@PathVariable Long id , @RequestBody Session session){
        // Recupere la session existante
        Session existingSession = sessionRepository.getById(id);
        // à l'aide de BeanUtils on copie les valeurs de la nouvelle session sur l'ancienne sans le session id PK
        BeanUtils.copyProperties(session,existingSession,"session_id");
        // Sauvgarder la session apres modification
        return sessionRepository.saveAndFlush(existingSession);
    }

}
