package com.example.springboottutorial.id1212.controller;

import com.example.springboottutorial.Customer;
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
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final ChatroomUserRepository chatroomUserRepository;
    private final ChatroomRepository chatroomRepository;
    private final CategoryRepository categoryRepository;
    private User user;
    private Chatroom chatroom;
    private ChatroomUser chatroomUser;
    private ArrayList<ChatroomUser> userChatrooms;

    public UserController(CategoryRepository categoryRepository, UserRepository userRepository, ChatroomUserRepository chatroomUserRepository, ChatroomRepository chatroomRepository) {
        this.userRepository = userRepository;
        this.chatroomUserRepository = chatroomUserRepository;
        this.chatroomRepository = chatroomRepository;
        this.categoryRepository = categoryRepository;

    }

    @PostMapping("/home")
    public String findUser(@RequestParam String email , @RequestParam String password, Model model) {
        user = userRepository.findUserByEmailAndPassword(email, password);
        Integer userId = user.getUserId();
        ArrayList<ChatroomUser> chatroomuser = chatroomUserRepository.findChatroomUsersByUserId(userId);
        ArrayList<Chatroom> chatrooms = new ArrayList<>();
        for(ChatroomUser cu : chatroomuser) {
            chatrooms.add(chatroomRepository.findChatRoomByChatroomId(cu.getChatroomId()));
        }

        /*
        List<Integer> chatroomIds = chatroomUserRepository.getAllChatroomIdsByUserId(userId); // chatroom ids specific to user
        List<Integer> chatroomIdsAll = chatroomRepository.getAllId(); // all chatroom ids
        ArrayList<Integer> chatroomIdsforUser = new ArrayList<Integer>();
        for (Integer chatId : chatroomIdsAll) {
            chatroomUser = chatroomUserRepository.findChatroomUserByUserIdAndChatroomId(userId, chatId);
            if (chatroomUser != null) {
                chatroomIdsforUser.add(chatId);
            }
        }
        ArrayList<String> chatroomNamesforUser = new ArrayList<String>();
        for (Integer chatId : chatroomIds) {
            Chatroom chatroom = chatroomRepository.findChatRoomByChatroomId(chatId);
            String chatroomName = chatroom.getName();
            chatroomNamesforUser.add(chatroomName);
        }
        */
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("chatroom", chatrooms);
            //model.addAttribute("chatrooms", chatroomIds);
            //model.addAttribute("chatnames", chatroomNamesforUser);
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
            ArrayList<ChatroomUser> chatroomuser = chatroomUserRepository.findChatroomUsersByUserId(user.getUserId());
            ArrayList<Chatroom> chatrooms = new ArrayList<>();
            for(ChatroomUser cu : chatroomuser) {
                chatrooms.add(chatroomRepository.findChatRoomByChatroomId(cu.getChatroomId()));
            }
            model.addAttribute("chatroom", chatrooms);
            return "home";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }

    @GetMapping("/add-chatroom")
    public String createChatroom(Model model) {
        chatroom = new Chatroom();
        model.addAttribute("chatroom", chatroom);

        return "add-chatroom";
    }

    @PostMapping("/create-chatroom")
    public String addChatroom(@RequestParam String name, @RequestParam Boolean status, @RequestParam int userCount) {
        chatroom.setName(name);
        chatroom.setStatus(status);
        //chatroom.setId(1);
        chatroom.addUserCount(userCount);
        chatroomRepository.save(chatroom);
        return "home";
    }

    @GetMapping("/create-chatroom1")
    public String createChatroom1(Model model) {
        chatroom = new Chatroom();
        Category cat1 = new Category();
        cat1.setCategory("Programming");
        categoryRepository.save(cat1);
        ArrayList<Category> categories = (ArrayList<Category>) categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("chatroom", chatroom);

        return "create-chatroom1";
    }

    @PostMapping("/create-chatroom1-process")
    public String processChatroom1(Chatroom chatroom) {
        chatroom.addUserCount(1);
        chatroomRepository.save(chatroom);
        ChatroomUser chatroomUser = new ChatroomUser();
        chatroomUser.setChatroomId(chatroom.getId());
        chatroomUser.setUserId(user.getUserId());
        chatroomUser.setAdmin(true);
        chatroomUserRepository.save(chatroomUser);

        return "create-chatroom1-success";
    }

  /*  @PostMapping("/create-chatroom")
    public String chatroomRegister(Chatroom chatroom) {
        // to get from web is all columns in chatroom to create a userChatroom connection
        // once created the chatroom
        // create a userChatroom with the logged in user as admin
        chatroomRepository.save(chatroom);

        return "home";
    }*/

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
        model.addAttribute("user", new User());

        return "chatroom";
    }
}
