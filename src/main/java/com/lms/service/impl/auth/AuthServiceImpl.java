package com.lms.service.impl.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.lms.dto.request.auth.authentication.LoginGoogleRequest;
import com.lms.dto.request.auth.authentication.LoginRequest;
import com.lms.dto.request.auth.authentication.RegisterRequest;
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
import com.lms.service.core.auth.AuthService;
import com.lms.service.core.auth.JwtService;
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
        String email = request.getEmail();
        String password = request.getPassword();
        //Lấy thông tin theo gmail
        validate.checkEmailExists(email);
        User user = userRepository.findByEmail(email);
        //Check pass
        validate.checkNull(password);
        validate.checkPasswordMatch(password, user.getPasswordHash());
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
        String email = request.getEmail();
        String password = request.getPassword();
        String fullName = request.getFullName();
        //Check
        validate.checkNull(email);
        validate.checkNull(password);
        validate.checkNull(fullName);
        validate.validateEmail(email);
        validate.validatePassword(password);
        validate.validateFullName(fullName);
        validate.validateByAI(fullName);
        //Tạo thông tin user
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
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
        profile.setFullName(fullName);
        profile.setRatingAvg(BigDecimal.valueOf(0.0));
        profile.setNumberReview(0);
        userProfileRepository.save(profile);
    }
}
