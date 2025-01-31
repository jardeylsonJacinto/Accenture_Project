package acc.br.relatorio_service;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import acc.br.login_service.config.security.TokenService;
import acc.br.login_service.controller.LoginController;
import acc.br.login_service.model.User;
import acc.br.login_service.model.dtos.AuthenticationDTO;
import acc.br.login_service.model.dtos.RegisterDTO;
import acc.br.login_service.model.enums.UserRoles;
import acc.br.login_service.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository repository;

    @Mock
    private TokenService tokenService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testLogin_Success() {
        // Arrange
        AuthenticationDTO authDTO = new AuthenticationDTO("user123", "password");
        UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());

        Authentication auth = mock(Authentication.class);
        User mockUser = new User(1L, "user123", passwordEncoder.encode("password"), UserRoles.USER);

        when(authenticationManager.authenticate(authToken)).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(mockUser);
        when(tokenService.generateToken(mockUser)).thenReturn("mockedToken");

        // Act
        ResponseEntity<?> response = loginController.login(authDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("mockedToken"));
    }

    @Test
    void testRegister_Success() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("newUser", "securePassword", UserRoles.USER);
        
        when(repository.findByLogin(registerDTO.login())).thenReturn(null);
        when(repository.save(any(User.class))).thenReturn(new User(registerDTO.login(), 
            passwordEncoder.encode(registerDTO.password()), registerDTO.role()));

        // Act
        ResponseEntity<?> response = loginController.register(registerDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testRegister_Fail_UserAlreadyExists() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("existingUser", "securePassword", UserRoles.USER);
        when(repository.findByLogin(registerDTO.login())).thenReturn(new User());

        // Act
        ResponseEntity<?> response = loginController.register(registerDTO);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }
}
