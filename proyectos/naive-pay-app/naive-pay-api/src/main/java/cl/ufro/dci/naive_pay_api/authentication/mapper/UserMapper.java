package cl.ufro.dci.naive_pay_api.authentication.mapper;

import cl.ufro.dci.naive_pay_api.authentication.dto.UserDto;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para convertir una entidad AccountHolder en un DTO UserDto.
 * Utiliza MapStruct para mapear las propiedades de la entidad AccountHolder a la representación de datos UserDto.
 *
 * @author 00jesus
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convierte una entidad AccountHolder a un DTO UserDto.
     *
     * @param accountHolder La entidad AccountHolder que se va a convertir.
     * @return UserDto El DTO resultante con la información del AccountHolder.
     */
    UserDto toUserDto(AccountHolder accountHolder);
}
