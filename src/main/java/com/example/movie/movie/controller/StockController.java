package com.example.movie.movie.controller;

import com.example.movie.movie.controller.Dto.StockDto;
import com.example.movie.movie.entity.Stock;
import com.example.movie.movie.repository.StockRepository;
import com.example.movie.movie.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();
        List<StockDto> stockDtoList = stockService.findAll()
                .stream().map(stock -> StockDto.builder()
                        .id(stock.getId())
                        .amount(stock.getAmount())
                        .build())
                .toList();
        response.put("DATA", stockDtoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        if (id == null) {
            response.put("message", "id no provided");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Stock> stockOptional = stockService.findById(id);
        if (stockOptional.isEmpty()) {
            response.put("message", "Stock with ID" + id + "not found");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Stock stock = stockOptional.get();
        StockDto stockDto = StockDto.builder()
                .id(stock.getId())
                .amount(stock.getAmount())
                .build();
        response.put("DATA", stockDto);
        return ResponseEntity.ok(response);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody StockDto stockDto){
        Map<String, Object> response = new HashMap<>();
        try {
            if (stockDto.getAmount() == null) {
                response.put("message", "One or more fields are empty");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }
                stockService.save(
                        Stock.builder()
                                .amount(stockDto.getAmount())
                                .build()
                );

                response.put("message", "Successfully created record");
                response.put("url", new URI("/stock"));

                return ResponseEntity.ok(response);

        } catch (URISyntaxException e) {
            //throw new RuntimeException(e);
            response.put("error", e.getMessage());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "id no provided");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Stock> stockOptional = stockService.findById(id);
        if(stockOptional.isPresent()){
            response.put("message", "Deleted record");
            stockService.deleteById(id);
            return ResponseEntity.ok(response);
        }

        response.put("message", "Stock with ID\" + id + \"not found");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

}

