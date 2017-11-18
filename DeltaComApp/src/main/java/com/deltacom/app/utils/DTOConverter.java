package com.deltacom.app.utils;

import com.deltacom.app.entities.*;
import com.deltacom.dto.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for converting DTO to Entity / Entity to DTO
 */
public class DTOConverter {
    private DTOConverter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Converts OptionDTO to option
     * @param optionDTO option dto
     * @return option entity
     */
    public static Option OptionDTOToOption(OptionDTO optionDTO) {
        return new Option(optionDTO.getId(),  optionDTO.getName(),  optionDTO.getPrice(),  optionDTO.getConnectionCost(),
                null, null);
    }

    /**
     * Converts option to OptionDTO
     * @param option option entity
     * @return option dto
     */
    public static OptionDTO OptionToOptionDTO(Option option) {
        OptionDTO optionDTO = new OptionDTO(option.getId(), option.getName(), option.getPrice(), option.getConnectionCost(), null, null);
        optionDTO.setCompatibleOptions(getIdsArrayFromList(option.getCompatibleOptions()));
        optionDTO.setIncompatibleOptions(getIdsArrayFromList(option.getIncompatibleOptions()));
        return optionDTO;
    }

    /**
     * Converts ClientDTO to Client entity
     * @param clientDTO client dto
     * @return client entity
     */
    public static Client ClientDTOToClient(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getBirthDate(),
                clientDTO.getPassport(), clientDTO.getAddress(), clientDTO.getEmail(), clientDTO.getPassword(),
                clientDTO.isActivated(), clientDTO.getForgottenPassToken(), clientDTO.getOpenIdToken(),
                clientDTO.isUsingTwoFactorAuth(), clientDTO.getSmsCode(), clientDTO.getSmsSendDate(),
                clientDTO.getTwoFactorAuthNumber(), null, null);
    }

    /**
     * Converts TariffDTO to Tariff entity
     * @param tariffDTO tariff DTO
     * @return tariff entity
     */
    public static Tariff TariffDTOToTariff(TariffDTO tariffDTO) {
        return new Tariff(tariffDTO.getId(), tariffDTO.getName(), tariffDTO.getPrice(), null);
    }

    /**
     * Converts Tariff to Tariff DTO with Options
     * @param tariff tariff entity
     * @return tariff DTO with Options
     */
    public static TariffDTOwOpts TariffToTariffDTOwOpts(Tariff tariff) {
        TariffDTOwOpts tariffDTO = new TariffDTOwOpts(tariff.getId(), tariff.getName(), tariff.getPrice(), null);
        List<OptionDTO> options = new ArrayList<>();
        for(Option option : tariff.getOptions()) {
            options.add(OptionToOptionDTO(option));
        }
        tariffDTO.setOptions(options);
        return tariffDTO;
    }

    private static <T> String[] getIdsArrayFromList(List<T> entitiesList) {
        String[] ids = new String[entitiesList.size()];
        for(int i = 0; i < entitiesList.size(); i++) {
            T entity = entitiesList.get(i);
            if(entity instanceof Option) {
                Option option = (Option) entity;
                ids[i] = Integer.toString(option.getId());
            }
        }
        return ids;
    }
}
