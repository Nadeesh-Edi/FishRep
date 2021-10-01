package com.example.fish;

import com.example.fish.AdListing.AdAdapter;

import org.junit.Test;
import org.junit.Before;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CalculationTest {
    private AdAdapter adAdapter;

    @Before
    public void setup() {
        adAdapter = new AdAdapter();
    }

    // Date difference calculation test - Sachintha - IT20208462
    @Test
    public void testGetDateDifference() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date posted =simpleDateFormat.parse("25-09-2021");
        Date current =simpleDateFormat.parse("01-10-2021");
        assert posted != null;
        assert current != null;
        Long dateDifference = adAdapter.getDateDifference(posted,current);

        assertEquals(6,dateDifference.longValue());
    }
}
