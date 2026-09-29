package cl.ufro.dci.naive_pay_api.authentication.mapper;

import cl.ufro.dci.naive_pay_api.authentication.dto.AccountHolderDto;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir entre la entidad AccountHolder y su representación DTO (AccountHolderDto).
 * Se utiliza MapStruct para mapear las propiedades de la entidad a las del DTO.
 *
 * @author 00jesus
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * Convierte una entidad AccountHolder a un DTO AccountHolderDto.
     *
     * @param accountHolder La entidad AccountHolder que se va a convertir.
     * @return AccountHolderDto El DTO resultante.
     */
        AccountHolderDto toAccountDto(AccountHolder accountHolder);
    }

