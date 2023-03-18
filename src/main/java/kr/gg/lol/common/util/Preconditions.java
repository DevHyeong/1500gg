package kr.gg.lol.common.util;

import org.springframework.lang.Nullable;

/**
 * Simple static methods to be called at the start of your own methods to verify correct arguments and state. This
 * allows constructs such as
 *
 * <pre>
 * if (count &lt;= 0) {
 * 	throw new IllegalArgumentException(&quot;must be positive: &quot; + count);
 * }
 * </pre>
 *
 * to be replaced with the more compact
 *
 * <pre>
 * checkArgument(count &gt; 0, &quot;must be positive: %s&quot;, count);
 * </pre>
 *
 * Note that the sense of the expression is inverted; with {@code Preconditions} you declare what you expect to be
 * <i>true</i>, just as you do with an <a href="http://java.sun.com/j2se/1.5.0/docs/guide/language/assert.html">
 * {@code assert}</a> or a JUnit {@code assertTrue} call.
 *
 * <p>
 * <b>Warning:</b> only the {@code "%s"} specifier is recognized as a placeholder in these messages, not the full range
 * of {@link String#format(String, Object[])} specifiers.
 *
 * <p>
 * Take care not to confuse precondition checking with other similar types of checks! Precondition exceptions --
 * including those provided here, but also {@link IndexOutOfBoundsException}, {@link UnsupportedOperationException} and
 * others -- are used to signal that the <i>calling method</i> has made an error. This tells the caller that it should
 * not have invoked the method when it did, with the arguments it did, or perhaps ever. Postcondition or other
 * invariant
 * failures should not throw these types of exceptions.
 *
 * @author Kevin Bourrillion
 * @since 2 (imported from Google Collections Library)
 */
public final class Preconditions {

    private Preconditions(){

    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param <T>       type
     * @param reference an object reference
     * @return the non-null reference that was validated
     */
    public static <T> T checkNotNull(T reference){
        if(reference == null){
            throw new IllegalArgumentException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param <T>          type
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a string using
     *                     {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage){
        if(reference == null){
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;
    }


}
