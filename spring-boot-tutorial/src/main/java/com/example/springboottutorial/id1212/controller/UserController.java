package com.example.springboottutorial.id1212.controller;

import com.example.springboottutorial.id1212.DTO.MessageUserDTO;
import com.example.springboottutorial.id1212.DTO.UserRoleDTO;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomCategory.ChatroomCategory;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomCategory.ChatroomCategoryRepository;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomUser.ChatroomUser;
import com.example.springboottutorial.id1212.entities.bridges.ChatroomUser.ChatroomUserRepository;
import com.example.springboottutorial.id1212.entities.category.Category;
import com.example.springboottutorial.id1212.entities.category.CategoryRepository;
import com.example.springboottutorial.id1212.entities.chat.*;
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
    private final RoleRepository roleRepository;
    private MessageRepository messageRepository;
    private MessageUserDTO messageUserDTO;
    private User user;
    private ChatroomCategory chatroomCategory;

    public UserController(ChatroomCategoryRepository chatroomCategoryRepository, CategoryRepository categoryRepository,
                          UserRepository userRepository, ChatroomUserRepository chatroomUserRepository,
                          ChatroomRepository chatroomRepository, MessageRepository messageRepository,
                          RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.chatroomUserRepository = chatroomUserRepository;
        this.chatroomRepository = chatroomRepository;
        this.categoryRepository = categoryRepository;
        this.chatroomCategoryRepository = chatroomCategoryRepository;
        this.messageRepository = messageRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/home")
    public String findUser(@RequestParam String email, @RequestParam String password, Model model) {
        user = userRepository.findUserByEmailAndPassword(email, password);
        if(user != null){
            model.addAttribute("user", user);
            Integer userId = user.getUserId();
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
        ArrayList<Integer> favChatrooms = chatroomUserRepository.getAllChatroomIdsForFavoriteChatroomByUserId(user.getUserId());
        ArrayList<Chatroom> favoriteChatrooms = new ArrayList<>();

        for(Integer favChat : favChatrooms) {
            Chatroom fcr = chatroomRepository.findChatRoomByChatroomId(favChat);
            favoriteChatrooms.add(fcr); // / Users favorite chatrooms
        }
        for(ChatroomUser cu : chatroomUser) {
            Integer chatId = cu.getChatroomId();
            Chatroom cr = chatroomRepository.findChatRoomByChatroomId(chatId);
            chatrooms.add(cr);   // Chatroom the user is part of
        }

        publicChatrooms.removeAll(chatrooms); // Public chatrooms, user is not in
        chatrooms.removeAll(favoriteChatrooms); // Removing favorite from all chatrooms, user is in

        model.addAttribute("chatroom", chatrooms);
        model.addAttribute("pubchatroom", publicChatrooms);
        model.addAttribute("favchatroom", favoriteChatrooms);
    }

    @GetMapping("/create-chatroom")
    public String createChatroom(Model model) {
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
    public String processChatroom(Model model, Chatroom chatroom, @RequestParam("categoryId") ArrayList<Integer> categoryIds) {
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
            chatroomUser.setAdmin(1); // TRUE
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
       /* Integer roleId = chatroomUserRepository.getRoleIdByUserIdChatroomId(user.getUserId(), id);
        Role role = roleRepository.findRoleByRoleId(roleId);
        roleRepository.delete(role);*/

        chatroomUserRepository.delete(chatroomUser);
        System.out.println("AFTER DELETE CHATROOM USER! ----------------------------------------");
        Chatroom chatroom = chatroomRepository.findChatRoomByChatroomId(id);
        ArrayList<ChatroomCategory> chatroomCategories = chatroomCategoryRepository.findChatroomCategoriesByChatroomId(id);
        if(chatroomUser.getAdmin() == 1) { // TRUE
            /*if chatroom had categories the bridge needs to be removed first*/
            if(chatroomCategories.size() > 0) {
                for(ChatroomCategory cc : chatroomCategories){
                    chatroomCategoryRepository.delete(cc);
                }
            }
            /*if chatroom had messages those needs to be removed too */
            ArrayList<Integer> messageIdsInChatroom = messageRepository.getAllMessageIdsByChatroomId(id);
            if(messageIdsInChatroom.size() > 0) {
                for(Integer mId : messageIdsInChatroom){
                    Message m = messageRepository.findMessageByMessageId(mId);
                    messageRepository.delete(m);
                }
            }
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
                Integer adminValue = 0;
                chatroomUser.setAdmin(adminValue); // FALSE
                chatroomUserRepository.save(chatroomUser);
                return "redirect:/chatroom/{id}";
            }

            if(chatroom != null){
                prevConversation(model, id);
                chatroomRole(model, id);

                Integer favoriteStatus = chatroomUserRepository.getFavoriteStatusByUserIdChatroomId(userId, id);
                String favoriteString = "false";
                if(favoriteStatus != null) {
                    System.out.println("Favorite Status USER CONTROLLER = " + favoriteStatus + "--------------------------------------------------------------------------");
                    if(favoriteStatus.equals(1)) {
                        favoriteString = "true";
                    }
                }
                model.addAttribute("favString", favoriteString); // must have for js code, not work with bool or number
                model.addAttribute("favorite", favoriteStatus);

                Integer roleId = chatroomUser.getRoleId();
                if(roleId != null) {
                    Role role = roleRepository.findRoleByRoleId(roleId);
                    String roleName = role.getRole();
                    model.addAttribute("userRole", roleName);
                }

                model.addAttribute("categories", categories);
                //model.addAttribute("chatroom", chatroomWithUser);
                Chatroom cr = chatroomRepository.findChatRoomByChatroomId(id);
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

    private void chatroomRole(Model model, Integer chatroomId) {
        ArrayList<Integer> userIdsInChatroom = chatroomUserRepository.getAllUserIdsByChatroomIdDescRoleIdOrder(chatroomId);
        ArrayList<UserRoleDTO> userRoles = new ArrayList<UserRoleDTO>();
        UserRoleDTO adminUser = new UserRoleDTO();

        for (Integer userId : userIdsInChatroom) {
            UserRoleDTO userRole = new UserRoleDTO();
            String username = userRepository.getUsername(userId);
            Integer admin = chatroomUserRepository.getAdminStatusByUserIdChatroomId(userId, chatroomId);
            Integer roleId = chatroomUserRepository.getRoleIdByUserIdChatroomId(userId, chatroomId);

            if(roleId != null) {
                Role role = roleRepository.findRoleByRoleId(roleId);
                String roleName = role.getRole();
                if(admin == 1) { // TRUE
                    adminUser.setRole(roleName);
                }
                else { userRole.setRole(roleName); }
            }

            if(admin == 1) { // TRUE
                adminUser.setUsername(username);
            }
            else {
                userRole.setUsername(username);
                userRoles.add(userRole);
            }
        }
        model.addAttribute("adminuser", adminUser);
        model.addAttribute("userroles", userRoles);
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

    private void prevConversation(Model model, Integer chatroomId) {
        ArrayList<Message> conversation = messageRepository.getAllMessagesInChatroom(chatroomId);
        ArrayList<MessageUserDTO> usernameConversations = new ArrayList<MessageUserDTO>();
        for (Message mes : conversation) {
            Integer userId = messageRepository.getUserIdByMessageId(mes.getId());
            String name = userRepository.getUsername(userId);

            MessageUserDTO userMessage = new MessageUserDTO();
            userMessage.setMessage(mes);
            userMessage.setUsername(name);
            usernameConversations.add(userMessage);
        }
        model.addAttribute("conversation", usernameConversations);
    }

    @GetMapping("/chatroom/{id}/create-role")
    public String createRole(Model model, @PathVariable Integer id) {
        if(user != null){
            Integer currentRole = chatroomUserRepository.getRoleIdByUserIdChatroomId(user.getUserId(), id);
            if (currentRole != null) {
                chatroomUserRepository.updateChatroomUserWithRoleId(null, id, user.getUserId());
            }
            Role role = new Role();
            Chatroom chatroom = chatroomRepository.findChatRoomByChatroomId(id);
            model.addAttribute("role", role);
            model.addAttribute("user", user);
            model.addAttribute("chatroom", chatroom);

            return "create-role";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }

    @PostMapping("/chatroom/{id}/create-role-process")
    public String processRole(Model model, @PathVariable Integer id, String role) { // Does not work when Role role as should
        if(user != null){
            Role chatRole = new Role();
            chatRole.setRole(role);
            roleRepository.save(chatRole);

            Integer roleId = chatRole.getRoleId();
            Integer userId = user.getUserId();

            //ChatroomUser chatroomUser = chatroomUserRepository.findChatroomUserByUserIdAndChatroomId(user.getUserId(), id);

            chatroomUserRepository.updateChatroomUserWithRoleId(roleId, id, userId);

            Chatroom chatroom = chatroomRepository.findChatRoomByChatroomId(id);
            model.addAttribute("chatroom", chatroom);
            model.addAttribute("role", role);

            return "create-role-success";
        }
        else {
            String message = "You are logged out";
            model.addAttribute("message", message);
        }
        return "index";
    }
}
