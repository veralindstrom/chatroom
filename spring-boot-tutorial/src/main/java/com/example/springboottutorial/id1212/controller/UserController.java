package com.example.springboottutorial.id1212.controller;

import com.example.springboottutorial.id1212.entities.bridges.ChatroomCategory.ChatroomCategory;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomCategory.ChatroomCategoryRepository;
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
    private final ChatroomCategoryRepository chatroomCategoryRepository;
    private User user;
    private Chatroom chatroom;
    private ArrayList<Chatroom> chatrooms;
    private ChatroomCategory chatroomCategory;

    public UserController(ChatroomCategoryRepository chatroomCategoryRepository, CategoryRepository categoryRepository, UserRepository userRepository, ChatroomUserRepository chatroomUserRepository, ChatroomRepository chatroomRepository) {
        this.userRepository = userRepository;
        this.chatroomUserRepository = chatroomUserRepository;
        this.chatroomRepository = chatroomRepository;
        this.categoryRepository = categoryRepository;
        this.chatroomCategoryRepository = chatroomCategoryRepository;

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
        if(user != null){
            chatroom = new Chatroom();
            ArrayList<Category> categories = (ArrayList<Category>) categoryRepository.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("chatroom", chatroom);

            return "create-chatroom";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }

    @PostMapping("/create-chatroom-process")
    public String processChatroom1(Model model, Chatroom chatroom, @RequestParam("categoryId") ArrayList<Integer> categoryIds) {
        if(user != null){
            chatroom.addUserCount(1);
            chatroomRepository.save(chatroom);
            for (Integer categoryId : categoryIds){
                chatroomCategory = new ChatroomCategory();
                chatroomCategory.setCategoryId(categoryId);
                chatroomCategory.setChatroomId(chatroom.getId());
                chatroomCategoryRepository.save(chatroomCategory);
            }
            ChatroomUser chatroomUser = new ChatroomUser();
            chatroomUser.setChatroomId(chatroom.getId());
            chatroomUser.setUserId(user.getUserId());
            chatroomUser.setAdmin(true);
            chatroomUserRepository.save(chatroomUser);

            return "create-chatroom-success";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
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

    @GetMapping("/logout")
    public String logout(Model model) {
        user = null;

        return "redirect:/home"; //to let user know she logged out
    }

    @GetMapping("/leave-chatroom/{id}")
    public String leaveChatroom(@PathVariable Integer id, Model model){
        ChatroomUser chatroomUser = chatroomUserRepository.findChatroomUserByUserIdAndChatroomId(user.getUserId(), id);
        chatroomUserRepository.delete(chatroomUser);
        Chatroom chatroom = chatroomRepository.findChatRoomByChatroomId(id);
        ArrayList<ChatroomCategory> chatroomCategories = chatroomCategoryRepository.findChatroomCategoriesByChatroomId(id);
        if(chatroomUser.getAdmin()) {
            /*if chatroom had categories the bridge needs to be removed first*/
            if(chatroomCategories.size() > 0) {
                for(ChatroomCategory cc : chatroomCategories){
                    chatroomCategoryRepository.delete(cc);
                }
            }
            /*if chatroom had messages those needs to be removed too (for later)*/

            /*deleting chatroom*/
            chatroomRepository.delete(chatroom);
        }
        return "leave-chatroom";
    }

    @GetMapping("/chatroom/{id}")
    public String showChatroom(@PathVariable Integer id, Model model) {
        if(user != null){
            Integer userId = user.getUserId();
            ChatroomUser chatroomUser = chatroomUserRepository.findChatroomUserByUserIdAndChatroomId(userId, id);

            if(chatroomUser == null) { // if chatroomUser not exist - user joined new public room
                chatroomUser = new ChatroomUser();
                chatroomUser.setChatroomId(id);
                chatroomUser.setUserId(userId);
                chatroomUser.setAdmin(false);
                chatroomUserRepository.save(chatroomUser);
                return "redirect:/chatroom/{id}";
            }

            /*VERAS CODE START*/
            Chatroom chatroom = chatroomRepository.findChatRoomByChatroomId(chatroomUser.getChatroomId());
            ArrayList<ChatroomCategory> chatroomCategories = chatroomCategoryRepository.findChatroomCategoriesByChatroomId(id);
            ArrayList<Category> categories = new ArrayList<>();
            for(ChatroomCategory chatroomCategory : chatroomCategories){
                categories.add(categoryRepository.findCategoryBycategoryId(chatroomCategory.getCategoryId()));
            }

            if(chatroom != null){
                model.addAttribute("categories", categories);
                model.addAttribute("chatroom", chatroom);
                model.addAttribute("user", user);
            }
            /*VERAS CODE END*/
            model.addAttribute("user", new User());

            /*VERAS CODE START*/
            return "chatroom";
            /*VERAS CODE END*/
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }
}
