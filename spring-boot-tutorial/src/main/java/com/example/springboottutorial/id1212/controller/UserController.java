package com.example.springboottutorial.id1212.controller;

import com.example.springboottutorial.id1212.entities.bridges.ChatroomCategory.ChatroomCategory;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomCategory.ChatroomCategoryRepository;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomUser.ChatroomUser;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomUser.ChatroomUserRepository;
import com.example.springboottutorial.id1212.entities.category.Category;
import com.example.springboottutorial.id1212.entities.category.CategoryRepository;
import com.example.springboottutorial.id1212.entities.chat.Chatroom;
import com.example.springboottutorial.id1212.entities.chat.ChatroomRepository;
import com.example.springboottutorial.id1212.entities.chat.MessageRepository;
import com.example.springboottutorial.id1212.entities.user.User;
import com.example.springboottutorial.id1212.entities.user.UserRepository;
import com.example.springboottutorial.id1212.entities.chat.Message;
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
   // private MessageRepository messageRepository;
    //private ChatController chatController;
    private User user;
    private ChatroomCategory chatroomCategory;

    public UserController(ChatroomCategoryRepository chatroomCategoryRepository, CategoryRepository categoryRepository,
                          UserRepository userRepository, ChatroomUserRepository chatroomUserRepository,
                          ChatroomRepository chatroomRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.chatroomUserRepository = chatroomUserRepository;
        this.chatroomRepository = chatroomRepository;
        this.categoryRepository = categoryRepository;
        this.chatroomCategoryRepository = chatroomCategoryRepository;
        //this.messageRepository = messageRepository;
        //this.chatController = new ChatController(messageRepository, userRepository);
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
        ArrayList<Chatroom> chatrooms = new ArrayList<>();
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
            Chatroom chatroom = new Chatroom();
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
            Chatroom chatroom = chatroomRepository.findChatRoomByChatroomId(id);
            ChatroomUser chatroomUser = chatroomUserRepository.findChatroomUserByUserIdAndChatroomId(userId, id);
            //Chatroom chatroomWithUser = chatroomRepository.findChatRoomByChatroomId(chatroomUser.getChatroomId());
            ArrayList<ChatroomCategory> chatroomCategories = chatroomCategoryRepository.findChatroomCategoriesByChatroomId(id);
            ArrayList<Category> categories = new ArrayList<>();
            for(ChatroomCategory chatroomCategory : chatroomCategories){
                categories.add(categoryRepository.findCategoryBycategoryId(chatroomCategory.getCategoryId()));
            }

            if(chatroomUser == null) { // if chatroomUser not exist - user joined new public room
                chatroom.addUserCount(1);
                chatroomRepository.save(chatroom);
                chatroomUser = new ChatroomUser();
                chatroomUser.setChatroomId(id);
                chatroomUser.setUserId(userId);
                chatroomUser.setAdmin(false);
                chatroomUserRepository.save(chatroomUser);
                return "redirect:/chatroom/{id}";
            }

            if(chatroom != null){
                model.addAttribute("categories", categories);
                //model.addAttribute("chatroom", chatroomWithUser);
                //model.addAttribute("user", user);
                Chatroom cr = chatroomRepository.findChatRoomByChatroomId(id);
                Message userMessage = new Message();
                model.addAttribute("message", userMessage);
                model.addAttribute("user", user);
                model.addAttribute("chatroom", cr);
                model.addAttribute("chatroomId", id);
                chatroom(model, cr);
            }

            return "chatroom";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }

    private void chatroom(Model model, Chatroom chat) {
        Integer chatId = chat.getId();
        //Integer userId = user.getUserId();
        //ArrayList<ChatroomUser> chatroomUserList = chatroomUserRepository.findChatroomUsersByChatroomId(chatId);
        ArrayList<Integer> userIdList = chatroomUserRepository.getAllUserIdsByChatroomId(chatId);
        ArrayList<String> userNames = new ArrayList<String>();
        for (Integer id : userIdList) {
            String name = userRepository.getUsername(id);
            userNames.add(name);
        }
        model.addAttribute("usernames", userNames);
    }



    public void sendingMessage(String message, Model model) {
        if(message != null) {
        }
    }

}
