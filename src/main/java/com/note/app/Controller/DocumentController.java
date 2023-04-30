package com.note.app.Controller;


import com.note.app.Model.Note;
import com.note.app.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("/")
public class DocumentController {


    private String FOLDER_PATH = "E:/AFSD Project Note App/Backend/learning_material_note_app/src/main/resources/static/files/";


    @Autowired
    private NoteService document;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadfile(@RequestParam("images") MultipartFile[] file,
                                             @RequestParam ("title") String title,
                                             @RequestParam ("description") String description) throws IOException {
        Note attach = document.save(file,title,description,FOLDER_PATH);
        Arrays.stream(file).forEach(multipartFile -> {
            String downloadURl = FOLDER_PATH+multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(new File(downloadURl));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return ResponseEntity.ok("Upload Sucsses..");
    }

    @GetMapping("/getNotes")
    public List<Note> getEmployees(){
        return document.getNotes();
    }

    @GetMapping("/getNotes/{Nid}")
    public Note getEmployees(@PathVariable Long Nid){
        return document.getByIdNotes(Nid);
    }
    @DeleteMapping("/getNotes/{Nid}")
    String deleteUser(@PathVariable Long Nid) {
        return document.deleteNote(Nid);
    }

    @PutMapping("/update/{Nid}")
    public ResponseEntity<String> update(@PathVariable Long Nid,
                                         @RequestParam ("title") String title,
                                         @RequestParam ("description")String description)throws IOException {
        Note updatedNote = document.update(Nid,title, description);
        return ResponseEntity.ok("Update Success....");
    }

    @GetMapping("/search/{word}")
    public List <Note> getEmployees(@PathVariable String word){

        return document.findbykeyword(word);
    }

}
