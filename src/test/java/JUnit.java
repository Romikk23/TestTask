import org.junit.Test;

import static Main.Main.calculate;
import static org.junit.Assert.*;

public class JUnit {
    @Test
    public void testCalculateIntegerNumbers(){
        assertEquals(291.0,calculate("11+34*((93/31)+5)+8"), 0.1);
        assertEquals(20.0,calculate("(1*3+4/2)+15"), 0.1);
        assertEquals(-136.0,calculate("32+-12*(10+4)"), 0.1);
        assertEquals(10.0,calculate("22+14*10/20-19"), 0.1);
    }

    @Test
    public void testCalculateDoubleNumbers(){
        assertEquals(16.24,calculate("4.2+22.4/14.5*(12-4.2)"), 0.1);
        assertEquals(342.47,calculate("(41.5+12)/10*(12.4+44.1)+40.2)"), 0.1);
    }

    @Test
    public void testUnknowNumber() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            calculate("40.3.1*12/(43.1+12)");
        });

        String expectedMessage = "Unexpected number: ";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUnexpectedTokenN1() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            calculate("1*(10.5-3.3");
        });

        String expectedMessage = "Unexpected token: ";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUnexpectedTokenN2() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            calculate("1.4++2.5*12/10");
        });

        String expectedMessage = "Unexpected token: ";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUnexpectedCharacter() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            calculate("20+K*12/(10+4)");
        });

        String expectedMessage = "Unexpected character: ";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
