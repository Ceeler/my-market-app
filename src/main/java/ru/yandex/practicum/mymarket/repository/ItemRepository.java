package ru.yandex.practicum.mymarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.mymarket.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAllByTitleContainsIgnoreCase(String title, Pageable pageable);

    @Query("""
            SELECT i
            FROM Item i
            WHERE i.count > 0
            """)
    List<Item> findAllCartItems();
}