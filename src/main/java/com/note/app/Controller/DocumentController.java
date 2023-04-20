package com.note.app.Controller;

import com.note.app.Model.Document;
import com.note.app.Model.Note;
import com.note.app.Repository.DocumentRepo;
import com.note.app.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class DocumentController {


    @Autowired
    private NoteService document;
    private Note attach = null;



    @PostMapping("/upload")
    public ResponseEntity<String> uploadfile(@RequestParam("images") MultipartFile[] file, @RequestParam ("title") String title,@RequestParam ("description") String description) throws IOException {
        String downloadURl = "";
        attach = document.save(file,title,description);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attach.getDocuments().stream().map(Document::getDName).toString())
                .toUriString();
          return ResponseEntity.ok("file uploaded"+downloadURl);
    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws Exception {
            Document att = null;
                      att = document.getAttachment(fileId);
               return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(att.getDType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + att.getDName()+"\"")
                .body(new ByteArrayResource(att.getFile()));
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
