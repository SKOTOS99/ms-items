package com.items.app.service;

import java.util.List;
import java.util.Optional;

import com.items.app.models.Item;

public interface ItemService {

	 List<Item> findAll();
	 Optional<Item> findById(Long id);
}
