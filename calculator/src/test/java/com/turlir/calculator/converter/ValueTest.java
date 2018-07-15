package com.turlir.calculator.converter;

import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class ValueTest {

    private final DecimalFormat mFormat;

    public ValueTest() {
        mFormat = new DecimalFormat();
        DecimalFormatSymbols settings = new DecimalFormatSymbols(new Locale("ru"));
        mFormat.setDecimalFormatSymbols(settings);
        mFormat.setMaximumFractionDigits(340); // see doc DecimalFormat
        mFormat.setMinimumIntegerDigits(1);
    }

    @Test
    public void findFormatPath() {
        String t = "1234.1234567890123456789";
        BigDecimal bd = new BigDecimal(t, MathContext.UNLIMITED);

        DecimalFormatSymbols fs = new DecimalFormatSymbols(new Locale("ru"));
        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(fs);
        df.setMaximumFractionDigits(340);

        String format = df.format(bd);
        assertEquals("1 234,1234567890123456789", format);
    }

    @Test
    public void displayTest() {
        Value v = new Value("1234.123456", false);
        Visual view = v.view();
        Printer printer = Mockito.spy(new SimplePrinter(mFormat));
        view.print(printer);
        assertEquals("1 234,123456", printer.toString());
    }

    @Test
    public void lastSymbolSeparatorTest() {
        Value v = new Value("1234", true);
        Visual view = v.view();
        Printer printer = Mockito.spy(new SimplePrinter(mFormat));
        view.print(printer);
        assertEquals("1 234,", printer.toString());
    }

    private class SimplePrinter implements Printer {

        private final StringBuilder mBuilder;
        private final DecimalFormat mFormat;
        private final String mSeparator;

        SimplePrinter(DecimalFormat format) {
            mFormat = format;
            mSeparator = String.valueOf(mFormat.getDecimalFormatSymbols().getDecimalSeparator());
            mBuilder = new StringBuilder();
        }

        @Override
        public void append(String part) {
            mBuilder.append(part);
        }

        @Override
        public void append(BigDecimal part) {
            String str = mFormat.format(part);
            append(str);
        }

        @Override
        public void appendSeparator() {
            append(mSeparator);
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public void reset() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return mBuilder.toString();
        }
    }
}