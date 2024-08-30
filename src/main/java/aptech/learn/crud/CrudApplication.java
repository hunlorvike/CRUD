package aptech.learn.crud;

import aptech.learn.crud.dtos.UserDTO;
import aptech.learn.crud.entities.User;
import aptech.learn.crud.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
@Controller
public class CrudApplication {

    private final UserRepository userRepository;

    @Autowired
    public CrudApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("title", "Home - MyApp");
        return "index";
    }

    @PostMapping("/")
    public String createUser(@Valid @ModelAttribute("userDto") UserDTO userDto, BindingResult bindingResult, Model model) {

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.userDto", "Username already exists");
        }

        if (bindingResult.hasErrors()) {
            return "index";
        }

        User newUser = User.builder()
                .username(userDto.getUsername())
                .fullname(userDto.getFullname())
                .password(userDto.getPassword())
                .build();

        userRepository.save(newUser);

        model.addAttribute("message", "User created successfully!");
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model) {
        return "success";
    }

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
}
