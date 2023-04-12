package com.mshoes.mshoes.services.impl;

import com.mshoes.mshoes.exception.ResourceNotFoundException;
import com.mshoes.mshoes.libraries.Utilities;
import com.mshoes.mshoes.mapper.UserMapper;
import com.mshoes.mshoes.models.requested.RequestedSignup;
import com.mshoes.mshoes.models.requested.RequestedUser;
import com.mshoes.mshoes.models.dtos.UserDTO;
import com.mshoes.mshoes.models.Role;
import com.mshoes.mshoes.models.User;
import com.mshoes.mshoes.repositories.RoleRepository;
import com.mshoes.mshoes.repositories.UserRepository;
import com.mshoes.mshoes.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final RoleRepository roleRepository;

	@Autowired
	private final UserMapper userMapper;

	@Autowired
	private final Utilities utilities;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper,
						   Utilities utilities) {
		// TODO Auto-generated constructor stub
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userMapper = userMapper;
		this.utilities = utilities;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = userRepository.findAll();
		return userMapper.mapModelToDTOs(users);
	}

	@Override
	public UserDTO getUserById(long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		return userMapper.mapModelToDTO(user);
	}

	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Transactional
	@Override
	public UserDTO createUser(RequestedUser requestedUser) {
		// TODO Auto-generated method stub
		User user = userMapper.mapRequestedToModel(requestedUser);

		// Get current date and set userCreatedDate and userLastModified
		user.setCreatedDate(utilities.getCurrentDate());
		user.setModifiedDate(utilities.getCurrentDate());

		// Encode password

		user.setPassword(passwordEncoder().encode(user.getPassword()));

		// Set default userStatus
		user.setStatus(1);

		// Set default role_id = 2 (ROLE_USER)
		long defaultRoleId = 2;
		Role role = roleRepository.findById(defaultRoleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "ID", defaultRoleId));
		user.getRoles().add(role);

		// Save user into database
		User responseUser = userRepository.save(user);

		return userMapper.mapModelToDTO(responseUser);
	}

	@Override
	public UserDTO signupUser(RequestedSignup requestedSignup) {
		User user = userMapper.mapRequestedSignupToModel(requestedSignup);

		// Get current date and set userCreatedDate and userLastModified
		user.setCreatedDate(utilities.getCurrentDate());
		user.setModifiedDate(utilities.getCurrentDate());

		// Encode password

		user.setPassword(passwordEncoder().encode(user.getPassword()));

		// Set default userStatus
		user.setStatus(1);

		// Set default role_id = 2 (ROLE_USER)
		long defaultRoleId = 2;
		Role role = roleRepository.findById(defaultRoleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "ID", defaultRoleId));
		user.getRoles().add(role);

		// Save user into database
		try {
			User responseUser = userRepository.save(user);
			return userMapper.mapModelToDTO(responseUser);
		} catch (Exception e){
			return null;
		}
		}


		@Override
		public UserDTO updateUser(RequestedUser requestedUser, long userId) {
			// TODO Auto-generated method stub

			// Get old User with userId from Database
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
			userMapper.updateModel(user, requestedUser);
			user.setModifiedDate(utilities.getCurrentDate());

			// Save data
			User responseUser = userRepository.save(user);

			return userMapper.mapModelToDTO(responseUser);

		}

		@Override
		public void deleteUser(long userId) {
			// TODO Auto-generated method stub

			// Get old User with userId from Database
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
			try {
				user.getRoles().clear();
				userRepository.delete(user);
			} catch (Exception ex) {
				System.out.print("Ex: " + ex);
			}
		}
}
