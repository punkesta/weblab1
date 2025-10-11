package com.punksoft.weblab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ItemDataSeeder implements CommandLineRunner {

    private final ItemRepository itemRepository;

    public ItemDataSeeder(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        itemRepository.save(new Item("Test Item 1"));
        itemRepository.save(new Item("Test Item 2"));
    }
}