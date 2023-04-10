package com.mshoes.mshoes.services;

import com.mshoes.mshoes.models.dtos.RequestedSignup;
import com.mshoes.mshoes.models.dtos.RequestedUser;
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
	 * @param requestedUser
	 * @return
	 */
	UserDTO createUser(RequestedUser requestedUser);

	/**
	 * Method create new User <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param requestedSignup
	 * @return
	 */
	UserDTO signupUser(RequestedSignup requestedSignup);

	/**
	 * Method update user with new information User and userId. <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param requestedUser
	 * @param userId
	 * @return
	 */
	UserDTO updateUser(RequestedUser requestedUser, long userId);

	/**
	 * Method delete user with userId. <br>
	 * <u><i>Update: 26/02/2023</i></u>
	 *
	 * @param userId
	 */
	void deleteUser(long userId);
}
