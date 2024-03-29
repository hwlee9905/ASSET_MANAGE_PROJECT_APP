package org.example.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.history.util.HistoryMapper;
import org.example.domain.login.dto.LoginDto;
import org.example.domain.login.service.LoginService;
import org.example.domain.manager.entity.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class LoginController {
    private final LoginService loginService;
    private final HistoryMapper historyMapper;
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        Manager manager = loginService.login(loginDto.getLoginId(), loginDto.getPassword());
        if (manager == null) {
            return new ResponseEntity<>("LOGIN FAILED : 아이디 또는 비밀번호가 맞지 않습니다.", HttpStatus.SERVICE_UNAVAILABLE);
        }else{
            //세션이 있으면 세션 반환, 없으면 신규 세션을 생성
            HttpSession session = request.getSession();
            //세션에 로그인 회원 정보 저장
            session.setAttribute(LoginDto.LOGIN_MANAGER, manager);
            historyMapper.setLoginMember((Manager) session.getAttribute(LoginDto.LOGIN_MANAGER));
            return new ResponseEntity<>("LOGIN SUCCESS", HttpStatus.OK);
        }
    }
    @PostMapping("/login/session")
    public ResponseEntity<Manager> getSession(@SessionAttribute(name = LoginDto.LOGIN_MANAGER, required = false) Manager manager, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false로 설정하면 세션이 없으면 null을 반환
        // 세션이 null이면 에러 반환
        if (session == null) {
            throw new IllegalStateException("해당 작업을 수행하기 위한 권한이 없습니다. 관리자 계정으로 로그인해주세요.");
        }

        return new ResponseEntity<>(manager, HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalStateException("해당 작업을 수행하기 위한 권한이 없습니다. 관리자 계정으로 로그인해주세요.");
            
        }else{
            session.invalidate();
        }
        return new ResponseEntity<>("LOGOUT SUCCESS", HttpStatus.OK);
    }
}
