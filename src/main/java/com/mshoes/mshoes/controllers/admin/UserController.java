package com.mshoes.mshoes.controllers.admin;

import com.mshoes.mshoes.models.dtos.RequestedUser;
import com.mshoes.mshoes.models.dtos.UserDTO;
import com.mshoes.mshoes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private final UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/")
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") long userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody RequestedUser requestedUser) {
		return new ResponseEntity<>(userService.createUser(requestedUser), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable(name = "id") long userId,
			@RequestBody RequestedUser requestedUser) {
		UserDTO responseUser = userService.updateUser(requestedUser, userId);

		return new ResponseEntity<>(responseUser, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable(name = "id") long userId) {
		try {
			userService.deleteUser(userId);

			return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>("Delete fail!!", HttpStatus.OK);
		}
	}
}
