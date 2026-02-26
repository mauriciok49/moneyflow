package com.mauricio.moneyflow.service;

import com.mauricio.moneyflow.dto.CategoryRequestDTO;
import com.mauricio.moneyflow.dto.CategoryResponseDTO;
import com.mauricio.moneyflow.entity.Category;
import com.mauricio.moneyflow.exception.EntityNotFoundException;
import com.mauricio.moneyflow.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {

        Category category = Category.builder()
                .name(categoryRequestDTO.getName())
                .build();

        categoryRepository.save(category);

        return new CategoryResponseDTO(category.getId(), category.getName());

    }

    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return  categories.stream()
                .map(category -> new CategoryResponseDTO(category.getId(),category.getName()))
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO findById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return new CategoryResponseDTO(category.getId(), category.getName());

    }

    public CategoryResponseDTO update(UUID id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        category.setName(categoryRequestDTO.getName());


        categoryRepository.save(category);

        return new CategoryResponseDTO(category.getId(), category.getName());
    }

    public void deleteById(UUID id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        categoryRepository.deleteById(id);
    }
}
