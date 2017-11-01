package com.deltacom.app.utils;

import com.deltacom.app.entities.*;
import com.deltacom.dto.*;

import java.util.List;

public class DTOConverter {
    private DTOConverter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Option OptionDTOToOption(OptionDTO optionDTO) {
        return new Option(optionDTO.getId(),  optionDTO.getName(),  optionDTO.getPrice(),  optionDTO.getConnectionCost(),
                null, null);
    }

    public static Client ClientDTOToClient(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getBirthDate(),
                clientDTO.getPassport(), clientDTO.getAddress(), clientDTO.getEmail(), clientDTO.getPassword(), null, null);
    }

    public static Tariff TariffDTOToTariff(TariffDTO tariffDTO) {
        return new Tariff(tariffDTO.getId(), tariffDTO.getName(), tariffDTO.getPrice(), null);
    }

    public static TariffDTO TariffToTariffDTO(Tariff tariff) {
        TariffDTO tariffDTO = new TariffDTO();

        tariffDTO.setId(tariff.getId());
        tariffDTO.setName(tariff.getName());
        tariffDTO.setPrice(tariff.getPrice());
        List<Option> options = tariff.getOptions();
        String[] tariffOptionsIds = new String[options.size()];
        for(int i = 0; i < options.size(); i++) {
            tariffOptionsIds[i] = Integer.toString(options.get(i).getId());
        }
        tariffDTO.setOptionsIds(tariffOptionsIds);

        return tariffDTO;
    }
}
