package com.qthegamep.patternproject.controller;

import com.qthegamep.patternproject.model.CalculatorModel;
import com.qthegamep.patternproject.view.CalculatorView;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.function.DoubleConsumer;

/**
 * This class is the implementation of the controller that has logging to the file.
 * The class has a standard implementation of the calculator with I/O to the console
 * and has an implementation that accepts I/O parameters.
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorControllerImpl implements CalculatorController {

    @NonNull
    @Getter
    @Setter
    private CalculatorModel calculatorModel;
    @NonNull
    @Getter
    @Setter
    private CalculatorView calculatorView;

    /**
     * This method implements the beginning of work with the calculator. It does not have any input parameters.
     * The output is carried out to the console, the reading is done from the console also.
     * If expression divided by ';' then each result out to the new line.
     * Empty lines are not calculated.
     * Incoming data comes from the console.
     * Result output to the console.
     * All calculations take place in the {@link CalculatorModel}.
     * All outputs take place in the {@link CalculatorView}.
     */
    @Override
    public void startDefaultCalculate() {
        log.info("Preparing double consumer to set default output to the console for the calculator");

        DoubleConsumer doubleConsumer = System.out::println;
        log.info("Preparing double consumer to set default output to the console for the calculator " +
                "was done successful");

        calculateFromConsoleTo(doubleConsumer);
    }

    /**
     * This method takes Reader parameter and after calculation of the expression out the result to console
     * in the new line.
     * If expression divided by ';' then each result out to the new line.
     * Empty lines are not calculated.
     * Incoming data comes from the reader.
     * Result output to the console.
     * All calculations take place in the {@link CalculatorModel}.
     * All outputs take place in the {@link CalculatorView}.
     *
     * @param reader information reading parameters.
     */
    @Override
    public void calculateToConsoleFrom(Reader reader) {
        log.info("Preparing double consumer to set default output to the console for the calculator");

        DoubleConsumer doubleConsumer = System.out::println;
        log.info("Preparing double consumer to set default output to the console for the calculator " +
                "was done successful");

        calculateFromTo(reader, doubleConsumer);
    }

    /**
     * This method takes DoubleConsumer parameter and after calculation of the expression out the result
     * to DoubleConsumer.
     * If expression divided by ';' then each result out to the new line.
     * Empty lines are not calculated.
     * Incoming data comes from the console.
     * Result output to the resultConsumer.
     * All calculations take place in the {@link CalculatorModel}.
     * All outputs take place in the {@link CalculatorView}.
     *
     * @param resultConsumer result output parameter.
     */
    @Override
    public void calculateFromConsoleTo(DoubleConsumer resultConsumer) {
        log.info("Preparing input stream reader to set default input to the console for the calculator");

        try (val reader = new InputStreamReader(System.in, "UTF-8")) {
            log.info("Preparing input stream reader [{}] to set default input to the console for the " +
                            "calculator with encoding [{}] was done successful",
                    reader.getClass(),
                    reader.getEncoding()
            );

            calculateFromTo(reader, resultConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method read expression to calculate from Reader and then output result to DoubleConsumer.
     * If expression divided by ';' then each result out to the new line.
     * Empty lines are not calculated.
     * Incoming data comes from the console.
     * Result output to the resultConsumer.
     * All calculations take place in the {@link CalculatorModel}.
     * All outputs take place in the {@link CalculatorView}.
     *
     * @param reader         information reading parameters.
     * @param resultConsumer result output parameter.
     */
    @Override
    public void calculateFromTo(Reader reader, DoubleConsumer resultConsumer) {
        log.info("Preparing calculator to work");

        calculate(reader, resultConsumer);
    }

    private void calculate(@NonNull Reader reader, @NonNull DoubleConsumer resultConsumer) {
        log.info("Preparing calculator to work was done successful! Waiting for user input");

        new BufferedReader(reader).lines()
                .flatMap((s) -> Stream.of(s.split(";")))
                .filter((s) -> !s.trim().isEmpty())
                .mapToDouble(calculatorModel::calculate)
                .forEach(result -> calculatorView.outputResult(resultConsumer, result));
    }
}
