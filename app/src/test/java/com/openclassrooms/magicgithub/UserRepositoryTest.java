package com.openclassrooms.magicgithub;

import com.openclassrooms.magicgithub.api.FakeApiServiceGenerator;
import com.openclassrooms.magicgithub.di.Injection;
import com.openclassrooms.magicgithub.model.User;
import com.openclassrooms.magicgithub.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;
import java.util.stream.Collectors;

import static com.openclassrooms.magicgithub.api.FakeApiServiceGenerator.FAKE_USERS;
import static com.openclassrooms.magicgithub.api.FakeApiServiceGenerator.FAKE_USERS_RANDOM;
import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;


/**
 * Unit test, which will execute on a JVM.
 * Testing UserRepository.
 */
@RunWith(JUnit4.class)
public class UserRepositoryTest {
    
    private UserRepository userRepository;
    
    @Before
    public void setup() {
        userRepository = Injection.createUserRepository();
    } //
    
    @Test
    public void getUsersWithSuccess() {
        List<User> usersActual = userRepository.getUsers(); //on récup les utilisateurs actuels
        List<User> usersExpected = FAKE_USERS;  //users que l'on attend
        assertThat(usersActual, containsInAnyOrder(usersExpected.toArray()));  // on s'assure que la liste actuelle contient bien la liste de données.
    }

    @Test
    public void generateRandomUserWithSuccess() {
        userRepository.getUsers().clear(); // on assure un état initial de test vide.
        userRepository.generateRandomUser(); // on génère un User de la liste FAKE_USERS_RANDOM.
        User user = userRepository.getUsers().get(0); // on met le résultat généré dans user
        assertEquals(1, userRepository.getUsers().size()); // on s'assure que la génération produit un résultat ajouté à la liste.
        assertTrue(FAKE_USERS_RANDOM.stream().map(User::getAvatarUrl).collect(Collectors.toList()).contains(user.getAvatarUrl()));
        assertTrue(FAKE_USERS_RANDOM.stream().map(User::getId).collect(Collectors.toList()).contains(user.getId()));
        assertTrue(FAKE_USERS_RANDOM.stream().map(User::getLogin).collect(Collectors.toList()).contains(user.getLogin())); // on s'assure que les paramètres de user provienne bien de la liste FAKE_USERS_RANDOM.
        assertFalse(FAKE_USERS.stream().map(User::getAvatarUrl).collect(Collectors.toList()).contains(user.getAvatarUrl()));
        assertFalse(FAKE_USERS.stream().map(User::getId).collect(Collectors.toList()).contains(user.getId()));
        assertFalse(FAKE_USERS.stream().map(User::getLogin).collect(Collectors.toList()).contains(user.getLogin())); // on s'assure que les paramètres de user ne provienne pas de la liste FAKE_USERS
    }

    @Test
    public void deleteUserWithSuccess() {
        User userToDelete = userRepository.getUsers().get(0); // on récup le User à l'indice 0.
        userRepository.deleteUser(userToDelete); // on le delete
        assertFalse(userRepository.getUsers().contains(userToDelete)); // on s'assure que le User à supprimer n'est plus dans la liste.
    }
}