package com.patternproject;

import com.patternproject.test.rule.TimingRules;
import com.patternproject.test.util.TestUtil;

import lombok.val;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Koliadin Nikita
 */
public class ApplicationTest {

    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Before
    public void setUp() {
        System.setIn(TestUtil.CONSOLE_INPUT_STREAM);
        System.setOut(TestUtil.CONSOLE_PRINT_STREAM);
    }

    @Test
    public void shouldCalculateExpression() {
        val input = "sin(1)*sin(1)+cos(1)*cos(1)";

        val byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        val byteArrayOutputStream = new ByteArrayOutputStream();

        System.setIn(byteArrayInputStream);
        System.setOut(new PrintStream(byteArrayOutputStream));

        Application.main(null);

        val actual = byteArrayOutputStream.toString();
        val expected = "-> Hello!" + System.lineSeparator()
                + "-> I'm your calculator today!" + System.lineSeparator()
                + "-> To exit print: exit()" + System.lineSeparator()
                + "1.0" + System.lineSeparator();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldCalculateALotOfExpression() {
        val inputs = new String[]{
                "sin(1)*sin(1)+cos(1)*cos(1)",
                "(sin(1)*sin(1)+cos(1)*cos(1))+(tan(1)*(1/tan(1)))",
                "tan(0)+sqrt(225)"
        };

        val byteArrayOutputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(byteArrayOutputStream));

        Arrays.stream(inputs).forEach((input) -> {
            val byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
            System.setIn(byteArrayInputStream);
            Application.main(null);
        });

        val actual = byteArrayOutputStream.toString();
        val expected = "-> Hello!" + System.lineSeparator()
                + "-> I'm your calculator today!" + System.lineSeparator()
                + "-> To exit print: exit()" + System.lineSeparator()
                + "1.0" + System.lineSeparator()
                + "-> Hello!" + System.lineSeparator()
                + "-> I'm your calculator today!" + System.lineSeparator()
                + "-> To exit print: exit()" + System.lineSeparator()
                + "2.0" + System.lineSeparator()
                + "-> Hello!" + System.lineSeparator()
                + "-> I'm your calculator today!" + System.lineSeparator()
                + "-> To exit print: exit()" + System.lineSeparator()
                + "15.0" + System.lineSeparator();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldThrowInvocationTargetExceptionWhenCreateObjectWithReflection() {
        assertThatExceptionOfType(InvocationTargetException.class).isThrownBy(() -> {
            val constructor = Application.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        }).withCauseInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenSystemInIsNull() {
        System.setIn(null);
        assertThatNullPointerException().isThrownBy(
                () -> Application.main(null)
        );
    }

    @Test
    public void shouldThrowNullPointerExceptionWhenSystemOutIsNull() {
        System.setOut(null);
        assertThatNullPointerException().isThrownBy(
                () -> Application.main(null)
        );
    }
}
