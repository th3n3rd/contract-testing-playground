package com.example.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface OrdersRepository extends JpaRepository<Order, UUID> {}
