package com.deltacom.app.utils;

import com.deltacom.app.entities.*;
import com.deltacom.dto.*;

import java.util.ArrayList;
import java.util.List;

public class DTOConverter {
    private DTOConverter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Option OptionDTOToOption(OptionDTO optionDTO) {
        return new Option(optionDTO.getId(),  optionDTO.getName(),  optionDTO.getPrice(),  optionDTO.getConnectionCost(),
                null, null);
    }

    public static OptionDTO OptionToOptionDTO(Option option) {
        OptionDTO optionDTO = new OptionDTO(option.getId(), option.getName(), option.getPrice(), option.getConnectionCost(), null, null);
        optionDTO.setCompatibleOptions(getIdsArrayFromList(option.getCompatibleOptions()));
        optionDTO.setIncompatibleOptions(getIdsArrayFromList(option.getIncompatibleOptions()));
        return optionDTO;
    }

    public static Client ClientDTOToClient(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getBirthDate(),
                clientDTO.getPassport(), clientDTO.getAddress(), clientDTO.getEmail(), clientDTO.getPassword(), null, null);
    }

    public static Tariff TariffDTOToTariff(TariffDTO tariffDTO) {
        return new Tariff(tariffDTO.getId(), tariffDTO.getName(), tariffDTO.getPrice(), null);
    }

    public static TariffDTOwOpts TariffToTariffDTOwOpts(Tariff tariff) {
        TariffDTOwOpts tariffDTO = new TariffDTOwOpts(tariff.getId(), tariff.getName(), tariff.getPrice(), null);
        List<OptionDTO> options = new ArrayList<>();
        for(Option option : tariff.getOptions()) {
            options.add(OptionToOptionDTO(option));
        }
        tariffDTO.setOptions(options);
        return tariffDTO;
    }

    public static TariffDTO TariffToTariffDTO(Tariff tariff) {
        TariffDTO tariffDTO = new TariffDTO(tariff.getId(), tariff.getName(), tariff.getPrice(), null);
        List<Option> options = tariff.getOptions();
        tariffDTO.setOptionsIds(getIdsArrayFromList(options));
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
