package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@RequestMapping("dummy")
@RestController
public class DummyController {
    private final UserRepository userRepository;

    @PostMapping("join")
    public String join(User user){
        System.out.println("userName: " + user.getUserName());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);

        return "회원가입이 완료되었습니다";
    }

    @GetMapping("user/{id}")
    public User detail(@PathVariable int id){
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
            }
        });
        return user;
    }

    @GetMapping("user")
    public List<User> userList(){
        return userRepository.findAll();
    }

    @GetMapping("user/page")
    public List<User> userListAsPage(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        return users;
    }

    @Transactional
    @PutMapping("user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        System.out.println("id: " + id);
        System.out.println("password: " + requestUser.getPassword());
        System.out.println("email: " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("수정에 실패하였습니다"));

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        return user;
    }

    @DeleteMapping("user/{id}")
    public String delete(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 사용자는 존재하지 않습니다. id: " + id;
        }
        return "삭제되었습니다. id:" + id;
    }
}
