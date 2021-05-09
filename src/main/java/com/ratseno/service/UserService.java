package com.ratseno.service;

import com.ratseno.dto.UserDTO;
import com.ratseno.entity.UserEntity;

public interface UserService {

	public UserEntity userSave(UserDTO userDTO) throws Exception;
}
