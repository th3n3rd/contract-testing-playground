package com.example.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface CartRepository extends JpaRepository<Cart, UUID> {}
