package com.adstand.app.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TariffsLoaderTest {
    TariffsLoader tariffsLoader;

    @Before
    public void setup() {
        tariffsLoader = new TariffsLoader();
    }

    // if there no exception - test correct
    @Test
    public void getTariffsFromServer() throws Exception {
        tariffsLoader.getTariffsFromServer();
    }

}