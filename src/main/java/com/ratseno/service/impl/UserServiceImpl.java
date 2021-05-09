package com.ratseno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ratseno.dto.UserDTO;
import com.ratseno.entity.UserEntity;
import com.ratseno.repository.UserRepository;
import com.ratseno.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserEntity userSave(UserDTO userDTO) throws Exception{
		UserEntity findUser = userRepository.findByUsername(userDTO.getUsername());
		if(findUser != null) {
			throw new Exception("EXISTED USER");
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userDTO.getUsername());
		userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		return userRepository.save(userEntity);
	}

}
