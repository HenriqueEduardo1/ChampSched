package com.champsched.service;

import com.champsched.dto.CampeonatoResponseDTO;
import com.champsched.dto.TimeResponseDTO;
import com.champsched.dto.UserRequestDTO;
import com.champsched.dto.UserResponseDTO;
import com.champsched.model.Campeonato;
import com.champsched.model.Time;
import com.champsched.model.User;
import com.champsched.repository.CampeonatoRepository;
import com.champsched.repository.TimeRepository;
import com.champsched.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UserService")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TimeRepository timeRepository;

    @Mock
    private CampeonatoRepository campeonatoRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        
        user = new User();
        user.setId(1);
        user.setNome("João Silva");
        user.setContato("joao@email.com");

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setNome("João Silva");
        userRequestDTO.setContato("joao@email.com");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(1);
        userResponseDTO.setNome("João Silva");
        userResponseDTO.setContato("joao@email.com");
    }

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void testCreateUser_Success() {
        
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.createUser(userRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("João Silva");
        assertThat(result.getContato()).isEqualTo("joao@email.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve retornar todos os usuários")
    void testGetAllUsers_Success() {
        
        User user2 = new User();
        user2.setId(2);
        user2.setNome("Maria Santos");
        user2.setContato("maria@email.com");

        List<User> users = Arrays.asList(user, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNome()).isEqualTo("João Silva");
        assertThat(result.get(1).getNome()).isEqualTo("Maria Santos");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void testGetAllUsers_EmptyList() {
        
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<UserResponseDTO> result = userService.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void testGetUserById_Success() {
        
        when(userRepository.findById(1)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.getUserById(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getNome()).isEqualTo("João Silva");
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente por ID")
    void testGetUserById_NotFound() {
        
        when(userRepository.findById(anyInt())).thenReturn(null);

        
        assertThatThrownBy(() -> userService.getUserById(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário não encontrado com ID: 999");
        verify(userRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void testUpdateUser_Success() {
        
        UserRequestDTO updateRequest = new UserRequestDTO();
        updateRequest.setNome("João Silva Atualizado");
        updateRequest.setContato("joao.novo@email.com");

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setNome("João Silva Atualizado");
        updatedUser.setContato("joao.novo@email.com");

        when(userRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponseDTO result = userService.updateUser(1, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("João Silva Atualizado");
        assertThat(result.getContato()).isEqualTo("joao.novo@email.com");
        verify(userRepository, times(1)).findUserById(1);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void testUpdateUser_NotFound() {
        
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> userService.updateUser(999, userRequestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário não encontrado com ID: 999");
        verify(userRepository, times(1)).findUserById(999);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void testDeleteUser_Success() {
        
        when(userRepository.findUserById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(1);

        verify(userRepository, times(1)).findUserById(1);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void testDeleteUser_NotFound() {
        
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> userService.deleteUser(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário não encontrado com ID: 999");
        verify(userRepository, times(1)).findUserById(999);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    @DisplayName("Deve retornar times do usuário")
    void testGetUserTimes_Success() {
        
        Time time1 = new Time();
        time1.setId(1);
        time1.setNome("Time A");

        Time time2 = new Time();
        time2.setId(2);
        time2.setNome("Time B");

        List<Time> times = Arrays.asList(time1, time2);

        when(userRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(timeRepository.findByIntegrantesId(1)).thenReturn(times);

        List<TimeResponseDTO> result = userService.getUserTimes(1);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(userRepository, times(1)).findUserById(1);
        verify(timeRepository, times(1)).findByIntegrantesId(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar times de usuário inexistente")
    void testGetUserTimes_UserNotFound() {
        
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> userService.getUserTimes(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário não encontrado com ID: 999");
        verify(userRepository, times(1)).findUserById(999);
        verify(timeRepository, never()).findByIntegrantesId(anyInt());
    }

    @Test
    @DisplayName("Deve retornar campeonatos do usuário")
    void testGetUserCampeonatos_Success() {
        
        Campeonato campeonato1 = new Campeonato();
        campeonato1.setId(1);
        campeonato1.setNome("Campeonato A");
        campeonato1.setOrganizador(user);

        Campeonato campeonato2 = new Campeonato();
        campeonato2.setId(2);
        campeonato2.setNome("Campeonato B");
        campeonato2.setOrganizador(user);

        List<Campeonato> campeonatos = Arrays.asList(campeonato1, campeonato2);

        when(userRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(campeonatoRepository.findByParticipanteId(1)).thenReturn(campeonatos);

        List<CampeonatoResponseDTO> result = userService.getUserCampeonatos(1);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(userRepository, times(1)).findUserById(1);
        verify(campeonatoRepository, times(1)).findByParticipanteId(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar campeonatos de usuário inexistente")
    void testGetUserCampeonatos_UserNotFound() {
        
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> userService.getUserCampeonatos(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário não encontrado com ID: 999");
        verify(userRepository, times(1)).findUserById(999);
        verify(campeonatoRepository, never()).findByParticipanteId(anyInt());
    }

    @Test
    @DisplayName("Deve retornar campeonatos organizados pelo usuário")
    void testGetUserCampeonatosOrganizados_Success() {
        
        Campeonato campeonato1 = new Campeonato();
        campeonato1.setId(1);
        campeonato1.setNome("Campeonato Organizado A");
        campeonato1.setOrganizador(user);

        List<Campeonato> campeonatos = Arrays.asList(campeonato1);

        when(userRepository.findUserById(1)).thenReturn(Optional.of(user));
        when(campeonatoRepository.findByOrganizadorId(1)).thenReturn(campeonatos);

        List<CampeonatoResponseDTO> result = userService.getUserCampeonatosOrganizados(1);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        verify(userRepository, times(1)).findUserById(1);
        verify(campeonatoRepository, times(1)).findByOrganizadorId(1);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar campeonatos organizados de usuário inexistente")
    void testGetUserCampeonatosOrganizados_UserNotFound() {
        
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());

        
        assertThatThrownBy(() -> userService.getUserCampeonatosOrganizados(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário não encontrado com ID: 999");

        verify(userRepository, times(1)).findUserById(999);
        verify(campeonatoRepository, never()).findByOrganizadorId(anyInt());
    }
}
