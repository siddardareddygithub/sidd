package com.sid.interview;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleExponent {
private static final String HELLO_IBM = " Hello IBM: ";
private static final int MAX_ITERATION = 100;

public static void main(String[] args) throws InterruptedException {
StringBuffer message = new StringBuffer();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
// long base_interval = 50;
for (int i = 1; i <= MAX_ITERATION; i++) {
BigInteger result = calculateExponentialInterval(i, BigInteger.valueOf(50)); // Calculate interval directly
// check if the value overflow long size in java
long interval = result.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 ? Long.MAX_VALUE
: result.longValue();

Thread.sleep(interval);// sleep to print the output in exponentiated intervals
Date dt = new Date();
String formattedDate = dateFormat.format(dt); // Store the formatted date string
message.append(formattedDate).append(HELLO_IBM).append(i);
System.out.println(message);
message.setLength(0); // Clear StringBuffer using setLength(0)
}
}

private static BigInteger calculateExponentialInterval(int repetition, BigInteger initialValue) {
// Base case: the first term is the initial value
if (repetition == 1) {
return initialValue;
}
// Recursive case: square the previous term [50, 50^2=2500, 2500^2=6250000, ...]
BigInteger previousValue = calculateExponentialInterval(repetition - 1, initialValue);
return previousValue.multiply(previousValue); // Square the previous value

}
}


