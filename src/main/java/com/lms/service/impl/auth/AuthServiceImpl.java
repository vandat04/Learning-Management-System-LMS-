package com.lms.service.impl.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.lms.dto.request.LoginGoogleRequest;
import com.lms.dto.request.LoginRequest;
import com.lms.dto.request.RegisterRequest;
import com.lms.entity.auth.Role;
import com.lms.entity.auth.User;
import com.lms.entity.auth.UserProfile;
import com.lms.entity.auth.UserRole;
import com.lms.exception.AppException;
import com.lms.repository.auth.RoleRepository;
import com.lms.repository.auth.UserProfileRepository;
import com.lms.repository.auth.UserRepository;
import com.lms.repository.auth.UserRoleRepository;
import com.lms.security.GoogleTokenVerifier;
import com.lms.service.auth.AuthService;
import com.lms.service.auth.JwtService;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UserProfileRepository userProfileRepository;

    private final Validate validate;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final GoogleTokenVerifier googleTokenVerifier;

    @Override
    public String login(LoginRequest request) {
        //Lấy thông tin theo gmail
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new AppException("Email not exist", HttpStatus.BAD_REQUEST);
        }
        //Check pass
        validate.validatePassword(request.getPassword());
        validate.checkPasswordMatch(request.getPassword(), user.getPasswordHash());
        validate.checkActive(user.getIsActive());
        //Check role
        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(userRole.getRoleId())
                .orElseThrow(() -> new AppException("Role error", HttpStatus.BAD_REQUEST));

        return jwtService.generateToken(user.getId(), user.getEmail(), role.getName());
    }

    @Override
    public String loginWithGoogle(LoginGoogleRequest request) {
        //Lây token từ Google
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(request.getIdToken());

        if (payload == null) {
            throw new AppException("Invalid Google token", HttpStatus.UNAUTHORIZED);
        }
        //Lấy thông tin trong token
        String email = payload.getEmail();
        String fullName = (String) payload.get("name");
        String avatarUrl = (String) payload.get("picture");
        String googleId = payload.getSubject();

        //Kiểm tra xem đã đăng kí user trong DB chưa nếu đăng kí bằng LOCAL thì k cho dki mới, nếu chưa thì đkí
        User user = userRepository.findByEmail(email);
        validate.checkLocalAccountForGoogleLogin(user);
        if (user == null) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail(email);
            registerRequest.setFullName(fullName);
            registerRequest.setPassword("EscF1F2F3@@" + UUID.randomUUID().toString());
            this.registerStudent(registerRequest);

            //Thêm thông tin liên quan từ google cho tài khoản
            user = userRepository.findByEmail(email);
            user.setGoogleId(googleId);
            user.setAuthProvider("GOOGLE");
            userRepository.save(user);

            UserProfile userProfile = userProfileRepository.findByUserId(user.getId());
            userProfile.setAvatarUrl(avatarUrl);
            userProfileRepository.save(userProfile);
        } else {
            if (!googleId.equals(user.getGoogleId())) {
                throw new AppException("Google account mismatch", HttpStatus.UNAUTHORIZED);
            }
        }

        validate.checkActive(user.getIsActive());
        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(userRole.getRoleId())
                .orElseThrow(() -> new AppException("Role error", HttpStatus.BAD_REQUEST));

        return jwtService.generateToken(user.getId(), user.getEmail(), role.getName());

    }


    @Override
    public void registerStudent(RegisterRequest request) {
        //Check
        validate.checkNull(request.getEmail());
        validate.validateEmail(request.getEmail());
        validate.checkNull(request.getPassword());
        validate.validatePassword(request.getPassword());
        validate.checkNull(request.getFullName());
        validate.validateFullName(request.getFullName());
        //Tạo thông tin user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(true);
        user.setAuthProvider("LOCAL");
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        //Tao role
        UserRole userRole = new UserRole();
        userRole.setUserId(savedUser.getId());
        userRole.setRoleId(3);
        userRoleRepository.save(userRole);
        //Tạo profile
        UserProfile profile = new UserProfile();
        profile.setUserId(savedUser.getId());
        profile.setFullName(request.getFullName());
        profile.setRatingAvg(BigDecimal.valueOf(0.0));
        profile.setNumberReview(0);
        userProfileRepository.save(profile);
    }
}
