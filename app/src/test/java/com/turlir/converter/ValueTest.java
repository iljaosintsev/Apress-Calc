package com.turlir.converter;

import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ValueTest {

    @Test
    public void findFormatPath() {
        String t = "1234.1234567890123456789";
        BigDecimal bd = new BigDecimal(t, MathContext.UNLIMITED);

        DecimalFormatSymbols fs = new DecimalFormatSymbols(new Locale("ru"));
        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(fs);
        df.setMaximumFractionDigits(340);

        String format = df.format(bd);
        System.out.println(format);
        org.junit.Assert.assertEquals("1 234,1234567890123456789", format);
    }

    @Test
    public void displayTest() {
        Value v = new Value("1234.123456", false);
        Visual view = v.view();
        Printer printer = Mockito.mock(Printer.class);
        view.print(printer);
        Mockito.verify(printer).append(Mockito.eq("1 234,123456"));
    }

    @Test
    public void lastSymbolSeparatorTest() {
        Value v = new Value("1234", true);
        Visual view = v.view();
        Printer printer = Mockito.mock(Printer.class);
        view.print(printer);
        Mockito.verify(printer).append(Mockito.eq("1 234,"));
    }

}