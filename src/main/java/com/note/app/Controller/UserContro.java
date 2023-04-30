package com.note.app.Controller;


import com.note.app.Model.User;
import com.note.app.Repository.UserRepo;
import com.note.app.exception.UserNotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserContro {
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/user")
    User adduser (@RequestBody User adduser){
        return userRepo.save(adduser);
    }

    @GetMapping("/user")
    List<User> viewuser(){
        return userRepo.findAll();
    }
    @GetMapping("/user/{id}")
    User getuserById(@PathVariable Long id){
        return userRepo.findById(id)
                .orElseThrow(()->new UserNotException("Resource not found on server !!",id));
    }
    @PutMapping("/user/{id}")
     User updateuser(@RequestBody User newuser,@PathVariable Long id){
          return userRepo.findById(id)
                  .map(usre ->{
                       usre.setUsername(newuser.getUsername());
                       usre.setFullName(newuser.getFullName());
                       usre.setPassword(newuser.getPassword());
                       usre.setEmail(newuser.getEmail());
                       return userRepo.save(usre);
                  }).orElseThrow(()-> new UserNotException("Resource not found on server !!",id));
        }
    @DeleteMapping("/user/{id}")
     String deleteUser(@PathVariable Long id){
        if (!userRepo.existsById(id)){
            throw new UserNotException("Resource not found on server !!",id);
        }
        userRepo.deleteById(id);
        return "User Deleted Successes";
    }

    @GetMapping("/user/name/{username}")
    User getuserByuserName(@PathVariable String username){
        return userRepo.findByName(username);
    }
}
