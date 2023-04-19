package com.mshoes.mshoes.services;

import com.mshoes.mshoes.models.requested.SignupRequest;
import com.mshoes.mshoes.models.requested.UserRequest;
import com.mshoes.mshoes.models.dtos.UserDTO;

import java.util.List;

public interface UserService {

	/**
	 * Method get all user is active in database <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @return
	 */
	List<UserDTO> getAllUsers();

	/**
	 * Method get user by userId. <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param userId
	 * @return
	 */
	UserDTO getUserById(long userId);

	/**
	 * Method create new User <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param userRequest
	 * @return
	 */
	UserDTO createUser(UserRequest userRequest);

	/**
	 * Method create new User <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param signupRequest
	 * @return
	 */
	UserDTO signupUser(SignupRequest signupRequest);

	/**
	 * Method update user with new information User and userId. <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param userRequest
	 * @param userId
	 * @return
	 */
	UserDTO updateUser(UserRequest userRequest, long userId);

	/**
	 * Method delete user with userId. <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param userId
	 */
	void deleteUser(long userId);
}
