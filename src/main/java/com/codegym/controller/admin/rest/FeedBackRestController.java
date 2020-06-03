package com.codegym.controller.admin.rest;

import com.codegym.model.Contact;
import com.codegym.model.FeedBack;
import com.codegym.service.Impl.ContactService;
import com.codegym.service.Impl.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedBackRestController {
    @Autowired
    private FeedBackService feedBackService;

    @GetMapping("/feedBacks/")
    private ResponseEntity<List<FeedBack>> listFeedBacks(){
        List<FeedBack> feedBacks= feedBackService.findAll ();

        return new ResponseEntity<List<FeedBack>>(feedBacks, HttpStatus.OK);
    }

    @GetMapping(value = "/feedBacks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedBack> getFeedBack(@PathVariable("id") long id) {
        FeedBack feedBack = feedBackService.findById(id);
        if (feedBack == null) {

            return new ResponseEntity<FeedBack>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<FeedBack>(feedBack, HttpStatus.OK);
    }

    @PostMapping(value = "/feedBacks/")
    public ResponseEntity<FeedBack> createFeedBack(@RequestBody FeedBack feedBack, UriComponentsBuilder ucBuilder) {
        try {
            feedBackService.save(feedBack);
            return new ResponseEntity<FeedBack>(feedBack, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<FeedBack>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/feedBacks/{id}")
    public ResponseEntity<FeedBack> updateFeedBack(@PathVariable("id") long id, @RequestBody FeedBack feedBack) {

        FeedBack currentFeedBack = feedBackService.findById(id);

        if (currentFeedBack == null) {
            System.out.println("FeedBack with id " + id + " not found");
            return new ResponseEntity<FeedBack>(HttpStatus.NOT_FOUND);
        }

        currentFeedBack.setName (feedBack.getName ());
        try {
            feedBackService.save(currentFeedBack);
        }catch (Exception ex){
            return new ResponseEntity<FeedBack>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<FeedBack>(currentFeedBack, HttpStatus.OK);
    }
    @DeleteMapping(value = "/feedBacks/{id}")
    public ResponseEntity<FeedBack> deleteFeedBack(@PathVariable("id") Long id){
        FeedBack currentFeedBack = feedBackService.findById(id);
        if (currentFeedBack == null) {
            System.out.println("FeedBack with id " + id + " not found");
            return new ResponseEntity<FeedBack>(HttpStatus.NOT_FOUND);
        }
        try {
            feedBackService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<FeedBack> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FeedBack>(currentFeedBack, HttpStatus.OK);
    }
}
