package com.mirceanealcos.confruntarea.service;

import com.mirceanealcos.confruntarea.dto.UserDTO;
import com.mirceanealcos.confruntarea.dto.UserLoginDTO;
import com.mirceanealcos.confruntarea.dto.UserRegisterDTO;
import com.mirceanealcos.confruntarea.entity.Item;
import com.mirceanealcos.confruntarea.entity.ItemOwnership;
import com.mirceanealcos.confruntarea.entity.User;
import com.mirceanealcos.confruntarea.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ChampionOwnershipRepository championOwnershipRepository;
    private final ItemOwnershipRepository itemOwnershipRepository;
    private final ResetPasswordTokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;
    private final ResetPasswordTokenService tokenService;
    private final MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository, ChampionOwnershipRepository championOwnershipRepository, ItemOwnershipRepository itemOwnershipRepository, ResetPasswordTokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder, ItemRepository itemRepository, ResetPasswordTokenService tokenService, MailService mailService) {
        this.userRepository = userRepository;
        this.championOwnershipRepository = championOwnershipRepository;
        this.itemOwnershipRepository = itemOwnershipRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    public Boolean userExists(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> dtos = new ArrayList<>();

        users.forEach(user -> dtos.add(toUserDTO(user)));

        return dtos;
    }

    public List<User> getAllUsersAsEntity() {
        return userRepository.findAll();
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return toUserDTO(user);
    }

    public User getUserEntityByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new Exception("User with username " + username + " does not exist!");
        }
        return user;
    }

    public List<UserDTO> getUsersByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        List<UserDTO> userDTOS = new ArrayList<>();

        users.forEach(user -> userDTOS.add(toUserDTO(user)));

        return userDTOS;
    }

    /**
     * This method adds a new user entity to the DB using a UserRegisterDTO
     * @param userRegisterDTO  the DTO provided via request body
     * @throws Exception in case of empty fields
     */
    public void registerUser(UserRegisterDTO userRegisterDTO) throws Exception {

        if(userRegisterDTO.getUsername() == null) {
            throw new Exception("Username not present!");
        }

        if(userRegisterDTO.getPassword() == null) {
            throw new Exception("Password not present!");
        }

        if(userRegisterDTO.getPassword().length() < 8) {
            throw new Exception("Password must contain at least 8 characters!");
        }

        if(!containsNumber(userRegisterDTO.getPassword())) {
            throw new Exception("Password must contain at least one number!");
        }

        if(!containsCapitalLetters(userRegisterDTO.getPassword())) {
            throw new Exception("Password must contain at least one capital letter!");
        }

        if(!containsSpecialCharacter(userRegisterDTO.getPassword())) {
            throw new Exception("Password must contain at least one special character!");
        }

        if(userRegisterDTO.getEmail() == null) {
            throw new Exception("Email not present!");
        }

        User user = new User(userRegisterDTO.getUsername(), userRegisterDTO.getEmail(), userRegisterDTO.getPassword());
        user.setRole("player");
        user.setFunds(6000);
        user.setPicture("img1.webp");
        user.setLevel(1);
        user.setExp(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
        List<Item> items = itemRepository.findAll();
        items.forEach(item -> {
            ItemOwnership ownership = new ItemOwnership();
            ownership.setUser(user);
            ownership.setItem(item);
            ownership.setItemCount(0);
            itemOwnershipRepository.save(ownership);
        });
        long duration = 60 * 60 * 1000;
        tokenService.generateToken(user, duration);
        mailService.sendValidateAccountLink(user);
    }

    public void performPurchase(String username, Integer amount) throws Exception {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new Exception("Username not found!");
        }

        if(amount < 0) {
            amount = -amount;
        }

        user.setFunds(user.getFunds() - amount);
        userRepository.save(user);
    }

    public void updateUser(String username, UserDTO userDTO) throws Exception {

        boolean hasChanged = false;

        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new Exception("User with username " + username + " does not exist!");
        }

        if(userDTO == null) {
            throw new Exception("Empty body..");
        }
        System.out.println(userDTO.getPassword());
        if(userDTO.getPassword() != null && !userDTO.getPassword().equals("")) {

            if(userDTO.getPassword().length() < 8) {
                throw new Exception("Password must contain at least 8 characters!");
            }

            if(!containsNumber(userDTO.getPassword())) {
                throw new Exception("Password must contain at least one number!");
            }

            if(!containsCapitalLetters(userDTO.getPassword())) {
                throw new Exception("Password must contain at least one capital letter!");
            }

            if(!containsSpecialCharacter(userDTO.getPassword())) {
                throw new Exception("Password must contain at least one special character!");
            }

            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            hasChanged = true;
        }

        if(userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
            hasChanged = true;
        }

        if(userDTO.getFunds() != null) {
            user.setFunds(userDTO.getFunds());
            hasChanged = true;
        }

        if(userDTO.getPicture() != null) {
            user.setPicture(userDTO.getPicture());
            hasChanged = true;
        }

        if(userDTO.getLevel() != null) {
            user.setLevel(userDTO.getLevel());
            hasChanged = true;
        }

        if(userDTO.getExp() != null) {
            user.setExp(userDTO.getExp());
            hasChanged = true;
        }

        if(!hasChanged) {
            throw new Exception("No changes happened..");
        }

        userRepository.save(user);

    }

    public void deleteUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new Exception("User with username " + username + " does not exist!");
        }
        tokenRepository.deleteByUserUsername(username);
        championOwnershipRepository.deleteByUserUsername(user.getUsername());
        itemOwnershipRepository.deleteByUserUsername(username);
        userRepository.delete(user);
    }

    private boolean containsNumber(String string) {
        char[] chars = string.toCharArray();
        for(char c : chars) {
            if(Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsCapitalLetters(String string) {
        char[] chars = string.toCharArray();
        for(char c : chars) {
            if(Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSpecialCharacter(String string) {
        char[] chars = string.toCharArray();
        for(char c : chars) {
            if(!Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFunds(),
                user.getPicture(),
                user.getLevel(),
                user.getExp(),
                user.getRole(),
                user.isEnabled()
        );
    }

    private UserLoginDTO toUserLoginDTO(User user) {
        return new UserLoginDTO(user.getUsername(), user.getPassword());
    }


    /**
     * This function overrides the UserDetails method so that Spring Security will use the users stored in the DB
     * @param username username of user to load
     * @return a Spring Security user object loaded with the User entities' information from the DB
     * @throws UsernameNotFoundException in case the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Username not found!");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .authorities(authorities).build();
    }
}
