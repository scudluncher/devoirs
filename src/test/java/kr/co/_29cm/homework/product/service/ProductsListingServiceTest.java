package kr.co._29cm.homework.product.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.co._29cm.homework.product.repository.ProductsRepository;

@ExtendWith(MockitoExtension.class)
public class ProductsListingServiceTest {
    
    @InjectMocks
    private ProductsListingService productsListingService;

    @Mock
    private ProductsRepository productsRepo;
    



}
