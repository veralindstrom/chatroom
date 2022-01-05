package com.example.springboottutorial.id1212.controller;

import com.example.springboottutorial.id1212.entities.bridges.ChatroomUser;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomUserRepository;
import com.example.springboottutorial.id1212.entities.category.Category;
import com.example.springboottutorial.id1212.entities.category.CategoryRepository;
import com.example.springboottutorial.id1212.entities.chat.Chatroom;
import com.example.springboottutorial.id1212.entities.chat.ChatroomRepository;
import com.example.springboottutorial.id1212.entities.user.User;
import com.example.springboottutorial.id1212.entities.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final ChatroomUserRepository chatroomUserRepository;
    private final ChatroomRepository chatroomRepository;
    private final CategoryRepository categoryRepository;
    private User user;
    private Chatroom chatroom;
    private ArrayList<Chatroom> chatrooms;

    public UserController(CategoryRepository categoryRepository, UserRepository userRepository, ChatroomUserRepository chatroomUserRepository, ChatroomRepository chatroomRepository) {
        this.userRepository = userRepository;
        this.chatroomUserRepository = chatroomUserRepository;
        this.chatroomRepository = chatroomRepository;
        this.categoryRepository = categoryRepository;

    }

    @PostMapping("/home")
    public String findUser(@RequestParam String email, @RequestParam String password, Model model) {
        user = userRepository.findUserByEmailAndPassword(email, password);
        if(user != null){
            model.addAttribute("user", user);
            home(model);
            return "home";
        }
        else {
            String message = "Invalid e-mail/password";
            model.addAttribute("message", message);
        }
        return "index";
    }

    @GetMapping("/home")
    public String test(Model model) {
        if(user != null){
            model.addAttribute("user", user);
            home(model);
            return "home";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }

    private void home(Model model) {
        ArrayList<ChatroomUser> chatroomUser = chatroomUserRepository.findChatroomUsersByUserId(user.getUserId());
        chatrooms = new ArrayList<>();
        ArrayList<Chatroom> publicChatrooms = chatroomRepository.getAllPublicId();
        for(ChatroomUser cu : chatroomUser) {
            Integer chatId = cu.getChatroomId();
            Chatroom cr = chatroomRepository.findChatRoomByChatroomId(chatId);
            chatrooms.add(cr); // / Chatroom the user is part of
            publicChatrooms.removeAll(chatrooms);
        }
        model.addAttribute("chatroom", chatrooms);
        model.addAttribute("pubchatroom", publicChatrooms);
    }

    @GetMapping("/create-chatroom")
    public String createChatroom1(Model model) {
        chatroom = new Chatroom();
        Category cat1 = new Category();
        cat1.setCategory("Programming");
        categoryRepository.save(cat1);
        ArrayList<Category> categories = (ArrayList<Category>) categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("chatroom", chatroom);

        return "create-chatroom";
    }

    @PostMapping("/create-chatroom-process")
    public String processChatroom1(Chatroom chatroom) {
        chatroom.addUserCount(1);
        chatroomRepository.save(chatroom);
        ChatroomUser chatroomUser = new ChatroomUser();
        chatroomUser.setChatroomId(chatroom.getId());
        chatroomUser.setUserId(user.getUserId());
        chatroomUser.setAdmin(true);
        chatroomUserRepository.save(chatroomUser);

        return "create-chatroom-success";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup-process")
    public String processRegister(User user) {
        userRepository.save(user);

        return "signup-success";
    }

    @GetMapping("/chatroom/{id}")
    public String showChatroom(@PathVariable Integer id, Model model) {
        /*later id is used to set as attribute to show specified chatroom*/
        Integer userId = user.getUserId();
        ChatroomUser chatroomUser = chatroomUserRepository.findChatroomUserByUserIdAndChatroomId(userId, id);
        if(chatroomUser == null) { // if chatroomUser not exist - user joined new public room
            chatroomUser = new ChatroomUser();
            chatroomUser.setChatroomId(id);
            chatroomUser.setUserId(userId);
            chatroomUser.setAdmin(false);
            chatroomUserRepository.save(chatroomUser);
        }
        model.addAttribute("user", new User());

        return "chatroom";
    }
}
