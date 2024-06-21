package com.sparta.bunga6.user.controller;

import com.sparta.bunga6.base.dto.CommonResponse;
import com.sparta.bunga6.security.UserDetailsImpl;
import com.sparta.bunga6.user.dto.*;
import com.sparta.bunga6.user.entity.User;
import com.sparta.bunga6.user.entity.UserRole;
import com.sparta.bunga6.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.sparta.bunga6.util.ControllerUtil.getFieldErrorResponseEntity;
import static com.sparta.bunga6.util.ControllerUtil.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     */
    @PostMapping("/user/signup")
    public ResponseEntity<CommonResponse<?>> signup(
            @Valid @RequestBody SignupRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "회원가입 실패");
        }

        User user = userService.signup(request);
        SignupResponse response = new SignupResponse(user);

        return getResponseEntity(response, "회원 가입 성공");
    }

    /**
     * 로그아웃
     */
    @GetMapping("/user/logout")
    public ResponseEntity<CommonResponse<?>> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long response = userService.logout(userDetails.getUser());

        return getResponseEntity(response, "로그아웃 성공");
    }

    /**
     * 프로필 조회
     */
    @GetMapping("/profiles/{userId}")
    public ResponseEntity<CommonResponse<?>> getProfile(
            @PathVariable Long userId
    ) {
        User user = userService.getUser(userId);
        ProfileResponse response = new ProfileResponse(user);

        return getResponseEntity(response, "프로필 조회 성공");
    }

    /**
     * 프로필 수정
     */
    @PostMapping("/profiles/{userId}")
    public ResponseEntity<CommonResponse<?>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "프로필 수정 실패");
        }
        validateUser(userId, userDetails);

        User user = userService.updateProfile(userId, request);
        ProfileResponse response = new ProfileResponse(user);

        return getResponseEntity(response, "프로필 수정 성공");
    }

    /**
     * 비밀번호 수정
     */
    @PatchMapping("/profiles/{userId}")
    public ResponseEntity<CommonResponse<?>> updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UpdatePasswordRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "비밀번호 변경 실패");
        }
        validateUser(userId, userDetails);

        User user = userService.updatePassword(userId, request);
        ProfileResponse response = new ProfileResponse(user);

        return getResponseEntity(response, "비빌번호 변경 성공");
    }

    /**
     * 전체 회원 조회 (관리자 전용)
     */
    @GetMapping("/users")
    public ResponseEntity<CommonResponse<?>> getAllUsers(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        validateAdmin(userDetails);

        List<User> users = userService.getAllUsers();
        List<ProfileResponse> response = users.stream()
                .map(ProfileResponse::new).toList();

        return getResponseEntity(response, "전체 회원 조회 성공");
    }

    /**
     * 회원 권한 수정 (관리자 전용)
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<CommonResponse<?>> updateUserRole(
            @PathVariable Long userId,
            @Valid @RequestBody RoleRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getFieldErrorResponseEntity(bindingResult, "회원 권한 수정 실패");
        }
        validateAdmin(userDetails);
        validatePathIdWithBody(userId, request.getUserId());

        User user = userService.updateRole(request);
        ProfileResponse response = new ProfileResponse(user);

        return getResponseEntity(response, "회원 권한 수정 성공");
    }

    private void validateUser(Long userId, UserDetailsImpl userDetails) {
        if (!Objects.equals(userId, userDetails.getUser().getId())) {
            throw new IllegalArgumentException("해당 id의 회원이 아닙니다.");
        }
    }

    private void validateAdmin(UserDetailsImpl userDetails) {
        if (!userDetails.getUser().getRole().equals(UserRole.ADMIN)) {
            throw new IllegalArgumentException("관리자 전용 기능입니다.");
        }
    }

    private void validatePathIdWithBody(Long pathId, Long bodyId) {
        if (!pathId.equals(bodyId)) {
            throw new IllegalArgumentException("PathVariable의 id가 RequestBody의 id와 일치하지 않습니다.");
        }
    }

}
