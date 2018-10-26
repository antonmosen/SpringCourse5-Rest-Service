package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    //@Mapping(source = "id", target = "id") //-> Not needed.
    //@Mapping(source = "name", target = "name") // -> Not needed.
    CategoryDTO categoryToCategoryDTO(Category category);
}
