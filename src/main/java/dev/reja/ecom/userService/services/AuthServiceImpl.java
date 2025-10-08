package dev.reja.ecom.userService.services;

import dev.reja.ecom.userService.dtos.LoginResponseDto;
import dev.reja.ecom.userService.dtos.SignUpRequestDto;
import dev.reja.ecom.userService.dtos.SignUpResponseDto;
import dev.reja.ecom.userService.exceptions.InvalidCredentialsException;
import dev.reja.ecom.userService.exceptions.InvalidTokenException;
import dev.reja.ecom.userService.exceptions.RandomException;
import dev.reja.ecom.userService.exceptions.UserNotFoundException;
import dev.reja.ecom.userService.models.Session;
import dev.reja.ecom.userService.models.SessionStatus;
import dev.reja.ecom.userService.models.User;
import dev.reja.ecom.userService.repositories.SessionRepository;
import dev.reja.ecom.userService.repositories.UserRepository;
import dev.reja.ecom.userService.utils.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public LoginResponseDto logIn(String email, String password) {
        User user=userRepository.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundException("User is not present for the given email "+ email)
        );

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidCredentialsException("Invalid Credential");
        }

        //token genration startted
        MacAlgorithm alg= Jwts.SIG.HS256;
        SecretKey key= alg.key().build();

//        Map<String, Object> jsonForJWT = new HashMap<>();
//        jsonForJWT.put("userId", user.getId());
//        jsonForJWT.put("roles", user.getRoles());
//        jsonForJWT.put("createdAt", new Date());
//        jsonForJWT.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token=jwtUtil.generateToken(user.getUserName());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserName());

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        session.setLoginAt(new Date());
        sessionRepository.save(session);

        LoginResponseDto loginResponseDto=new LoginResponseDto();
        loginResponseDto.setUserId(user.getId());
        loginResponseDto.setEmail(user.getEmail());
        loginResponseDto.setToken(token);
        loginResponseDto.setName(user.getName());
        loginResponseDto.setUserName(user.getUserName());
        loginResponseDto.setRefreshToken(refreshToken);
        return loginResponseDto;

    }

    @Override
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        User user=new User();
        if(userRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()){
            throw new RandomException("User Already Present");
        }
        if(userRepository.findByContactNo(signUpRequestDto.getContactNo()).isPresent()){
            throw new RandomException("User With this Contact No Already Present");
        }
        if(getUserNameAvailability(signUpRequestDto.getUserName())){
            throw new RandomException("userName Already Exits");
        }
        else{

            user.setName(signUpRequestDto.getName());
            user.setUserName(signUpRequestDto.getUserName());
            user.setEmail(signUpRequestDto.getEmail());
            user.setContactNo(signUpRequestDto.getContactNo());
            String password=passwordEncoder.encode(signUpRequestDto.getPassword());
            user.setPassword(password);
            userRepository.save(user);
            String token=jwtUtil.generateToken(user.getUserName());
            signUpResponseDto.setName(user.getName());
            signUpResponseDto.setEmail(user.getEmail());
            signUpResponseDto.setToken(token);
            signUpRequestDto.setContactNo(user.getContactNo());
            signUpResponseDto.setUserName(user.getUserName());
            signUpResponseDto.setRefreshToken(jwtUtil.generateRefreshToken(user.getUserName()));
        }

        return signUpResponseDto;
    }

    @Override
    public String logout(String token, UUID userId) {

        Session session=sessionRepository.findByTokenAndId(token,userId).orElseThrow(
                ()-> new InvalidTokenException("Token is not valid")
        );

        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return session.getUser()+" is logged out syccessfully";
    }

    @Override
    public boolean getUserNameAvailability(String userName) {
        Optional<User> userOptional=userRepository.findByUserName(userName);
        if(userOptional.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public User userDetailsByToken(String userToken) {
        userToken=userToken.substring(7);
        System.out.println(userToken);
        String userName = jwtUtil.extractUsername(userToken);
        return userRepository.findByUserName(userName).orElseThrow(
                ()-> new RandomException("Token isInvalid")
        );
    }

    @Override
    public String getAccessToken(String refrehToken) {
        String accesToken= jwtUtil.getAccessTokenByRefreshToken(refrehToken);
        return accesToken;
    }
}
