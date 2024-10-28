package com.iktpreobuka.elektronski_dnevnik.repositories;

import com.iktpreobuka.elektronski_dnevnik.entities.UserEntity;

public interface UserRepository {
	UserEntity findByEmail(String email);
}
