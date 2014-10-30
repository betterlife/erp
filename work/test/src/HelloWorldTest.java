import org.junit.Assert;
import org.junit.Test;


public class HelloWorldTest {

    @Test
    public void testGetString(){
        String str = HelloWorld.getString();
        Assert.assertEquals("String should be [Hello World!]", "Hello World!", str);
    }

}