package com.example.filestoragesystem.Service;

import com.example.filestoragesystem.entity.User;
import com.example.filestoragesystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        // Given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("admin");
        user.setEnabled(true);

        // Mock behavior of passwordEncoder
        when(passwordEncoder.encode("admin")).thenReturn("hashedPassword");

        // When
        userService.save(user);

        // Then
        verify(passwordEncoder, times(1)).encode("admin");
        verify(userRepository, times(1)).save(user);

        // Optionally, you can assert other conditions if needed
        assert user.getPassword().equals("hashedPassword");
    }
}
