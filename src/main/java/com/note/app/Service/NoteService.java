package com.note.app.Service;
import com.note.app.Model.Document;
import com.note.app.Model.Note;
import com.note.app.Repository.DocumentRepo;
import com.note.app.Repository.NoteRepo;
import com.note.app.exception.UserNotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private DocumentRepo DRepo;



    private Logger logger = LoggerFactory.getLogger(NoteService.class);

    public Note save (MultipartFile[] files ,String title, String description) throws IOException {
        Note note = new Note();
        List<Document> documentList = new ArrayList<>();
        Arrays.stream(files).forEach(multipartFile -> {
               Document document = new Document();

            try {
                document.setFile(multipartFile.getBytes());
                document.setDSize(multipartFile.getSize());
                document.setDType(multipartFile.getContentType());
                document.setUploadTime(new Date());
                document.setDName(multipartFile.getOriginalFilename());
                documentList.add(document);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        note.setDocuments(documentList);
        note.setDate(new Date());
        note.setTitle(title);
        note.setDescription(description);
        return noteRepo.save(note);
    }
    public Document getAttachment(Long fileId) throws Exception {
        return DRepo
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }

    public List<Note> getNotes(){
        return noteRepo.findAll();
    }
    public Note getByIdNotes(Long Nid){
        return noteRepo.findById(Nid).orElseThrow(()->new UserNotException("Resource not found on server !!",Nid));
    }
    public  String deleteNote( Long id){
        if (!noteRepo.existsById(id)){
            throw new UserNotException("Resource not found on server !!",id);
        }
        noteRepo.deleteById(id);
        return "User Deleted Successes";
    }

    public Note update(Long noteId, String title, String description) throws IOException {
            Note note = noteRepo.findById(noteId).orElseThrow(() -> new EntityNotFoundException("Note not found"));
            note.setDate(new Date());
            note.setTitle(title);
            note.setDescription(description);
            return noteRepo.save(note);
        }
    public List<Note> findbykeyword(String word){
        return noteRepo.findbykeyword(word);
    }


}
