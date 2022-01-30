package me.daboy.fcapi.controllers;

import me.daboy.fcapi.entities.Tag;
import me.daboy.fcapi.repositories.TagRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class TagsController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<String> findTags() {
        List<Tag> tags = tagRepository.findAll();
        JSONArray jsonResponse = new JSONArray();

        for(Tag tag : tags) {
            JSONObject tempTag = new JSONObject();
            tempTag.put("label", tag.getTitle());
            tempTag.put("value", tag.getId());
            jsonResponse.put(tempTag);
        }

        return ResponseEntity.ok(jsonResponse.toString());
    }
}
