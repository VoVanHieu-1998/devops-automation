package com.devops.deploy_app.controller;

import com.devops.deploy_app.entity.Book;
import com.devops.deploy_app.repository.BookRepository;
import com.devops.deploy_app.repository.SACTTQTBCTInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * HomeController
 *
 * @author hieuvv
 * @version 01-00
 * @since 5/31/2024
 */
@RestController
@RequestMapping("/home")
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class HomeController {
    @Autowired
    SACTTQTBCTInfoRepository repository;
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/oracle")
    public String oracle() {
        int count = repository.countTTQTBCTChecking();
        return count + "" + "";
    }
    @GetMapping("/postgres")
    public ResponseEntity<List<Book>> postgres() {
        return ResponseEntity.ok(bookRepository.findAllData());
    }
}
