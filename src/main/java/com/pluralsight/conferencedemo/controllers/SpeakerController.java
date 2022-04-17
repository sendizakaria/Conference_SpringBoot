package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    @Autowired // intejcter le Speaker Repository
    private SpeakerRepository speakerRepository;

    @GetMapping // Get pour recuperer la list des Speakers
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }

    @GetMapping // Get pour recuperer la list des Speakers
    @RequestMapping("{id}") // parametre a passer
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getById(id);
    }

    @PostMapping // poster un Speaker au repository pour le creer
    public Speaker create(@RequestBody Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }


    @RequestMapping(value="{id}" , method = RequestMethod.DELETE)//pour faire le delete il faut appeler la methode Delete du request avec un parametre
    public void delete(@PathVariable Long id){
        speakerRepository.deleteById(id);
    }


    @RequestMapping(value="{id}",method = RequestMethod.PUT) //pour faire l'update il faut appeler la methode PUT du request avec un parametre
    public Speaker update(@PathVariable Long id , @RequestBody Speaker speaker){
        // Recupere la Speaker existante
        Speaker existingSpeaker = speakerRepository.getById(id);
        // à l'aide de BeanUtils on copie les valeurs de nouveau Speaker sur l'ancienne sans le Speaker id PK
        BeanUtils.copyProperties(speaker,existingSpeaker,"speaker_id");
        // Sauvgarder la Speaker apres modification
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

}
